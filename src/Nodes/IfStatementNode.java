package Nodes;

import main.*;

import java.util.ArrayList;

public class IfStatementNode implements JottTree {

    private final BooleanExpressionNode booleanExpressionNode;
    private final BodyNode bodyNode;
    private final ElseIfStatementNode elseIfStatementNode;
    private final ElseStatementNode elseStatementNode;
    public IfStatementNode(BooleanExpressionNode booleanExpressionNode, BodyNode bodyNode,  ElseIfStatementNode elseIfStatementNode, ElseStatementNode elseStatementNode)
    {
        this.booleanExpressionNode = booleanExpressionNode;
        this.bodyNode = bodyNode;
        this.elseIfStatementNode = elseIfStatementNode;
        this.elseStatementNode = elseStatementNode;
    }

    public static IfStatementNode parseIfStatementNode(ArrayList<Token> tokens) throws Exception {
        if (!(tokens.get(0).getToken().equals("if")))
        {
            return null;
        }
        else
        {
            tokens.remove(0);
            if (!(tokens.get(0).getTokenType() == TokenType.L_BRACKET))
            {
                throw new InvalidParseException("Error: expected \"[\"", tokens.get(0).getFilename(), tokens.get(0).getLineNum());
            }
            else
            {
                tokens.remove(0);
                BooleanExpressionNode booleanExpressionNode = BooleanExpressionNode.parseBooleanExpressionNode(tokens);

                if (booleanExpressionNode == null)
                {
                    throw new InvalidParseException("Error: unhandled in BooleanExpressionNode", 
                    tokens.get(0).getFilename(), tokens.get(0).getLineNum()); //should be handled in BooleanExpressionNode
                }
                else
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
                            if (bodyNode == null)
                            {
                                throw new InvalidParseException("Error: unhandled in BodyNode", 
                                tokens.get(0).getFilename(), tokens.get(0).getLineNum()); //should be handled in body node
                            }
                            else
                            {
                                if (!(tokens.get(0).getTokenType() == TokenType.R_BRACE))
                                {
                                    throw new InvalidParseException("Error: expected \"}\"", 
                                    tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                                }
                                else {
                                    tokens.remove(0);
                                    ElseIfStatementNode elseIfStatementNode = ElseIfStatementNode.parseElseIfStatementNode(tokens);
                                    ElseStatementNode elseStatementNode = ElseStatementNode.parseElseStatementNode(tokens);
                                    return new IfStatementNode(booleanExpressionNode, bodyNode, elseIfStatementNode, elseStatementNode);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String getType(SymbolTable symbolTable){
        return this.bodyNode.getType(symbolTable);
    }

    @Override
    public String convertToJott()
    {
        return "if[" + booleanExpressionNode.convertToJott() + "]{" + bodyNode.convertToJott() + "}" + elseIfStatementNode.convertToJott() + elseStatementNode.convertToJott();
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
    public String convertToPython(int nestLevel) { //Ian
		return "if (" + this.booleanExpressionNode.convertToPython(nestLevel) + "):\n"
			+ this.bodyNode.convertToPython(nestLevel + 1) + "\n"
			+ (this.elseIfStatementNode!=null?this.elseIfStatementNode.convertToPython(nestLevel)+"\n":"")
			+ (this.elseStatementNode!=null?this.elseStatementNode.convertToPython(nestLevel)+"\n":"");
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (this.booleanExpressionNode.validateTree(symbolTable)){
            if (this.bodyNode.validateTree(symbolTable)){
                if (this.elseIfStatementNode.validateTree(symbolTable)){
                    if (this.elseStatementNode.validateTree(symbolTable)){
                        if (this.elseStatementNode.getType(symbolTable) != null){
                            if (this.elseIfStatementNode.getType(symbolTable) == null){
                                return false; //else returns but else if does not
                            }
                            else if (this.elseStatementNode.getType(symbolTable) != this.elseIfStatementNode.getType(symbolTable)){
                                return false; //else and else if return different types
                            }
                            else if (this.elseStatementNode.getType(symbolTable) == this.elseIfStatementNode.getType(symbolTable)){
                                if (this.elseStatementNode.getType(symbolTable) != this.bodyNode.getType(symbolTable)) {
                                    return false; //else ifs and else match, body of main if does not
                                }
                                else return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
