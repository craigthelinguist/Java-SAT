package imperatives;

import ast.Variable;
import semantics.Environment;

public class Forget implements Imperative {

	private Variable variable;
	
	public Forget(String name) {
		this.variable = new Variable(name);
	}
	
	@Override
	public String exec(Environment env) {
		if (env.exists(this.variable)) {
			env.deleteVar(this.variable);
			return "";
		} else {
			return "Variable `" + this.variable + "` is undefined";
		}
	}

}
