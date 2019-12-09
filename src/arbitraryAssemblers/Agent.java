package arbitraryAssemblers;

import java.util.concurrent.ThreadLocalRandom;

import common.IAssemblyActorProcess;
import common.MaterialForAssemblyCounter;
import se.his.iit.it325g.common.AndrewsProcess;

public class Agent implements Runnable, IAssemblyActorProcess {
	private MaterialForAssemblyCounter materialForAssemblyCounter = new MaterialForAssemblyCounter();


	
	/* This method should be implemented by the student. Use printState and doThings methods to observe what is happening and 
	 * to simulate that the process does something. Use materialForAssemblyCounter to maintain number of assemblies performed by this
	 * process.
	 *  
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("???????");
		while (true) {
			System.out.println("!!!!!!!!!!!!!!!");
			int part1 = ThreadLocalRandom.current().nextInt(0, 3);
			int part2 = part1;
			while (part2 == part1)
				part2 = ThreadLocalRandom.current().nextInt(0, 3);
			materialForAssemblyCounter.setAmountOfAssembly(part1, 1);
			materialForAssemblyCounter.setAmountOfAssembly(part2, 1);
			doThings();
			// printState();
			// determine appropriate assembler
			Integer p1, p2, p3, status = -1;
			p1 = materialForAssemblyCounter.getAmountOfAssembly(0);
			p2 = materialForAssemblyCounter.getAmountOfAssembly(1);
			p3 = materialForAssemblyCounter.getAmountOfAssembly(2);
			// p1,p2 = 10
			// p2,p3 = 11
			// p3,p1 = 12
			if (p1 == 1 && p2 == 1) {
				status = 10;
			}
			if (p2 == 1 && p3 == 1) {
				status = 11;
			}
			if (p3 == 1 && p1 == 1) {
				status = 12;
			}
			GlobalState.assemblerAChan.send(status);
			GlobalState.assemblerBChan.send(status);
			GlobalState.assemblerCChan.send(status);
			System.out.println("all sent"+status.toString());
			status = GlobalState.supplierChan.receive();		//Any received message means that the item has been consumed
			System.out.println("received");
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
				.current().nextInt(500, 1000));	
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Agent: " + AndrewsProcess.currentAndrewsProcessId() + " Status:" + this.materialForAssemblyCounter;
	}
}
