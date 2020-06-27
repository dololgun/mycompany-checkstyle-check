package com.mycompany.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * we are not allowed to use UTF-8 
 * <p>
 * 
 * 
 * <pre>
 * For Linux/Unix OS:
java -classpath mycompanychecks.jar:checkstyle-${projectVersion}-all.jar \
    com.puppycrawl.tools.checkstyle.Main -c config.xml myproject
    
For Windows OS:
java -classpath mycompanychecks.jar;checkstyle-${projectVersion}-all.jar ^
    com.puppycrawl.tools.checkstyle.Main -c config.xml myproject
 * </pre>
 * 
 * @author dololgun
 *
 */
public class MethodLimitCheck extends AbstractCheck {
	
	private static final int DEFAULT_MAX = 100;
	private int max = DEFAULT_MAX;

	@Override
	public int[] getDefaultTokens() {

		return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
	}

	@Override
	public int[] getAcceptableTokens() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRequiredTokens() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visitToken(DetailAST ast) {

		System.out.println("===========visit ===============" + ast.toString());

		// find the OBJBLOCK node below the CLASS_DEF/INTERFACE_DEF
		DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);

		// count the number of direct children of the OBJBLOCK
		// that are METHOD_DEFS
		int methodDefs = objBlock.getChildCount(TokenTypes.METHOD_DEF);

		// report violation if limit is reached
		if (methodDefs > this.max) {
			String message = "too many methods, only " + this.max + " are allowed";
			log(ast.getLineNo(), message);
		}
	}

	public void setMax(int limit) {

		max = limit;
	}

}
