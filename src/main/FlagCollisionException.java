package main;

import java.util.Arrays;

import main.args.Flag;

public class FlagCollisionException extends RuntimeException {
	private static final long serialVersionUID = -7962697670229430936L;
	
	private static String[] getNames(Flag[] flags){
		String[] res = new String[flags.length];
		for(int i=0;i<res.length;i++){
			res[i]=flags[i].getName();
		}
		return res;
	}
	
	public FlagCollisionException(Flag...flags){
		super(String.format("Flags %s have collided", Arrays.toString(getNames(flags))));
		assert flags.length>=2;
	}
}
