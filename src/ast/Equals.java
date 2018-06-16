package ast;

import semantics.Environment;

public class Equals implements Proposition {

    private Proposition left, right;
    
    public Equals(Proposition left, Proposition right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Proposition solve(Environment env) {
        if (left.solve(env).equals(right.solve(env))) {
            return Constant.True();
        } else {
            return Constant.False();
        }
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    public String toString() {
        return "(eq " + this.left + " " + this.right + ")";
    }
    
}
