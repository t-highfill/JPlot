package main.args;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import main.AliasRecursionException;

public abstract class ArgsProcessor {
	protected Map<String,String> aliases = new HashMap<String, String>();
	protected List<ArgMatcher> knownArgs = new LinkedList<ArgMatcher>();
	
	public ArgsProcessor(){
		
	}
	public ArgsProcessor(String[][] aliases){
		for(String[] pair: aliases){
			assert pair.length>=2;
			this.aliases.put(pair[0], pair[1]);
		}
		
	}
	
	public void process(String[] args) throws AliasRecursionException{
		for(String arg : args){
			this.process(arg);
		}
	}
	
	protected void handleAssignment(String var, String val){}
	protected void handleFlag(char flag){}
	protected void handleGenericArg(String arg){}
	
	private void process(String arg) throws AliasRecursionException{
		arg=this.resolveAlias(arg);
		for(ArgMatcher argument : this.knownArgs){
			if(argument.matches(arg)){
				argument.process(arg);
				return;
			}
		}
		System.err.println("Unhandled argument: "+arg);
	}
	
	public String resolveAlias(String arg) throws AliasRecursionException{
		Stack<String> backTrace = new Stack<String>();
		while(aliases.containsKey(arg)){
			backTrace.add(arg);
			arg=aliases.get(arg);
			if(backTrace.search(arg)>=0){
				String trace = backTrace.pop();
				while(!backTrace.empty()){
					trace = String.format("%s => %s", backTrace.pop(), trace);
				}
				throw new AliasRecursionException("Infinite alias recursion!\t"+trace);
			}
		}
		return arg;
	}
	
}
