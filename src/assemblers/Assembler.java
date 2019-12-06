package assemblers;

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
		int status = 0;
		while (true) {
			if(getTool() == Tool.A) {
				status = GlobalState.assemblerAChan.receive();
				if(status == 10) {
					GlobalState.supplierChan.send(1337);
					
					// why this? context switch = bad
					materialForAssemblyCounter.setAmountOfAssembly(0, 1);
					materialForAssemblyCounter.setAmountOfAssembly(1, 1);
					doThings();
					System.out.println("Created AA");
					materialForAssemblyCounter.setAmountOfAssembly(0, 0);
					materialForAssemblyCounter.setAmountOfAssembly(1, 0);
				}
			}
			if(getTool() == Tool.B) {
				status = GlobalState.assemblerBChan.receive();
				if(status == 11) {
					GlobalState.supplierChan.send(1338);
					materialForAssemblyCounter.setAmountOfAssembly(1, 1);
					materialForAssemblyCounter.setAmountOfAssembly(2, 1);
					doThings();
					System.out.println("Created AB");
					materialForAssemblyCounter.setAmountOfAssembly(1, 0);
					materialForAssemblyCounter.setAmountOfAssembly(2, 0);
				}
			}
			if(getTool() == Tool.C) {
				status = GlobalState.assemblerCChan.receive();
				if(status == 12) {
					GlobalState.supplierChan.send(1339);
					materialForAssemblyCounter.setAmountOfAssembly(2, 1);
					materialForAssemblyCounter.setAmountOfAssembly(0, 1);
					doThings();
					System.out.println("Created AC");
					materialForAssemblyCounter.setAmountOfAssembly(2, 0);
					materialForAssemblyCounter.setAmountOfAssembly(0, 0);
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

