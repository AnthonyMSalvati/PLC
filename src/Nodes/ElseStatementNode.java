package Nodes;

import main.*;

import java.util.ArrayList;

public class ElseStatementNode implements JottTree {

    private final BodyNode bodyNode;
    public ElseStatementNode(BodyNode bodyNode)
    {
        this.bodyNode = bodyNode;

    }

    public ElseStatementNode()
    {
        this.bodyNode = null;
    }

    public static ElseStatementNode parseElseStatementNode(ArrayList<Token> tokens) throws Exception
    {
        Token token;
        token = tokens.get(0);
        if (!(token.getToken().equals("else")))
        {
            return new ElseStatementNode();
        }
        else
        {
            tokens.remove(0);
            token = tokens.get(0);
            if (!(token.getTokenType() == TokenType.L_BRACE))
            {
                throw new InvalidParseException("Error: expected \"{\"", token.getFilename(),
                        token.getLineNum());
            }
            else
            {
                tokens.remove(0);
                BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

                if (bodyNode != null)
                {
                    token = tokens.get(0);
                    if (!(token.getTokenType() == TokenType.R_BRACE))
                    {
                        throw new InvalidParseException("Error: expected \"}\"", token.getFilename(),
                                token.getLineNum());
                    }
                    else
                    {
                        tokens.remove(0);
                        return new ElseStatementNode(bodyNode);
                    }
                }
            }
        }
        return null;
    }

    public boolean hasReturn() {
        if (bodyNode == null) {
            return false;
        }
        return this.bodyNode.hasReturn();
    }
    @Override
    public String convertToJott()
    {
        if (this.bodyNode != null)
        {
            return "else{" + this.bodyNode.convertToJott() + "}";
        }
        else return "";
    }

    @Override
    public String convertToJava() {
        if (this.bodyNode != null){
            return "else{" + bodyNode.convertToJava() + "}";
        }
        else return "";
    }

    @Override
    public String convertToC() {
        if (this.bodyNode != null){
            return "else{" + bodyNode.convertToC() + "}";
        }
        else return "";
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
		String nestIndent = "";
		for (int i=0;i<nestLevel;i++) {
			nestIndent = nestIndent + "\t";
		}
		// python does not allow empty statements, so "True" is put there just in case
        return nestIndent + "else:\n"
			+ (this.bodyNode != null ? this.bodyNode.convertToPython(nestLevel+1)+"\n":"\tTrue\n");
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (this.bodyNode == null){
            return true;
        }
        else {
            return this.bodyNode.validateTree(symbolTable);
        }
    }

}
