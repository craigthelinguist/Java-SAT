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
        if (this.left.solve(env).equals(Constant.False()) || this.right.solve(env).equals(Constant.True())) {
            return Constant.True();
        } else {
            return Constant.False();
        }
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
