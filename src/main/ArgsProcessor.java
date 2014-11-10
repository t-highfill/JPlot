package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public abstract class ArgsProcessor {
	
	Map<String,Object> variables = new HashMap<String, Object>();
	Map<String,String> aliases = new HashMap<String, String>();
	Queue<String> varsWaiting = new LinkedList<String>();
	List<String> knownVariables = new LinkedList<String>();
	String varFlags = "";
	
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
		if(!varsWaiting.isEmpty()){
			String var = varsWaiting.poll();
			this.variables.put(var, arg);
			this.handleAssignment(var, arg);
			return;
		}
		arg = this.resolveAlias(arg);
		if(arg.startsWith("--")){
			String var = null, val = ""+true;
			if(arg.contains("=")){
				int idx = arg.indexOf("=");
				var = arg.substring(2,idx);
				val = arg.substring(idx+1);
				this.variables.put(var, val);
				this.handleAssignment(var, val);
			}else{
				var = arg.substring(2);
				if(this.knownVariables.contains(var)){
					this.varsWaiting.add(var);
				}else{
					this.variables.put(var, val);
					this.handleAssignment(var, val);
				}
			}
		}else if(arg.startsWith("-")){//Test if it's a flag
			String flags = arg.substring(1);//strip the dash
			for(char c : flags.toCharArray()){//to handle multiple flags
				String var = this.resolveAlias("-"+c);//Find alias
				if(!var.equals(c+"")){//Test to see if there is an alias
					this.process(var);//resolve like that alias demands
				}else if(this.varFlags.contains(""+c)){//test to see if it's a known var flag
					this.varsWaiting.add(""+c);
				}else{
					this.variables.put(""+c, ""+true);
					this.handleFlag(c);
				}
			}
		}else{
			this.handleGenericArg(arg);
		}
	}
	
	public boolean isDefined(String var){
		return variables.get(var)!=null;
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
