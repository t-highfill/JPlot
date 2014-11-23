package main;

import geom.Point;

import java.io.Reader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DualStreamDataset extends Dataset {
	private static final long serialVersionUID = 3419431301911831808L;
	
	private final SingleStreamDataset xdata, ydata;
	
	public DualStreamDataset(Reader xsrc, Reader ysrc){
		this.xdata = new SingleStreamDataset(xsrc);
		this.ydata = new SingleStreamDataset(ysrc);
	}
	
	public DualStreamDataset(Reader xsrc, Reader ysrc, GLColor color){
		this(xsrc,ysrc);
		this.color = xdata.color = ydata.color = color;
	}

	@Override
	protected Collection<? extends Point> read() {//TODO test this
		List<Point> res = new LinkedList<Point>();
		for(int i=0;!this.areTheyDone();++i){
			waitFor(i, xdata);//hang until xdata gets data or ends
			waitFor(i, ydata);//hang until ydata gets data or ends
			if(i<xdata.size() && i<ydata.size()){
				double x = xdata.get(i).y;
				double y = ydata.get(i).y;
				res.add(new Point(x,y));
			}
		}
		return res;
	}
	
	private static void waitFor(int i, Dataset data){
		while(i>=data.size() & !data.isDone()){}
	}
	
	private boolean areTheyDone(){
		return xdata.isDone() || ydata.isDone();
	}
	
	public void begin(){
		this.xdata.begin();
		this.ydata.begin();
		super.begin();
	}

}
