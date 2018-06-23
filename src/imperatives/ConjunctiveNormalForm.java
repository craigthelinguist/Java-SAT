package imperatives;

import ast.Proposition;
import semantics.Environment;

public class ConjunctiveNormalForm implements Imperative {

	private Proposition proposition;
	
	public ConjunctiveNormalForm(Proposition proposition) {
		this.proposition = proposition;
	}
	
	@Override
	public String exec(Environment env) {
		return this.proposition.conjunctiveNormalForm(env).toString();
	}

}
