package arbitraryAssemblers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import arbitraryAssemblers.Agent;
import arbitraryAssemblers.Assembler;
import common.Tool;
import se.his.iit.it325g.common.AndrewsProcess;

import se.his.iit.it325g.common.AsynchronousChan;

public class GlobalState {
	private final static int numberOfAssemblers = 6;
	public static AsynchronousChan<Integer> assemblerAChan = new AsynchronousChan<Integer>();
	public static AsynchronousChan<Integer> assemblerBChan = new AsynchronousChan<Integer>();
	public static AsynchronousChan<Integer> assemblerCChan = new AsynchronousChan<Integer>();
	public static AsynchronousChan<Integer> supplierChan = new AsynchronousChan<Integer>();
	
	public static void main(String[] args) {
		
		// create a list of runnables

		List<Runnable> runnableList;
		runnableList = IntStream.range(0,numberOfAssemblers).mapToObj(i -> new Assembler()).collect(Collectors.toList());
		runnableList.add(new Agent());
		
		// create an array of Andrews Processes based on the list of runnables
		
		AndrewsProcess[] processes = (AndrewsProcess[]) runnableList.stream().map(r -> new AndrewsProcess(r)).toArray(AndrewsProcess[]::new);
		
		// start the processes
		
		AndrewsProcess.startAndrewsProcesses(processes);
	}
}
