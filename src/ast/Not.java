package ast;

import semantics.Environment;

public class Not implements Proposition {

    private Proposition prop;

    public Not(Proposition prop) {
        this.prop = prop;
    }
    
    @Override
    public Proposition solve(Environment env) {
        Proposition a2 = this.prop.solve(env);
        if (a2 instanceof Constant) {
            if (a2.equals(Constant.True())) {
                return Constant.False();
            } else {
                return Constant.True();
            }
        } else {
            return new Not(a2);
        }
    }

    @Override
    public boolean isLiteral() {
        return this.prop.isLiteral();
    }
    
    @Override
    public String toString() {
        return "(not " + this.prop + ")";
    }
   
}
