package Nodes;

import main.JottTree;
import main.Token;

import java.util.ArrayList;

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
		IdNode id = IdNode.parseIdNode(tokens);
		if (id != null) {
			ParameterNode params = ParameterNode.parseParameterNode(tokens);
			if (params != null) {
				return new FunctionCallNode(id, params);
			}
			return new FunctionCallNode(id);
		}
        return null;
    }

    @Override
    public String convertToJott() {
		if (this.id != null) {
			if (this.params != null) {
				return this.id.convertToJott() + this.params.convertToJott();
			}
			return this.id.convertToJott();
		}
        return null;
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
