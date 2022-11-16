package Nodes;

import main.*;

import java.util.ArrayList;

public class ElseIfStatementNode implements JottTree {

    private final BooleanExpressionNode booleanExpressionNode;
    private final BodyNode bodyNode;
    private final ElseIfStatementNode elseIfStatementNode;

    public ElseIfStatementNode(BooleanExpressionNode booleanExpressionNode, BodyNode bodyNode, ElseIfStatementNode elseIfStatementNode)
    {
        this.booleanExpressionNode = booleanExpressionNode;
        this.bodyNode = bodyNode;
        this.elseIfStatementNode = elseIfStatementNode;
    }

    public ElseIfStatementNode()
    {
        this.booleanExpressionNode = null;
        this.bodyNode = null;
        this.elseIfStatementNode = null;
    }

    public static ElseIfStatementNode parseElseIfStatementNode(ArrayList<Token> tokens) throws Exception {
        Token token;
        token = tokens.get(0);
        if (!(token.getToken().equals("elseif"))) {
            return new ElseIfStatementNode();
        } else {
            tokens.remove(0);
            token = tokens.get(0);
            if (!(token.getTokenType() == TokenType.L_BRACKET)) {
                throw new InvalidParseException("Error: expected \"[\"", token.getFilename(),
                        token.getLineNum());
            } else {
                tokens.remove(0);
                BooleanExpressionNode booleanExpressionNode = BooleanExpressionNode.parseBooleanExpressionNode(tokens);

                if (booleanExpressionNode != null) {
                    token = tokens.get(0);
                    if (!(token.getTokenType() == TokenType.R_BRACKET)) {
                        throw new InvalidParseException("Error: expected \"]\"", token.getFilename(),
                                token.getLineNum());
                    } else {
                        tokens.remove(0);
                        token = tokens.get(0);
                        if (!(token.getTokenType() == TokenType.L_BRACE)) {
                            throw new InvalidParseException("Error: expected \"{\"", token.getFilename(),
                                    token.getLineNum());
                        } else {
                            tokens.remove(0);
                            BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

                            if (bodyNode != null) {
                                token = tokens.get(0);
                                if (!(token.getTokenType() == TokenType.R_BRACE)) {
                                    throw new InvalidParseException("Error: expected \"}\"", token.getFilename(),
                                            token.getLineNum());
                                } else {
                                    tokens.remove(0);
                                    ElseIfStatementNode elseIfStatementNode = ElseIfStatementNode.parseElseIfStatementNode(tokens);

                                    return new ElseIfStatementNode(booleanExpressionNode, bodyNode, elseIfStatementNode);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (bodyNode != null) {
            if (booleanExpressionNode != null) {
                if (elseIfStatementNode != null) {
                    return "elseif[" + this.booleanExpressionNode.convertToJott() + "]{" +
                            this.bodyNode.convertToJott() + "}" + this.elseIfStatementNode.convertToJott();
                }
            }
        }
        return "";
    }

    @Override
    public String convertToJava() {
        if (this.bodyNode == null){
            return "";
        }
        else {
            return "else if (" + this.booleanExpressionNode.convertToJava() + "){" + bodyNode.convertToJava() + "}" + this.elseIfStatementNode.convertToJava();
        }
    }

    @Override
    public String convertToC() {
        if (this.bodyNode == null){
            return "";
        }
        else {
            return "else if (" + booleanExpressionNode.convertToC() + "){" + bodyNode.convertToC() + "}" + this.elseIfStatementNode.convertToC();
        }
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
		if (this.booleanExpressionNode == null) {
			return "";
		}
		String nestIndent = "";
		for (int i=0;i<nestLevel;i++) {
			nestIndent = nestIndent + "\t";
		}
		return nestIndent + "elif ("
			+ this.booleanExpressionNode.convertToPython(nestLevel) + "):\n"
			+ this.bodyNode.convertToPython(nestLevel + 1) + "\n"
			+ (this.elseIfStatementNode != null ? this.elseIfStatementNode.convertToPython(nestLevel):"");
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (this.elseIfStatementNode == null){
            return true;
        }
        else if (this.elseIfStatementNode.validateTree(symbolTable)){
            if (this.booleanExpressionNode == null){
                return false; //if first was null, any nulls after that indicate an error
            }
            else if (this.booleanExpressionNode.validateTree(symbolTable)){
                if (this.bodyNode == null){
                    return false;
                }
                else return this.bodyNode.validateTree(symbolTable);
            }
        }

        return false;
    }
}
