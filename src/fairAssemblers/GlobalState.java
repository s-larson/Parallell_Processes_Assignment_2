package fairAssemblers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fairAssemblers.Agent;
import fairAssemblers.Assembler;
import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AsynchronousChan;

public class GlobalState {
	private final static int numberOfAssemblers = 5;
	public volatile static AsynchronousChan<Integer> supplierChan = new AsynchronousChan<Integer>();
	public volatile static List<AsynchronousChan<Integer>> assemblersChan = new ArrayList<AsynchronousChan<Integer>>();
	
	public static void main(String[] args) {
		// ArrayList of channels (one for each assembler)
		for(int i = 0; i <numberOfAssemblers; i++) {
			assemblersChan.add(new AsynchronousChan<Integer>());
		}
		
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