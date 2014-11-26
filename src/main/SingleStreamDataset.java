package main;

import geom.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SingleStreamDataset extends Dataset {
	private static final long serialVersionUID = -1074138053765911487L;
	
	BufferedReader br;
	String separator = null;
	boolean trim = false;
	List<Point> buffer = new LinkedList<Point>();
	
	private String getLine(){
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	protected void read(){
		String line = getLine();
		for(double x=0;line!=null;++x){
			JPlot.DEBUG.println("reading: "+line);
			if(trim)
				line = line.trim();
			double y=0;
			if(separator!=null){
				String[] pair = line.split(this.separator);
				assert pair.length>=2;
				x = Double.parseDouble(pair[0]);
				line = pair[1];
			}
			y=Double.parseDouble(line);
			Point p = new Point(x,y);
			JPlot.DEBUG.println("Adding to buffer: "+p);
			buffer.add(p);
			line=getLine();
		}
	}
	
	protected Collection<? extends Point> getBuffer(){
		return buffer;
	}
	
	public SingleStreamDataset(Reader r){
		br = new BufferedReader(r);
	}
	
	public SingleStreamDataset(Reader r, String sep, GLColor color){
		this(r);
		this.color=color;
		this.separator = sep;
	}
}
