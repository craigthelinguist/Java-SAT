package ast;

import repl.Utils.Pair;
import semantics.Environment;

public class Or implements Proposition {

    private Proposition left, right;
    
    public Or(Proposition left, Proposition right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Proposition solve(Environment env) {
    	Proposition left = this.left.solve(env);
    	Proposition right = this.right.solve(env);
    	if (left.equals(Constant.True()) || right.equals(Constant.True())) {
    		return Constant.True(); 
    	} else if (left.equals(Constant.False()) && right.equals(Constant.False())) {
    		return Constant.False();
    	} else {
    		return new Or(left, right);
    	}
    }

    @Override
    public boolean isLiteral() {
        return false;
    }
    
    @Override
    public String toString() {
        return "(or " + this.left + " " + this.right + ")";
    }

	@Override
	public Proposition negationNormalForm(Environment env) {
		Proposition left = this.left.negationNormalForm(env);
		Proposition right = this.right.negationNormalForm(env);
		return new Or(left, right).solve(env);
	}
	
	public Proposition getLeft() {
		return this.left;
	}
    
	public Proposition getRight() {
		return this.right;
	}

	@Override
	public Proposition conjunctiveNormalForm(Environment env) {
		Proposition left = this.left.conjunctiveNormalForm(env);
		Proposition right = this.right.conjunctiveNormalForm(env);
		
		// Distribute disjunctions over conjunctions. In the case where both arguments are conjunctions, it
		// doesn't matter which one we distribute over, you'll get the same result.
		if (left instanceof And) {
			And and = (And) left;
			return new And(new Or(and.getLeft(), right), new Or(and.getRight(), right))
					.conjunctiveNormalForm(env);
		} else if (right instanceof And) {
			And and = (And) right;
			return new And(new Or(left, and.getLeft()), new Or(left, and.getRight()))
					.conjunctiveNormalForm(env);
		} else {
			return new Or(left, right).solve(env);
		}
	}

}
