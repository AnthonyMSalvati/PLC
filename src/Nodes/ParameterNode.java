package Nodes;

import main.InvalidValidateException;
import main.JottTree;
import main.SymbolTable;
import main.Token;
import java.util.ArrayList;

/**
* @author Ian Paap-Gray
*/
public class ParameterNode implements JottTree {

	private final ExpressionNode expr;
	private final ParameterTailNode params_t;
	private final Token lastToken;

	private String functionName = "";

	// <expr><params_t>
	public ParameterNode(ExpressionNode expr, ParameterTailNode params_t, Token token) {
		this.expr = expr;
		this.params_t = params_t;
		this.lastToken = token;
	}
	
	public static ParameterNode parseParameterNode(ArrayList<Token> tokens) throws Exception {
		Token token;
		ExpressionNode expr = ExpressionNode.parseExpressionNode(tokens);
		if (expr != null) {
			token = tokens.get(0);
			ParameterTailNode params_t = ParameterTailNode.parseParameterTailNode(tokens);
			if (params_t != null) {
				token = tokens.get(0);
				return new ParameterNode(expr, params_t, token);
			}
			return new ParameterNode(expr, null, token);
		}
		return null;
	}

	public int getParamLength() {
		if (this.expr != null) {
			if (this.params_t != null) {
				return params_t.getParamLength(1);
			}
			return 1;
		}
		return 0;
	}

	public void setFunctionName (String functionName) {
		this.functionName = functionName;
	}

    @Override
    public String convertToJott() {
		if (this.expr != null) {
			if (this.params_t != null) {
				return this.expr.convertToJott() + this.params_t.convertToJott();
			}
			return this.expr.convertToJott();
		}
        return "";
    }

    @Override
    public String convertToJava() {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
		if (this.expr != null) {
			if (this.params_t != null) {
				return ", " + this.expr.convertToPython(nestLevel) + this.params_t.convertToPython(nestLevel);
			}
			return ", " + this.expr.convertToPython(nestLevel);
		}
		return "";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
		if (this.expr != null) {
			if (expr.getType(symbolTable).equals(symbolTable.getParamType(functionName, 0))) {
				if (this.params_t != null) {
					params_t.setParamIndex(1);
					params_t.setFunctionName(functionName);
					return this.expr.validateTree(symbolTable) && this.params_t.validateTree(symbolTable);
				}
				return this.expr.validateTree(symbolTable);
			}
			throw new InvalidValidateException("Parameter type does not match", this.lastToken.getFilename(), this.lastToken.getLineNum());
		}
		return true;
	}
}
