package semantics;

import java.util.HashMap;
import java.util.Map;

import ast.Proposition;

public class Environment {

    private Map<String, Proposition> variables;

    public Environment () {
        this.variables = new HashMap<>();
    }

    public boolean exists (String name) {
        return variables.containsKey(name);
    }

    public Proposition getVar (String name) {
        return variables.get(name);
    }

    public void put (String name, Proposition prop) {
        this.variables.put(name, prop);
    }

    public void deleteVar (String name) {
        variables.remove(name);
    }

}
