package ast;

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
        if (env.exists(this.name)) {
            return env.getVar(this.name).solve(env);
        } else {
            return this;
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

}
