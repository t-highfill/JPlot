package main.args;

import java.util.LinkedList;
import java.util.List;

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
		if(done){
			return false;
		}
		boolean res = this.arg.matches(arg);
		if(!res){
			done=true;
		}
		return res;
	}

	@Override
	public void process(String arg) {
		assert this.matches(arg);
		this.arg.process(arg);
		results.add(this.arg.val);
	}

	@Override
	public boolean isDone() {
		return done;
	}
}
