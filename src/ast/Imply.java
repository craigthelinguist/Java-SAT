package ast;

import semantics.Environment;

public class Imply implements Proposition {

    private Proposition left, right;
    
    public Imply(Proposition left, Proposition right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Proposition solve(Environment env) {
    	return new Or(new Not(this.left), this.right).solve(env);
    }

    @Override
    public boolean isLiteral() {
        return false;
    }
    
    @Override
    public String toString() {
        return "(imply " + this.left + " " + this.right + ")";
    }

}
