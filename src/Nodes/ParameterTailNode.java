package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import main.InvalidParseException;

import java.util.ArrayList;

/**
* @author Ian Paap-Gray
*/
public class ParameterTailNode implements JottTree {

	private final ExpressionNode expr;
	private final ParameterTailNode params_t;

	// ,<expr><params_t>
	public ParameterTailNode(ExpressionNode expr, ParameterTailNode params_t) {
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
				throw new InvalidParseException("Error: expected <params_t>", 
				tokens.get(0).getFilename(), tokens.get(0).getLineNum());
			}
			throw new InvalidParseException("Error: expected <expr>", 
			tokens.get(0).getFilename(), tokens.get(0).getLineNum());
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
		if (this.expr != null) { // if expr is not null then params_t cannot be null
			return this.expr.validateTree(symbolTable) && this.params_t.validateTree(symbolTable);
		}
		// epsilon instance case
        return true;
    }
}
