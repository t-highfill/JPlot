package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ArgsProcessor {
	
	Map<String,Object> variables = new HashMap<String, Object>();
	Map<String,String> aliases = new HashMap<String, String>();
	
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
	
	private void process(String arg) throws AliasRecursionException{
		arg = this.resolveAlias(arg);
		if(arg.startsWith("--")){
			String var = null;
			if(arg.contains("=")){
				var = arg.substring(2,arg.indexOf("="));
			}else{
				var = arg.substring(2);
			}
			
		}
	}
	
	private String resolveAlias(String arg) throws AliasRecursionException{
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
