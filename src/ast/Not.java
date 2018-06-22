package ast;

import repl.Utils.Pair;
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

	@Override
	public Proposition negationNormalForm(Environment env) {
		
		Proposition prop = this.prop.negationNormalForm(env);
		
		// Double negation.
		if (prop instanceof Not) {
			Not not = (Not) prop;
			return not.prop.negationNormalForm(env);
		}
		
		// DeMorgan's law 1.
		if (prop instanceof Or) {
			Or or = (Or) prop;
			Proposition left = new Not(or.getLeft()).negationNormalForm(env);
			Proposition right = new Not(or.getRight()).negationNormalForm(env);
			return new And(left, right).negationNormalForm(env);
		}
		
		// DeMorgan's law 2.
		if (prop instanceof And) {
			And and = (And) prop;
			Proposition left = new Not(and.getLeft()).negationNormalForm(env);
			Proposition right = new Not(and.getRight()).negationNormalForm(env);
			return new Or(left, right).negationNormalForm(env);
		}
		
		return new Not(prop);
		
	}

}
