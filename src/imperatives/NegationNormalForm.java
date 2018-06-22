package imperatives;

import ast.Proposition;
import semantics.Environment;

public class NegationNormalForm implements Imperative {

	private Proposition prop;
	
	public NegationNormalForm(Proposition prop) {
		this.prop = prop;
	}
	
	@Override
	public String exec(Environment env) {
		return this.prop.negationNormalForm(env).toString();
	}

}
