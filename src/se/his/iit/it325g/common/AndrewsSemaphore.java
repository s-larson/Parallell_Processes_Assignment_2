//    IT325G - Concurrent programming examples in Java
//    Copyright (C) 2017  Jonas Mikael Mellin
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.


package se.his.iit.it325g.common;

import java.util.concurrent.Semaphore;

public class AndrewsSemaphore {
	
	private Semaphore semaphore;

	public AndrewsSemaphore(int value) {
		this.semaphore = new Semaphore(value);
	}
	
	/**
	 * The P operation
	 */
	public void P() {
		this.semaphore.acquireUninterruptibly();
	}
	
	/**
	 * The V operation
	 */
	public void V() {
		this.semaphore.release();
	}


}
