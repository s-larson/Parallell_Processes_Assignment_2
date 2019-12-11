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
		// Status represents what parts has been created by the Agent
		int status;
		while (true) {
			// * Applies to all types of assemblers: * 
			// 1. Listen to channel of corresponding type for information of what parts currently exists
			// 2. If the assembler's tools can handle the parts, use them and notify Agent through supplierChan
			// 3. Go back to listening
			
			if (getTool() == Tool.A) {
				status = GlobalState.assemblersChan.get(AndrewsProcess.currentRelativeToTypeAndrewsProcessId()).receive();
				if (status == 10) {
					GlobalState.supplierChan.send(1);
					doThings();
					System.out.println(AndrewsProcess.currentRelativeToTypeAndrewsProcessId()+" Created AA ");
				}
			}
			if (getTool() == Tool.B) {
				status = GlobalState.assemblersChan.get(AndrewsProcess.currentRelativeToTypeAndrewsProcessId()).receive();
				if (status == 11) {
					GlobalState.supplierChan.send(1);
					doThings();
					System.out.println(AndrewsProcess.currentRelativeToTypeAndrewsProcessId() + " Created AB ");
				}
			}
			if (getTool() == Tool.C) {
				status = GlobalState.assemblersChan.get(AndrewsProcess.currentRelativeToTypeAndrewsProcessId()).receive();
				if (status == 12) {
					GlobalState.supplierChan.send(1);
					doThings();
					System.out.println(AndrewsProcess.currentRelativeToTypeAndrewsProcessId() + " Created AC ");
				}
			}
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
