package main.args;

import java.io.File;
import java.util.Arrays;

public class FileLoop extends ArgLoop<FileOption, File> {

	public FileLoop(String name, boolean optional) {
		super(name, optional,new FileOption(name, null));
	}

	public FileLoop(String name) {
		super(name, new FileOption(name, null));
	}
	
	public File[] getFiles(){
		return this.results.toArray(new File[results.size()]);
	}
	public String toString(){
		return this.getClass().getName()+": "+Arrays.toString(this.getFiles());
	}
}
