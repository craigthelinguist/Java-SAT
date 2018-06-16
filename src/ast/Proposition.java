package ast;

import semantics.Environment;

public interface Proposition {

    public Proposition solve(Environment env);
    public boolean isLiteral();
    
}
