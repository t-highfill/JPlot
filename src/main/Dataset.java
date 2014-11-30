package main;

import geom.Point;

import java.util.Queue;
import java.util.TreeSet;

import org.lwjgl.opengl.GL11;

import util.Selector;

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
	
	public Point get(final int index){
//		this.flush();
		int idx = index;
		for(Point p : this){
			if(idx==0){
				return p;
			}
			idx--;
		}
		throw new ArrayIndexOutOfBoundsException(index);
	}
	
	public Point min(){
		return select(new Selector<Double>(){
			@Override
			public Double choose(Double e1, Double e2) {
				return Math.min(e1, e2);
			}
		}, JPlot.X_MIN.getVal(), JPlot.Y_MIN.getVal());
	}
	
	public Point max(){
		return select(new Selector<Double>(){
			@Override
			public Double choose(Double e1, Double e2) {
				return Math.max(e1, e2);
			}
		}, JPlot.X_MAX.getVal(), JPlot.Y_MAX.getVal());
	}
	
	private Point select(Selector<Double> selector, Double forceX, Double forceY){
//		this.flush();
		if(this.isEmpty()) return null;
		boolean testX = forceX==null, testY = forceY==null;
		Point res = this.first();
		if(!testX){
			res=res.setX(forceX);
		}
		if(!testY){
			res=res.setY(forceY);
		}
		if(testX || testY){
			for(Point p : this.clone()){
				if(testX)
					res = res.setX(selector.choose(res.x, p.x));
				if(testY)
					res = res.setY(selector.choose(res.y, p.y));
				res = res.setZ(selector.choose(res.z, p.z));
			}
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
		return 2*(p-min)/(max-min)-1;
	}
	
	private volatile boolean flushing = false;
	private void flush(){
		while(flushing){}
		flushing=true;
		JPlot.DEBUG.print("Flushing...");
		Queue<? extends Point> buffer = this.getBuffer();
		while(!buffer.isEmpty()){
			this.add(buffer.remove());
		}
		assert buffer.isEmpty();
		JPlot.DEBUG.println("done");
		cull();
		flushing=false;
	}
	
	public boolean isFlushing(){
		return flushing;
	}
	
	private void cull(){
		int limit = JPlot.BUFFER_LIMIT.getVal();
		if(limit>0){
			JPlot.DEBUG.println("Culling to limit: "+limit);
			while(this.size()>limit){
				this.remove(this.first());
			}
			JPlot.DEBUG.println("Size is now: "+this.size());
		}
	}
	
	public void render(){
		this.flush();
		if(this.isEmpty()) return;
		Point minp = min(), maxp = max();
		double minx = floor(minp.x), miny = floor(minp.y), maxx = ceil(maxp.x), maxy = ceil(maxp.y);
		JPlot.DEBUG.println(String.format("min:%s\tmax:%s", minp.toString(), maxp.toString()));
		JPlot.LINECOLOR.getVal().activate();
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for(Point p : this){
			p.setX(adjust(p.x,minx,maxx)).setY(adjust(p.y,miny,maxy)).activate();
//			JPlot.DEBUG.println("Drew point: "+p);
		}
		GL11.glEnd();
	}
	
	protected abstract void read();
	protected abstract Queue<? extends Point> getBuffer();
	
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
//		this.flush();
		TreeSet<Point> res = (TreeSet<Point>) super.clone();
		return res;
	}
}
