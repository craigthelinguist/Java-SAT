package ast;

import repl.Utils.Pair;
import semantics.Environment;

public class And implements Proposition {

    private Proposition left, right;
    
    public And(Proposition left, Proposition right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Proposition solve(Environment env) {
    	Proposition left = this.left.solve(env);
    	Proposition right = this.right.solve(env);
    	if (left.equals(Constant.True()) && right.equals(Constant.True())) {
    		return Constant.True();
    	} else if (left.equals(Constant.False()) || right.equals(Constant.False())) {
    		return Constant.False();
    	} else {
    		return new And(left, right);
    	}
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    @Override
    public String toString() {
        return "(and " + this.left + " " + this.right + ")";
    }

	@Override
	public Proposition negationNormalForm(Environment env) {
		Proposition left = this.left.negationNormalForm(env);
		Proposition right = this.right.negationNormalForm(env);
		return new And(left, right).solve(env);
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
		return new And(left, right).solve(env);
	}
	
	@Override
	public Proposition disjunctiveNormalForm(Environment env) {
		Proposition left = this.left.disjunctiveNormalForm(env);
		Proposition right = this.right.disjunctiveNormalForm(env);
		
		// Distribute conjunctions over disjunctions. In the case where both arguments are disjunctions, it
		// doesn't matter which one we distribute over, we'll get the same result.
		if (left instanceof Or) {
			Or or = (Or) left;
			return new Or(new And(or.getLeft(), right), new And(or.getRight(), right))
					.disjunctiveNormalForm(env);
		} else if (right instanceof Or) {
			Or or = (Or) right;
			return new Or(new And(left, or.getLeft()), new Or(left, or.getRight()))
					.disjunctiveNormalForm(env);
		} else {
			return new And(left, right).solve(env);
		}
	}


}
