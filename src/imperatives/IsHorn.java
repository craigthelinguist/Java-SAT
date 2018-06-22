package imperatives;

import ast.Proposition;
import semantics.Environment;

public class IsHorn implements Imperative {

	private Proposition proposition;
	
	public IsHorn(Proposition proposition) {
		this.proposition = proposition;
	}

	@Override
	public String exec(Environment env) {
		return this.proposition.isHornClause(env) ? "true" : "false";
	}

}
