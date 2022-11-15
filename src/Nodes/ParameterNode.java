package Nodes;

import main.JottTree;
import main.Token;
import java.util.ArrayList;

/**
* @author Ian Paap-Gray
*/
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
	
	public static ParameterNode parseParameterNode(ArrayList<Token> tokens) throws Exception {
		ExpressionNode expr = ExpressionNode.parseExpressionNode(tokens);
		if (expr != null) {
			ParameterTailNode params_t = ParameterTailNode.parseParameterTailNode(tokens);
			if (params_t != null) {
				return new ParameterNode(expr, params_t);
			}
			return new ParameterNode(expr, null);
		}
		return null;
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
    public String convertToPython() { //Ian
		if (this.expr != null) { // if expr exists, params_t must exist
			return ", " + this.expr.convertToPython() + this.params_t.convertToPython();
		}
        return "";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
		// any issues here are also in ParameterTailNode, as the functions are identical
		if (this.expr != null) { // if expr is not null then params_t cannot be null
			return this.expr.validateTreeTree(symbolTable) && this.params_t.validateTree(symbolTable);
		}
		// epsilon instance case
        return true;
    }
}
