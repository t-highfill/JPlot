package main.args;

import java.io.File;
import java.util.Arrays;

public class FileLoop extends ArgLoop<GenericArgument, String> {

	public FileLoop(String name, boolean optional) {
		super(name, optional,new GenericArgument(name, null));
	}

	public FileLoop(String name) {
		super(name, new GenericArgument(name, null));
	}
	
	public File[] getFiles(){
		File[] res = new File[this.results.size()];
		int i =0;
		for(String fname : this.results){
			res[i] = new File(fname);
			++i;
		}
		return res;
	}
	public String toString(){
		return this.getClass().getName()+": "+Arrays.toString(this.getFiles());
	}
}
