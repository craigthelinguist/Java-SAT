package imperatives;

import ast.Proposition;
import semantics.Environment;

public class Let implements Imperative {

	private String name;
	private Proposition definition;
	
	public Let(String name, Proposition definition) {
		this.name = name;
		this.definition = definition;
	}
	
	@Override
	public String exec(Environment env) {
		env.put(this.name, this.definition);
		return definition.toString();
	}

}
