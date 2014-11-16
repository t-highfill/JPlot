package main;

import geom.Point;

import java.util.TreeSet;

public abstract class Dataset extends TreeSet<Point>{
	private static final long serialVersionUID = 1522253239006114986L;
	GLColor color = JPlot.DEFAULT_LINECOLOR;
	private volatile boolean done = false;
	Thread readThread = new Thread(new Runnable(){
		@Override
		public void run() {
			read();
			done=true;
		}
	});
	
	public Point get(int index){
		final int idx = index;
		for(Point p : this){
			if(index==0){
				return p;
			}
			index--;
		}
		throw new ArrayIndexOutOfBoundsException(idx);
	}
	
	protected abstract void read();
	
	public void begin(){
		if(!this.readThread.isAlive() && !done){
			this.readThread.start();
		}
	}

	public GLColor getColor() {
		return color;
	}

	public void setColor(GLColor color) {
		this.color = color;
	}

	public boolean isDone() {
		return done;
	}
}
