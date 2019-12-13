package fairAssemblers;

import java.util.concurrent.ThreadLocalRandom;

import fairAssemblers.GlobalState;
import common.IAssemblyActorProcess;
import common.MaterialForAssemblyCounter;
import se.his.iit.it325g.common.AndrewsProcess;

public class Agent implements Runnable, IAssemblyActorProcess {
	private MaterialForAssemblyCounter materialForAssemblyCounter = new MaterialForAssemblyCounter();
	// Used for cycling through parts and assemblers in a fair fashion
	private int turnParts = 0;
	private int turnAAssembler = 0;
	private int turnBAssembler = 0;
	private int turnCAssembler = 0;

	@Override
	public void run() {
		Integer p1, p2, p3, status = -1;
		int totalA = 0, totalB = 0, totalC = 0;
		
		// Calculate how many of each assemblers there are to accurately message the right assembler
		totalA = (int) Math.ceil((double)GlobalState.assemblersChan.size()/(double)3);
		int diff = GlobalState.assemblersChan.size() % 3;
		if(diff == 0) {
			totalB = totalA;
			totalC = totalA;
		}
		if(diff == 1) {
			totalB = totalA -1;
			totalC = totalA -1;
		}
		if(diff == 2) {
			totalB = totalA;
			totalC = totalA -1;
		}
		while (true) {
			// Determine what parts should be generated and modify message (status) to be sent
			// p1, p2 and p3 represents available parts and are used for a simpler comparison
			// Fairness is ensured with turnParts, since it cycles through all combination of parts methodically
			doThings();
			p1 = 0;
			p2 = 0;
			p3 = 0;
			if(turnParts==0) {
				p1 = 1; 
				p2 = 1;
				turnParts++;
				if(GlobalState.assemblersChan.size()==1) turnParts=0;
			}else if(turnParts  == 1) {
				p2 = 1;
				p3 = 1;
				turnParts++;
				if(GlobalState.assemblersChan.size()==2) turnParts=0;
			}else if (turnParts == 2) {
				p3 = 1;
				p1 = 1;
				turnParts=0;
			}
			
			/* Notify one assembler at a time in ascending order using turnAssembler.
			 * Since parts are generated in same order every time, 
			 * and assemblers are instantiated in the same order (A, B, C, repeat),
			 * we will always notify the correct assembler directly.
			*/
			if (p1 == 1 && p2 == 1) {
				status = 10;		
				GlobalState.assemblersChan.get(turnAAssembler*3).send(status);
				turnAAssembler++;
			}
			if (p2 == 1 && p3 == 1) {
				status = 11;
				GlobalState.assemblersChan.get(1+turnBAssembler*3).send(status);
				turnBAssembler++;
			}
			if (p3 == 1 && p1 == 1) {
				status = 12;
				GlobalState.assemblersChan.get(2+turnCAssembler*3).send(status);
				turnCAssembler++;
			}
			if(turnAAssembler==totalA) turnAAssembler = 0;
			if(turnBAssembler==totalB) turnBAssembler = 0;
			if(turnCAssembler==totalC) turnCAssembler = 0;
			
			
			//Any received message means that the item has been consumed --> Reset parts (done first next cycle)
			status = GlobalState.supplierChan.receive();
			materialForAssemblyCounter.setAmountOfAssembly(0, 0);
			materialForAssemblyCounter.setAmountOfAssembly(1, 0);
			materialForAssemblyCounter.setAmountOfAssembly(2, 0);
		}
	}
	
	/**
	 * This method prints the state of the Agent process
	 */
	@Override
	public void printState(){
		System.out.println(this);
	}
	
	/**
	 * This method simulates that the process does something and signals that a process
	 * preemption is acceptable to the run-time environment.
	 */
	
	@Override
	public void doThings(){
		AndrewsProcess.uninterruptibleMinimumDelay(ThreadLocalRandom
				.current().nextInt(1000, 1100));	
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Agent: " + AndrewsProcess.currentAndrewsProcessId() + " Status:" + this.materialForAssemblyCounter;
	}
}