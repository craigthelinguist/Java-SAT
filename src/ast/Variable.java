package ast;

import repl.Utils.Pair;
import semantics.Environment;

public class Variable implements Proposition {

    private String name;
    
    public Variable(String name) {
        if (name.contains(" ")) {
            throw new RuntimeException("Variable cannot have a space in its name.");
        }
        this.name = name;
    }
    
    @Override
    public Proposition solve(Environment env) {
    	Proposition prop = this;
    	while (prop instanceof Variable && env.exists((Variable)prop)) {
    		prop = env.getVar((Variable)prop);
    	}
    	if (prop == this) {
    		return prop;
    	} else {
    		return prop.solve(env);
    	}
    }

    @Override
    public boolean isLiteral() {
        return true;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

	@Override
	public Proposition negationNormalForm(Environment env) {
		if (env.exists(this)) {
			return env.getVar(this).negationNormalForm(env);
		} else {
			return this;
		}
	}

	@Override
	public Proposition conjunctiveNormalForm(Environment env) {
		if (env.exists(this)) {
			return env.getVar(this).conjunctiveNormalForm(env);
		} else {
			return this;
		}
	}

}
