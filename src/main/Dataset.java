package main;

import geom.Point;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.lwjgl.opengl.GL11;

public abstract class Dataset extends TreeSet<Point>{
	private static final long serialVersionUID = 1522253239006114986L;
	GLColor color = JPlot.DEFAULT_LINECOLOR;
	private volatile boolean done = false;
	private List<Point> buffer = new LinkedList<Point>();
	Thread readThread = new Thread(new Runnable(){
		@Override
		public void run() {
			buffer.addAll(read());
			done=true;
		}
	});
	
	public Point get(int index){
		this.flush();
		final int idx = index;
		for(Point p : this.clone()){
			if(index==0){
				return p;
			}
			index--;
		}
		throw new ArrayIndexOutOfBoundsException(idx);
	}
	
	public Point min(){
		this.flush();
		if(this.isEmpty()) return null;
		Point res = this.get(0);
		for(Point p : this.clone()){
			res = res.setX(Math.min(res.x, p.x));
			res = res.setY(Math.min(res.y, p.y));
			res = res.setZ(Math.min(res.z, p.z));
		}
		return res;
	}
	
	public Point max(){
		this.flush();
		if(this.isEmpty()) return null;
		Point res = this.get(0);
		for(Point p : this.clone()){
			res = res.setX(Math.max(res.x, p.x));
			res = res.setY(Math.max(res.y, p.y));
			res = res.setZ(Math.max(res.z, p.z));
		}
		return res;
	}
	
	private static int floor(double d){
		return (int) Math.floor(d);
	}
	private static int ceil(double d){
		return (int) Math.ceil(d);
	}
	
	private static double adjust(double p, double min, double max){
		return 2*(p-min)/max-1;
	}
	
	private void flush(){
		this.addAll(this.buffer);
		this.buffer.removeAll(this.buffer);
		assert this.buffer.isEmpty();
	}
	
	public void render(){
		this.flush();
		if(this.isEmpty()) return;
		Point minp = min(), maxp = max();
		double minx = floor(minp.x), miny = floor(minp.y), maxx = ceil(maxp.x), maxy = ceil(maxp.y);
		JPlot.LINECOLOR.getVal().activate();
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for(Point p : this.clone()){
			p.setX(adjust(p.x,minx,maxx)).setY(adjust(p.y,miny,maxy)).activate();
//			JPlot.DEBUG.println("Drew point: "+p);
		}
		GL11.glEnd();
	}
	
	protected abstract Collection<? extends Point> read();
	
	public boolean isReading(){
		return this.readThread.isAlive();
	}
	
	public void begin(){
		if(!this.isReading() && !done){
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
	
	@SuppressWarnings("unchecked")
	public TreeSet<Point> clone(){
		this.flush();
		return (TreeSet<Point>) super.clone();
	}
}
