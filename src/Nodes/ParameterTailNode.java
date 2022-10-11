package Nodes;

import main.JottTree;
import main.Token;
import java.util.ArrayList;

/**
* @author Ian Paap-Gray
*/
public class ParameterTailNode implements JottTree {

	private final ExpressionNode expr;
	private final ParameterTailNode params_t;

	// ,<expr><params_t>
	public ParameterNode(ExpressionNode expr, ParameterTailNode params_t) {
		this.expr = expr;
		this.params_t = params_t;
	}

	// epsilon case
    public ParameterTailNode() {
		this.expr = null;
		this.params_t = null;
    }
	
	public static ParameterTailNode parseParameterTailNode(ArrayList<Token> tokens) throws Exception {
		if (tokens.get(0).getTokenType() == TokenType.COMMA) {
			tokens.remove(0); // following StringLiteralNode for example
			ExpressionNode expr = ExpressionNode.parseExpressionNode(tokens);
			if (expr != null) {
				ParameterTailNode params_t = ParameterTailNode.parseParameterTailNode(tokens);
				if (params_t != null) {
					return new ParameterTailNode(expr, params_t);
				}
				throw new Exception("Error: expected <params_t>");
			}
			throw new Exception("Error: expected <expr>");
		}
		// again, make sure this doesnt have infinite loop issues
		return new ParameterTailNode();
	}

    @Override
    public String convertToJott() {
		if (this.expr != null && this.params_t != null) {
			return this.expr.convertToJott() + this.params_t.convertToJott();
		}
		/* in ParameterNode, this returns null, but here I have it return
		an empty string. I believe one of those will cause an issue, but
		the other one should be correct. I hope, at least. The epsilon
		case is confusing. */
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
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
