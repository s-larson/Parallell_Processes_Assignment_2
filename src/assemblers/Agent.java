package assemblers;

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
		while (GlobalState.totalPartsA + GlobalState.totalPartsB + GlobalState.totalPartsC < 60) {
			// Generate two unique parts and place them on the depot
			int part1 = ThreadLocalRandom.current().nextInt(0, 3);
			int part2 = part1;
			while (part2 == part1)
				part2 = ThreadLocalRandom.current().nextInt(0, 3);
			materialForAssemblyCounter.setAmountOfAssembly(part1, 1);
			materialForAssemblyCounter.setAmountOfAssembly(part2, 1);
			doThings();

			// Determine what parts are in depot and modify message (status) to be sent
			// p1, p2 and p3 is used for a simpler comparison
			
			Integer p1, p2, p3, status = -1;
			p1 = materialForAssemblyCounter.getAmountOfAssembly(0);
			p2 = materialForAssemblyCounter.getAmountOfAssembly(1);
			p3 = materialForAssemblyCounter.getAmountOfAssembly(2);
			if (p1 == 1 && p2 == 1) {
				GlobalState.totalPartsA++;
				GlobalState.totalPartsB++;
				status = 10;
			}
			if (p2 == 1 && p3 == 1) {
				GlobalState.totalPartsB++;
				GlobalState.totalPartsC++;
				status = 11;
			}
			if (p3 == 1 && p1 == 1) {
				GlobalState.totalPartsC++;
				GlobalState.totalPartsA++;
				status = 12;
			}
			
			System.out.println(status);
			// Notify all assembler queues there's parts ready for pickup
			GlobalState.assemblerAChan.send(status);
			GlobalState.assemblerBChan.send(status);
			GlobalState.assemblerCChan.send(status);
			
			//Any received message means that the item has been consumed --> Reset parts
			status = GlobalState.supplierChan.receive();		
			materialForAssemblyCounter.setAmountOfAssembly(0, 0);
			materialForAssemblyCounter.setAmountOfAssembly(1, 0);
			materialForAssemblyCounter.setAmountOfAssembly(2, 0);
		}
		System.out.println("Total A created: "+ GlobalState.totalPartsA);
		System.out.println("Total B created: "+ GlobalState.totalPartsB);
		System.out.println("Total C created: "+ GlobalState.totalPartsC);
		System.out.println("Total A assembled: " + GlobalState.totalAssembledA);
		System.out.println("Total B assembled: " + GlobalState.totalAssembledB);
		System.out.println("Total C assembled: " + GlobalState.totalAssembledC);
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
				.current().nextInt(1, 5));	
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Agent: " + AndrewsProcess.currentAndrewsProcessId() + " Status:" + this.materialForAssemblyCounter;
	}
}