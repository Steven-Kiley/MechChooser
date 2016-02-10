package microserver;

/**
 * 
 * @author Steven Kiley
 *
 */

class SynchronizedCounter {
	private int count;
	SynchronizedCounter(){
		count = 0;
	}
	
	public synchronized void incrementCounter(){
		count++;
	}
	
	public int getCountValue(){int x = count; return x;}
	
}
