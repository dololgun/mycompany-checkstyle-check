package com.mycompany.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * 토큰과 관련된 4가지 매소드가 제공된다. setToken은 기본으로 제공되는 토큰 이외의 토큰을 사용하고자 할 때 쓰는 것이다. 나머지
 * 3개는 getter인데, <br>
 * getDefaultToken : visitToken 매소드에서 기본으로 사용하는 token sets을 리턴한다.<br>
 * getRequiredToken : Check가 수행되기 위해 필수로 필요한 token<br>
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
	
	private static final int DEFAULT_MAX = 2;
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

		System.out.println("===========visit===============");

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
