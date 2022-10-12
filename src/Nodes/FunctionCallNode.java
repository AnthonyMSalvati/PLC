package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

/**
* @author Ian Paap-Gray
*/
public class FunctionCallNode implements JottTree {

	private final IdNode id;
	private final ParameterNode params;
	
	public FunctionCallNode(IdNode id) {
		this.id = id;
		this.params = null;
	}

    public FunctionCallNode(IdNode id, ParameterNode params) {
		this.id = id;
		this.params = params;
    }

    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.size() > 1) {
            if (tokens.get(1).getTokenType() == TokenType.L_BRACKET) {
                IdNode id = IdNode.parseIdNode(tokens);
                if (id != null) {
                    if (tokens.get(0).getTokenType() == TokenType.L_BRACKET) {
                        tokens.remove(0);
                        ParameterNode params = ParameterNode.parseParameterNode(tokens);
                        if (params != null) {
                            if (tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
                                tokens.remove(0);
                                return new FunctionCallNode(id, params);
                            }
                            throw new Exception("Error: expected R_BRACKET");
                        }
                        if (tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
                            tokens.remove(0);
                            return new FunctionCallNode(id);
                        }
                        throw new Exception("Error: expected R_BRACKET");
                    }
                    throw new Exception("Error: expected L_BRACKET");
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
		if (this.id != null) {
			if (this.params != null) {
				return this.id.convertToJott() + "[" +
                        this.params.convertToJott() + "]";
			}
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
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
