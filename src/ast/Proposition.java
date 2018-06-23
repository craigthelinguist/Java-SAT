package ast;

import java.util.ArrayDeque;
import java.util.Deque;

import semantics.Environment;

public interface Proposition {

    public Proposition solve(Environment env);
    public boolean isLiteral();
	public Proposition negationNormalForm(Environment env);
	public Proposition conjunctiveNormalForm(Environment env);
	public Proposition disjunctiveNormalForm(Environment env);
	
	default public boolean isHornClause(Environment env) {
		
			Proposition prop = this.negationNormalForm(env);
			
			Deque<Proposition> stack = new ArrayDeque<>();
			boolean alreadySeenNegativeLiteral = false;
			
			stack.push(prop);
			
			while (!stack.isEmpty()) {
				prop = stack.pop();
				if (prop instanceof Variable) {
					if (env.exists((Variable)prop)) {
						stack.push(env.getVar((Variable)prop));
					}
				} else if (prop.isLiteral() && prop instanceof Not) {
					if (alreadySeenNegativeLiteral) {
						return false;
					} else {
						alreadySeenNegativeLiteral = true;
					}
				} else if (!(prop instanceof Or)) {
					return false;
				} else {
					Or or = (Or) prop;
					stack.push(or.getRight());
					stack.push(or.getLeft());	
				}
			}
			
			return true;
			
	}
	
}
