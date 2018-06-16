package imperatives;

import ast.Proposition;
import semantics.Environment;

public class Print implements Imperative {

    private Proposition prop;
    
    public Print(Proposition prop) {
        this.prop = prop;
    }
    
    @Override
    public String exec(Environment env) {
        return this.prop.toString();
    }

}
