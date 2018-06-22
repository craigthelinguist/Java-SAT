package ast;

import repl.Utils.Pair;
import semantics.Environment;

public class Constant implements Proposition {

    private boolean value;

    private Constant(boolean v) {
        this.value = v;
    }
    
    private static Constant TRUE = new Constant(true);
    private static Constant FALSE = new Constant(false);
    
    public static Constant True() {
        return TRUE;
    }
    
    public static Constant False() {
        return FALSE;
    }
    
    @Override
    public Proposition solve(Environment env) {
        return this;
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Constant)) {
            return false;
        } else {
            Constant c2 = (Constant) other;
            return c2.value == this.value;
        }
    }

	@Override
	public Proposition negationNormalForm(Environment env) {
		return this;
	}

}
