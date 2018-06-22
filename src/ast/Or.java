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

}
