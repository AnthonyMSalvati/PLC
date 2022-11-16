package Nodes;

import main.*;

import java.util.ArrayList;

public class WhileLoopNode implements JottTree {

    private final BooleanExpressionNode booleanExpressionNode;
    private final BodyNode bodyNode;

    public WhileLoopNode(BooleanExpressionNode booleanExpressionNode, BodyNode bodyNode)
    {
        this.booleanExpressionNode = booleanExpressionNode;
        this.bodyNode = bodyNode;
    }

    public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws Exception {

        if (!(tokens.get(0).getToken().equals("while")))
        {
            return null;
        }
        else
        {
            tokens.remove(0);
            if (!(tokens.get(0).getTokenType() == TokenType.L_BRACKET))
            {
                throw new InvalidParseException("Error: expected \"[\"", 
                tokens.get(0).getFilename(), tokens.get(0).getLineNum());
            }
            else
            {
                tokens.remove(0);
                BooleanExpressionNode booleanExpressionNode = BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                if (!(booleanExpressionNode == null))
                {
                    if (!(tokens.get(0).getTokenType() == TokenType.R_BRACKET))
                    {
                        throw new InvalidParseException("Error: expected \"]\"", 
                        tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                    }
                    else
                    {
                        tokens.remove(0);
                        if (!(tokens.get(0).getTokenType() == TokenType.L_BRACE))
                        {
                            throw new InvalidParseException("Error: expected \"{\"", 
                            tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                        }
                        else
                        {
                            tokens.remove(0);
                            BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                            if (!(bodyNode == null))
                            {
                                if (!(tokens.get(0).getTokenType() == TokenType.R_BRACE))
                                {
                                    throw new InvalidParseException("Error: expected \"}\"", 
                                    tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                                }
                                else
                                {
                                    tokens.remove(0);
                                    return new WhileLoopNode(booleanExpressionNode, bodyNode);
                                }
                            }
                            else throw new InvalidParseException("Error: unhandled in BodyNode", 
                            tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                        }
                    }
                }
                else
                {
                    throw new InvalidParseException("Error: unhandled in BooleanExpressionNode", 
                    tokens.get(0).getFilename(), tokens.get(0).getLineNum()); //should be handled in BooleanExpressionNode
                }
            }
        }
    }
    @Override
    public String convertToJott() {

        return "while[" + this.booleanExpressionNode.convertToJott() + "]{" + this.bodyNode.convertToJott() +"}";
    }

    @Override
    public String convertToJava() {
        return "while (" + this.booleanExpressionNode.convertToJava() + "){" + this.bodyNode.convertToJava() + "}";
    }

    @Override
    public String convertToC() {
        return "while (" + this.booleanExpressionNode.convertToC() + "){" + this.bodyNode.convertToC() + "}";
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
		return "while (" + this.booleanExpressionNode.convertToPython(nestLevel)
			+ "):\n" + this.bodyNode.convertToPython(nestLevel + 1) + "\n";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (this.booleanExpressionNode.validateTree(symbolTable)){
            return this.bodyNode.validateTree(symbolTable);
        }
        return false;
    }

}
