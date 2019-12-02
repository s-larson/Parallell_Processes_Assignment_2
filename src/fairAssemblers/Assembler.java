package fairAssemblers;

import java.util.concurrent.ThreadLocalRandom;

import common.IAssemblyActorProcess;
import common.MaterialForAssemblyCounter;
import common.Tool;
import se.his.iit.it325g.common.AndrewsProcess;

public class Assembler implements Runnable, IAssemblyActorProcess {
	private MaterialForAssemblyCounter materialForAssemblyCounter = new MaterialForAssemblyCounter();

	

	/**
	 * this method returns the tool associated with the process. 
	 * @return
	 */
	Tool getTool() {
		return Tool.values()[AndrewsProcess.currentRelativeToTypeAndrewsProcessId()%3];
	}
	
	/* This method should be implemented by the student. Use printState and doThings methods to observe what is happening and 
	 * to simulate that the process does something. Use materialForAssemblyCounter to maintain number of assemblies performed by this
	 * process.
	 *  
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */

	@Override
	public void run() {
		while (true) {

		}
	}

	/**
	 * This method prints the state of the Assembler process
	 */
	@Override
	public void printState() {
		System.out.println(this);
	}

	/**
	 * This method simulates that the process does something and signals that a process
	 * preemption is acceptable to the run-time environment.
	 */
	@Override
	public void doThings() {
		AndrewsProcess.uninterruptibleMinimumDelay(ThreadLocalRandom.current()
				.nextInt(500, 1000));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Assembler: " 
				+ AndrewsProcess.currentRelativeToTypeAndrewsProcessId()
				+ " Tool: " + this.getTool()
				+ " Status:" + this.materialForAssemblyCounter;
	}
}
