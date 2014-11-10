package main;

import geom.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class SingleStreamDataset extends Dataset {
	private static final long serialVersionUID = -1074138053765911487L;
	
	BufferedReader br;
	String separator = null;
	boolean trim = false;
	
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
		while(line!=null){
			if(trim)
				line = line.trim();
			double x = this.size(), y=0;
			if(separator!=null){
				String[] pair = line.split(this.separator);
				assert pair.length>=2;
				x = Double.parseDouble(pair[0]);
				line = pair[1];
			}
			y=Double.parseDouble(line);
			this.add(new Point(x,y));
			line=getLine();
		}
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
