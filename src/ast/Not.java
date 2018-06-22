package ast;

import semantics.Environment;

public class Not implements Proposition {

    private Proposition prop;

    public Not(Proposition prop) {
        this.prop = prop;
    }
    
    @Override
    public Proposition solve(Environment env) {
    	Proposition prop = this.prop.solve(env);
    	if (prop.equals(Constant.True())) {
    		return Constant.False();
    	} else if (prop.equals(Constant.False())) {
    		return Constant.True();
    	} else {
    		return new Not(prop);
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
