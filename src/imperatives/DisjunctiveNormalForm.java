package imperatives;

import ast.Proposition;
import semantics.Environment;

public class DisjunctiveNormalForm implements Imperative {

	private Proposition proposition;
	
	public DisjunctiveNormalForm(Proposition proposition) {
		this.proposition = proposition;
	}
	
	@Override
	public String exec(Environment env) {
		return this.proposition.disjunctiveNormalForm(env).toString();
	}

}
