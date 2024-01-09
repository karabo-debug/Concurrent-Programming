package clubSimulation;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
// GridBlock class to represent a block in the club.
// only one thread at a time "owns" a GridBlock

public class GridBlock {
	private AtomicInteger isOccupied; 
	private final AtomicBoolean isExit;  //is tthis the exit door?
	private final AtomicBoolean isBar; //is it a bar block?
	private final AtomicBoolean isDance; //is it the dance area?
	private AtomicInteger [] coords; // the coordinate of the block.
	
	GridBlock(boolean exitBlock, boolean barBlock, boolean danceBlock) throws InterruptedException {
		isExit=new AtomicBoolean(exitBlock);
		isBar=new AtomicBoolean(barBlock);
		isDance=new AtomicBoolean(danceBlock);
		isOccupied= new AtomicInteger(-1);
	}
	
	GridBlock(int x, int y, boolean exitBlock, boolean refreshBlock, boolean danceBlock) throws InterruptedException {
		this(exitBlock,refreshBlock,danceBlock);
		coords = new AtomicInteger [] {new AtomicInteger(x),new AtomicInteger(y)};
	}
	
	public synchronized int getX() {return coords[0].get();}  
	
	public synchronized int getY() {return coords[1].get();}
	
	public synchronized boolean get(int threadID) throws InterruptedException {
		if (isOccupied.get()==threadID) return true; //thread Already in this block
		if (isOccupied.get()>=0) return false; //space is occupied
		//isOccupied=threadID;  //set ID to thread that had block
		isOccupied.set(threadID); //set ID to thread that had block
		return true;
	}
		
	public synchronized void release() {
		isOccupied.set(-1);
	}
	
	public synchronized boolean occupied() {
		if(isOccupied.get()==-1) return false;
		return true;
	}
	
	public synchronized boolean isExit() {
		return isExit.get();	
	}

	public synchronized boolean isBar() {
		return isBar.get();
	}
	public synchronized boolean isDanceFloor() {
		return isDance.get();
	}

}
