package common;

import java.util.Arrays;

public class MaterialForAssemblyCounter {
	private int[] amountOfAssembly = {0,0,0};
	
	/**
	 * Checks the index and throws an exception if it is out of range.
	 * 
	 * @param index
	 */
	private void checkIndex(int index) {
		if (index<0 || index>2) {
			throw new IllegalArgumentException("Index out of range");
		}
		
	}
	/**
	 * @param index
	 * @return The amount of assemblies of index registered by this counter. 
	 */
	public int getAmountOfAssembly(int index) {
		checkIndex(index);
		return this.amountOfAssembly[index];
	}
	/**
	 * Sets the amount of assemblies. Typically set methods are not encouraged, but in this case, if you want to
	 * pass a state (number of assemblies) from the agent to the assembler and cache that state in the assembler, then you
	 * can use this method.
	 * @param index
	 * @param value
	 */
	public void setAmountOfAssembly(int index, int value) {
		checkIndex(index);
		this.amountOfAssembly[index] = value;
	}
	/**
	 * Use to increase the amount of assemblies requested/generated/produced (depending on
	 * where the counter is used)
	 * @param index
	 */
	public void increaseAmountOfAssembly(int index) {
		checkIndex(index);
		this.amountOfAssembly[index]++;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MaterialForAssemblyCounter [part=" + Arrays.toString(amountOfAssembly) + "]";
	}
	
	
	

}
