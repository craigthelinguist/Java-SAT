package imperatives;

import ast.Proposition;
import ast.Variable;
import semantics.Environment;

public class Print implements Imperative {

    private Proposition prop;
    
    public Print(Proposition prop) {
        this.prop = prop;
    }
    
    @Override
    public String exec(Environment env) {
    	// TODO: refactor using an observer
    	if (prop instanceof Variable) {
    		Variable var = (Variable) prop;
    		if (!env.exists(var)) {
    			return "Variable `" + var.toString() + "` is undefined";
    		} else {
    			return env.getVar(var).toString();
    		}
    	} else {
    		return this.prop.toString();
    	}
    }

}
