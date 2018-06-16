package imperatives;

import ast.Proposition;
import semantics.Environment;

public class Solve implements Imperative {

    private Proposition prop;
    
    public Solve(Proposition prop) {
        this.prop = prop;
    }
    
    @Override
    public String exec(Environment env) {
        return this.prop.solve(env).toString();
    }

}
