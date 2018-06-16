package ast;

import semantics.Environment;

public class Or implements Proposition {

    private Proposition left, right;
    
    public Or(Proposition left, Proposition right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Proposition solve(Environment env) {
        if (left.solve(env).equals(Constant.True()) || right.solve(env).equals(Constant.True())) {
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
        return "(or " + this.left + " " + this.right + ")";
    }

}
