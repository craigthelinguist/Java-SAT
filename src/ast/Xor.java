package ast;

import semantics.Environment;

public class Xor implements Proposition {

    private Proposition left, right;
    
    public Xor(Proposition left, Proposition right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Proposition solve(Environment env) {
        if (!this.left.solve(env).equals(this.right.solve(env))) {
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
        return "(xor " + this.left + " " + this.right + ")";
    }

}
