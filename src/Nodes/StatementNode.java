package Nodes;

import main.JottTree;
import main.SymbolTable;
import main.Token;
import java.util.ArrayList;

/**
* @author Ian Paap-Gray
*/
public class StatementNode implements JottTree {

	private final AssignmentNode asmt;
	private final VariableDeclarationNode var_dec;
	private final FunctionCallNode func_call;
	private final EndStatementNode end_stmt;

	// <asmt>
    public StatementNode(AssignmentNode asmt) {
		this.asmt = asmt;
		this.var_dec = null;
		this.func_call = null;
		this.end_stmt = null;
    }
	
	// <var_dec>
    public StatementNode(VariableDeclarationNode var_dec) {
		this.asmt = null;
		this.var_dec = var_dec;
		this.func_call = null;
		this.end_stmt = null;
    }
	// <func_call><end_stmt>
    public StatementNode(FunctionCallNode func_call, EndStatementNode end_stmt) {
		this.asmt = null;
		this.var_dec = null;
		this.func_call = func_call;
		this.end_stmt =	end_stmt;
    }
	
	// used BooleanExpressionNode.java as a template
	public static StatementNode parseStatementNode (ArrayList<Token> tokens) throws Exception {
		AssignmentNode asmt = AssignmentNode.parseAssignmentNode(tokens);
		if (asmt != null) {
			return new StatementNode(asmt);
		}
		VariableDeclarationNode var_dec = VariableDeclarationNode.parseVariableDeclarationNode(tokens);
		if (var_dec != null) {
			return new StatementNode(var_dec);
		}
		FunctionCallNode func_call = FunctionCallNode.parseFunctionCallNode(tokens);
		if (func_call != null) {
			EndStatementNode end_stmt = EndStatementNode.parseEndStatementNode(tokens);
			return new StatementNode(func_call, end_stmt);
		}
		return null;
	}
	
    @Override
    public String convertToJott() {
		if (this.asmt != null) {
			return this.asmt.convertToJott();
		}
		if (this.var_dec != null) {
			return this.var_dec.convertToJott();
		}
		if (this.func_call != null && this.end_stmt != null) {
			return this.func_call.convertToJott() + this.end_stmt.convertToJott();
		}
        return "";
    }

    @Override
    public String convertToJava() { //Ian
		if (this.asmt != null) {
			return this.asmt.convertToJava();
		}
		if (this.var_dec != null) {
			return this.var_dec.convertToJava();
		}
		if (this.func_call != null && this.end_stmt != null) {
			return this.func_call.convertToJava() + this.end_stmt.convertToJava();
		}
        return "";
    }

    @Override
    public String convertToC() { //Ian
		if (this.asmt != null) {
			return this.asmt.convertToC();
		}
		if (this.var_dec != null) {
			return this.var_dec.convertToC();
		}
		if (this.func_call != null && this.end_stmt != null) {
			return this.func_call.convertToC() + this.end_stmt.convertToC();
		}
        return "";
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
		if (this.asmt != null) {
			return this.asmt.convertToPython(nestLevel);
		}
		if (this.var_dec != null) {
			return this.var_dec.convertToPython(nestLevel);
		}
		if (this.func_call != null) {
			return this.func_call.convertToPython(nestLevel)
				+ this.end_stmt.convertToPython(nestLevel);
		}
		return "\n";
	}

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
		if (this.asmt != null) {
			return this.asmt.validateTree(symbolTable);
		}
		if (this.var_dec != null) {
			return this.var_dec.validateTree(symbolTable);
		}
		if (this.func_call != null) { // end_stmt being null is linked to func_call
			return this.func_call.validateTree(symbolTable) && this.end_stmt.validateTree(symbolTable);
		}
		// code below this should never be reached, but is there just in case it is
		//throw new InvalidValidateException("Impossible scenario reached in StatementNode");
        return false;
    }
}
