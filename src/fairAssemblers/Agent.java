package fairAssemblers;

import java.util.concurrent.ThreadLocalRandom;

import common.IAssemblyActorProcess;
import common.MaterialForAssemblyCounter;
import se.his.iit.it325g.common.AndrewsProcess;

public class Agent implements Runnable, IAssemblyActorProcess {
	private MaterialForAssemblyCounter materialForAssemblyCounter = new MaterialForAssemblyCounter();
	// Used for cycling through parts and assemblers in a fair fashion
	private int turnParts, turnAssembler = 0;

	/* This method should be implemented by the student. Use printState and doThings methods to observe what is happening and 
	 * to simulate that the process does something. Use materialForAssemblyCounter to maintain number of assemblies performed by this
	 * process.
	 *  
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */

	@Override
	public void run() {
		// Determine what parts should be generated and modify message (status) to be sent
		// p1, p2 and p3 represents available parts and are used for a simpler comparison
		// Fairness is ensured with turnParts, since it cycles through all combination of parts methodically
		Integer p1, p2, p3, status = -1;
		while (true) {
			p1 = 0;
			p2 = 0;
			p3 = 0;
			if(turnParts==0) {
				p1 = 1; 
				p2 = 1;
				turnParts++;
			}else if(turnParts  == 1) {
				p2 = 1;
				p3 = 1;
				turnParts ++;
			}else if (turnParts == 2) {
				p3 = 1;
				p1 = 1;
				turnParts = 0;
			}
			doThings();
			if (p1 == 1 && p2 == 1) {
				status = 10;			
			}
			if (p2 == 1 && p3 == 1) {
				status = 11;
			}
			if (p3 == 1 && p1 == 1) {
				status = 12;
			}
			
			/* Notify one assembler at a time in ascending order using turnAssembler.
			 * Since parts are generated in same order every time, 
			 * and assemblers are instantiated in the same order (A, B, C, repeat),
			 * we will always notify the correct assembler directly.
			*/
			GlobalState.assemblersChan.get(turnAssembler).send(status);
			turnAssembler++;
			if(turnAssembler==GlobalState.assemblersChan.size()) turnAssembler = 0;
			
			//Any received message means that the item has been consumed --> Reset parts (done first next cycle)
			status = GlobalState.supplierChan.receive();
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
				.current().nextInt(500, 1000));	
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Agent: " + AndrewsProcess.currentAndrewsProcessId() + " Status:" + this.materialForAssemblyCounter;
	}
}