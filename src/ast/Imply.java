package ast;

import repl.Utils.Pair;
import semantics.Environment;

public class Imply implements Proposition {

    private Proposition left, right;
    
    public Imply(Proposition left, Proposition right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Proposition solve(Environment env) {
    	return this.asOr().solve(env);
    }

    @Override
    public boolean isLiteral() {
        return false;
    }
    
    @Override
    public String toString() {
        return "(imply " + this.left + " " + this.right + ")";
    }

	@Override
	public Proposition negationNormalForm(Environment env) {
		return this.asOr().negationNormalForm(env);
	}

	@Override
	public Proposition conjunctiveNormalForm(Environment env) {
		return this.asOr().conjunctiveNormalForm(env);
	}
	
	@Override
	public Proposition disjunctiveNormalForm(Environment env) {
		return this.asOr().disjunctiveNormalForm(env);
	}

	private Proposition asOr() {
		return new Or(new Not(this.left), this.right);
	}
	
}
