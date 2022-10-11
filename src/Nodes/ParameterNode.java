package Nodes;

import main.JottTree;
import main.Token;
import java.util.ArrayList;

public class ParameterNode implements JottTree {

	private final ExpressionNode expr;
	private final ParameterTailNode params_t;

	// <expr><params_t>
	public ParameterNode(ExpressionNode expr, ParameterTailNode params_t) {
		this.expr = expr;
		this.params_t = params_t;
	}

	// epsilon case
    public ParameterNode() {
		this.expr = null;
		this.params_t = null;
    }
	
	// TODO check if this is the correct handling of epsilon case
	public static ParameterNode parseParameterNode(ArrayList<Token> tokens) throws Exception {
		ExpressionNode expr = ExpressionNode.parseExpressionNode(tokens);
		if (expr != null) {
			ParameterTailNode params_t = ParameterTailNode.parseParameterTailNode(tokens);
			if (params_t != null) {
				return new ParameterNode(expr, params_t);
			}
			throw new Exception("Error: expected <params_t>");
		}
		// if there are infinite loops this may be the cause
		return new ParameterNode();
	}

    @Override
    public String convertToJott() {
		if (this.expr != null && this.params_t != null) {
			return this.expr.convertToJott() + this.params_t.convertToJott();
		}
        return null; // does this cover the epsilon case?
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
