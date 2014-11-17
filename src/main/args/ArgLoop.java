package main.args;

import java.util.LinkedList;
import java.util.List;

import main.JPlot;

public class ArgLoop<E extends VariableArgument<F>, F> extends AbstractArgMatcher  implements ContinuousMatcher{

	E arg;
	List<F> results = new LinkedList<F>();
	private boolean done = false;

	public ArgLoop(String name, boolean optional, E arg) {
		super(name, optional);
		this.arg = arg;
	}

	public ArgLoop(String name, E arg) {
		super(name);
		this.arg = arg;
	}

	@Override
	public boolean matches(String arg) {
		JPlot.DEBUG.println("Argloop testing: "+arg);
		if(done){
			JPlot.DEBUG.println("This argloop is done");
			return false;
		}
		boolean match = this.arg.matches(arg);
		done = !match;
		return match;
	}

	@Override
	protected void processArg(String arg) {
		assert this.matches(arg);
		this.arg.process(arg);
		results.add(this.arg.val);
	}

	@Override
	protected int getUses() {
		if(done)
			return 0;
		return 100000; //To be safe
	}

	@Override
	protected void decrUses() {
		//Nothing!
	}
	
	public boolean isDone(){
		return done;
	}
}
