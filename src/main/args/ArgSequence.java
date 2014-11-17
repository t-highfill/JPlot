package main.args;

import java.util.HashMap;
import java.util.List;

import main.JPlot;

public abstract class ArgSequence<E extends AbstractArgMatcher> extends AbstractArgMatcher{

	private E current;
	private boolean done = false;
	private HashMap<String, Object> results = new HashMap<String, Object>();
	private HashMap<String, E> matchers = new HashMap<String, E>();
	protected int idx = -1;

	public ArgSequence(String name) {
		super(name);
	}
	
	public ArgSequence(String name, boolean optional) {
		super(name, optional);
	}
	
	protected abstract List<E> getList();
	
	private void putResults(Object obj){
		results.put(current.getName(), obj);
		matchers.put(current.getName(), current);
	}
	
	protected boolean hasNext(){
		return idx+1 < getList().size();
	}
	private E forceNext(){
		JPlot.DEBUG.println("Forcing next");
		current=null;
		return next();
	}
	protected E next(){
		if(hasNext() && (current==null || current.isDone())){
			E e = getList().get(++idx);
			JPlot.DEBUG.println(current+"=>"+e);
			return current = e;
		}
		JPlot.DEBUG.println("Current unchanged");
		return current;
	}

	@Override
	public boolean matches(String arg) {
		JPlot.DEBUG.println("ArgSequence testing: "+arg);
		next();
		if(done){
			JPlot.DEBUG.println("ArgSequence is done.");
			return false;
		}
		if(current.matches(arg)){
			JPlot.DEBUG.println("Success: "+arg);
			return true;
		}else if(current.isOptional() && hasNext()){
			forceNext();
			return matches(arg);
		}
		JPlot.DEBUG.println("current.isOptional()="+current.isOptional()+"\nhasNext()="+hasNext());
		throw new MissedArgumentException(current.getName(), arg);
	}

	@Override
	protected void processArg(String arg) {
		JPlot.DEBUG.println("Processing: "+arg);
		assert this.matches(arg);
		current.process(arg);
		if(current instanceof VariableArgument){
			VariableArgument<?> var = (VariableArgument<?>) current;
			this.putResults(var.val);
		}else{
			this.putResults(null);
		}
		next();
	}
	
	public void showResults(){
		System.out.println(this.toString());
	}

	public HashMap<String, Object> getResults() {
		return new HashMap<String, Object>(results);
	}
	
	public String toString(){
		String res = this.getClass().getName()+"{";
		for(String name : this.results.keySet()){
			Object obj =this.results.get(name);
			if(obj!=null)
				res+="\n\t"+name+':'+obj;
			res+="\n\t"+name+':'+this.matchers.get(name);
		}
		return res+"}";
	}

}
