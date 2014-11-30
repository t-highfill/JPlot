package main;

import geom.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Queue;

public class SingleStreamDataset extends Dataset {
	private static final long serialVersionUID = -1074138053765911487L;
	
	BufferedReader br;
	String separator = null;
	boolean trim = false;
	Queue<Point> buffer = new LinkedList<Point>();
	
	private String getLine(){
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	private void normalProcess(){
		String line = getLine();
		for(double linenum=0;line!=null;++linenum){
			JPlot.DEBUG.println("reading: "+line);
			double x = linenum;
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
	
	private void binaryProcess() throws IOException{
		for(int chr=br.read(), i=0; chr>=0; chr=br.read()){
//			while(this.isFlushing()){}
			while(copying){}
			buffer.add(new Point(i, chr));
			++i;
		}
	}
	
	protected void read(){
		if(JPlot.BINARY_MODE.getVal()){
			try {
				binaryProcess();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(e.getMessage().hashCode());
			}
		}else{
			normalProcess();
		}
	}
	
	private boolean copying = false;
	protected Queue<? extends Point> getBuffer(){
//		while(copying){}
//		copying=true;
//		Queue<? extends Point> res = new LinkedList<Point>(buffer);
//		copying=false;
//		return res;
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
