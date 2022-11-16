package Nodes;

import main.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Ben Froment
 *
 * Node representing an assignment statement
 */
public class AssignmentNode implements JottTree {

    private final String value;
    private final IdNode idNode;
    private final DoubleExpressionNode doubleExpressionNode;
    private final IntegerExpressionNode integerExpressionNode;
    private final StringExpressionNode stringExpressionNode;
    private final BooleanExpressionNode booleanExpressionNode;
    private final EndStatementNode endStatementNode;
    private final boolean firstDeclaration;

    // Double < id > = < d_expr > < end_statement >
    public AssignmentNode(String value, IdNode idNode,
                          DoubleExpressionNode doubleExpressionNode, EndStatementNode endStatementNode) {
        this.value = value;
        this.idNode = idNode;
        this.doubleExpressionNode = doubleExpressionNode;
        this.endStatementNode = endStatementNode;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = true;
    }

    // Integer < id > = < i_expr > < end_statement >
    public AssignmentNode(String value, IdNode idNode, IntegerExpressionNode integerExpressionNode,
                           EndStatementNode endStatementNode) {
        this.value = value;
        this.idNode = idNode;
        this.integerExpressionNode = integerExpressionNode;
        this.endStatementNode = endStatementNode;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = true;
    }

    // String < id > = < s_expr > < end_statement >
    public AssignmentNode(String value, IdNode idNode, StringExpressionNode stringExpressionNode,
                          EndStatementNode endStatementNode) {
        this.value = value;
        this.idNode = idNode;
        this.stringExpressionNode = stringExpressionNode;
        this.endStatementNode = endStatementNode;
        this.doubleExpressionNode = null;
        this.integerExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = true;
    }

    // Boolean < id > = < b_expr > < end_statement >
    public AssignmentNode(String value, IdNode idNode, BooleanExpressionNode booleanExpressionNode,
                          EndStatementNode endStatementNode) {
        this.value = value;
        this.idNode = idNode;
        this.booleanExpressionNode = booleanExpressionNode;
        this.endStatementNode = endStatementNode;
        this.doubleExpressionNode = null;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.firstDeclaration = true;
    }

    // < id > = < d_expr > < end_statement >
    public AssignmentNode(IdNode idNode, DoubleExpressionNode doubleExpressionNode,
                          EndStatementNode endStatementNode) {
        this.idNode = idNode;
        this.doubleExpressionNode = doubleExpressionNode;
        this.endStatementNode = endStatementNode;
        this.value = null;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = false;
    }

    // < id > = < i_expr > < end_statement >
    public AssignmentNode(IdNode idNode, IntegerExpressionNode integerExpressionNode,
                          EndStatementNode endStatementNode) {
        this.idNode = idNode;
        this.integerExpressionNode = integerExpressionNode;
        this.endStatementNode = endStatementNode;
        this.value = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = false;
    }

    // < id > = < s_expr > < end_statement >
    public AssignmentNode(IdNode idNode, StringExpressionNode stringExpressionNode,
                          EndStatementNode endStatementNode) {
        this.idNode = idNode;
        this.stringExpressionNode = stringExpressionNode;
        this.endStatementNode = endStatementNode;
        this.value = null;
        this.doubleExpressionNode = null;
        this.integerExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = false;
    }

    // < id > = < b_expr > < end_statement >
    public AssignmentNode(IdNode idNode, BooleanExpressionNode booleanExpressionNode,
                          EndStatementNode endStatementNode) {
        this.idNode = idNode;
        this.booleanExpressionNode = booleanExpressionNode;
        this.endStatementNode = endStatementNode;
        this.value = null;
        this.doubleExpressionNode = null;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.firstDeclaration = false;
    }

    // Function called by its parent node to parse the list of tokens
    public static AssignmentNode parseAssignmentNode (ArrayList<Token> tokens) throws Exception {
        Token token;
        token = tokens.get(0);
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            String value = tokens.get(0).getToken();
            IdNode idNode;
            if (tokens.size() > 2 && tokens.get(2).getTokenType() == TokenType.ASSIGN) {
                switch (value) {
                    case "Double":
                        tokens.remove(0);
                        idNode = IdNode.parseIdNode(tokens);
                        if (idNode != null) {
                            token = tokens.get(0);
                            if (token.getTokenType() != TokenType.ASSIGN) {
                                throw new InvalidParseException("Error: expected '=' sign", token.getFilename(),
                                        token.getLineNum());
                            }
                            tokens.remove(0);
                            DoubleExpressionNode doubleExpressionNode =
                                    DoubleExpressionNode.parseDoubleExpressionNode(tokens);
                            if (doubleExpressionNode != null) {
                                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                                return new AssignmentNode("Double", idNode,
                                        doubleExpressionNode, endStatementNode);
                            }
                            throw new InvalidParseException("Error: expected <d_expr>", token.getFilename(),
                                    token.getLineNum());
                        }
                        throw new InvalidParseException("Error: expected <id>", token.getFilename(),
                                token.getLineNum());
                    case "Integer":
                        tokens.remove(0);
                        idNode = IdNode.parseIdNode(tokens);
                        if (idNode != null) {
                            token = tokens.get(0);
                            if (token.getTokenType() != TokenType.ASSIGN) {
                                throw new InvalidParseException("Error: expected '=' sign", token.getFilename(),
                                        token.getLineNum());
                            }
                            tokens.remove(0);
                            IntegerExpressionNode integerExpressionNode =
                                    IntegerExpressionNode.parseIntegerExpressionNode(tokens);
                            if (integerExpressionNode != null) {
                                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                                return new AssignmentNode("Integer", idNode,
                                        integerExpressionNode, endStatementNode);
                            }
                            throw new InvalidParseException("Error: expected <i_expr>", token.getFilename(),
                                    token.getLineNum());
                        }
                        throw new InvalidParseException("Error: expected <id>", token.getFilename(),
                                token.getLineNum());
                    case "String":
                        tokens.remove(0);
                        idNode = IdNode.parseIdNode(tokens);
                        if (idNode != null) {
                            token = tokens.get(0);
                            if (token.getTokenType() != TokenType.ASSIGN) {
                                throw new InvalidParseException("Error: expected '=' sign", token.getFilename(),
                                        token.getLineNum());
                            }
                            tokens.remove(0);
                            StringExpressionNode stringExpressionNode =
                                    StringExpressionNode.parseStringExpressionNode(tokens);
                            if (stringExpressionNode != null) {
                                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                                return new AssignmentNode("String", idNode,
                                        stringExpressionNode, endStatementNode);
                            }
                            throw new InvalidParseException("Error: expected <s_expr>", token.getFilename(),
                                    token.getLineNum());
                        }
                        throw new InvalidParseException("Error: expected <id>", token.getFilename(),
                                token.getLineNum());
                    case "Boolean":
                        tokens.remove(0);
                        idNode = IdNode.parseIdNode(tokens);
                        if (idNode != null) {
                            token = tokens.get(0);
                            if (token.getTokenType() != TokenType.ASSIGN) {
                                throw new InvalidParseException("Error: expected '=' sign", token.getFilename(),
                                        token.getLineNum());
                            }
                            tokens.remove(0);
                            BooleanExpressionNode booleanExpressionNode =
                                    BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                            if (booleanExpressionNode != null) {
                                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                                return new AssignmentNode("Boolean", idNode,
                                        booleanExpressionNode, endStatementNode);
                            }
                            throw new InvalidParseException("Error: expected <b_expr>", token.getFilename(),
                                    token.getLineNum());
                        }
                        throw new InvalidParseException("Error: expected <id>", token.getFilename(),
                                token.getLineNum());
                }
            }
            if (tokens.size() > 1 && tokens.get(1).getTokenType() == TokenType.ASSIGN) {
                idNode = IdNode.parseIdNode(tokens);
                if (idNode != null) {
                    token = tokens.get(0);
                    if (token.getTokenType() != TokenType.ASSIGN) {
                        throw new InvalidParseException("Error: expected '=' sign", token.getFilename(),
                                token.getLineNum());
                    }
                    tokens.remove(0);
                    DoubleExpressionNode doubleExpressionNode =
                            DoubleExpressionNode.parseDoubleExpressionNode(tokens);
                    if (doubleExpressionNode != null) {
                        EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                        return new AssignmentNode(idNode, doubleExpressionNode, endStatementNode);
                    }
                    IntegerExpressionNode integerExpressionNode =
                            IntegerExpressionNode.parseIntegerExpressionNode(tokens);
                    if (integerExpressionNode != null) {
                        EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                        return new AssignmentNode(idNode, integerExpressionNode, endStatementNode);
                    }
                    StringExpressionNode stringExpressionNode =
                            StringExpressionNode.parseStringExpressionNode(tokens);
                    if (stringExpressionNode != null) {
                        EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                        return new AssignmentNode(idNode, stringExpressionNode, endStatementNode);
                    }
                    BooleanExpressionNode booleanExpressionNode =
                            BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                    if (booleanExpressionNode != null) {
                        EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                        return new AssignmentNode(idNode, booleanExpressionNode, endStatementNode);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (value != null) {
            if (idNode != null) {
                if (doubleExpressionNode != null) {
                    return value + " " + idNode.convertToJott() + "=" +
                            doubleExpressionNode.convertToJott() + endStatementNode.convertToJott();
                }
                if (integerExpressionNode != null) {
                    return value + " " + idNode.convertToJott() + "=" +
                            integerExpressionNode.convertToJott() + endStatementNode.convertToJott();
                }
                if (stringExpressionNode != null) {
                    return value + " " + idNode.convertToJott() + "=" +
                            stringExpressionNode.convertToJott() + endStatementNode.convertToJott();
                }
                if (booleanExpressionNode != null) {
                    return value + " " + idNode.convertToJott() + "=" +
                            booleanExpressionNode.convertToJott() + endStatementNode.convertToJott();
                }
            }
        }
        if (idNode != null) {
            if (doubleExpressionNode != null) {
                return idNode.convertToJott() + "=" +
                        doubleExpressionNode.convertToJott() + endStatementNode.convertToJott();
            }
            if (integerExpressionNode != null) {
                return idNode.convertToJott() + "=" +
                        integerExpressionNode.convertToJott() + endStatementNode.convertToJott();
            }
            if (stringExpressionNode != null) {
                return idNode.convertToJott() + "=" +
                        stringExpressionNode.convertToJott() + endStatementNode.convertToJott();
            }
            if (booleanExpressionNode != null) {
                return idNode.convertToJott() + "=" +
                        booleanExpressionNode.convertToJott() + endStatementNode.convertToJott();
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
    public String convertToPython(int nestLevel) { //Ian
		// python is all implicit variable declarations
		if (this.doubleExpressionNode != null) {
			return this.idNode.convertToPython(nestLevel) + "="
				+ this.doubleExpressionNode.convertToPython(nestLevel)
				+ this.endStatementNode.convertToPython(nestLevel);
		}
		if (this.integerExpressionNode != null) {
			return this.idNode.convertToPython(nestLevel) + "="
				+ this.integerExpressionNode.convertToPython(nestLevel)
				+ this.endStatementNode.convertToPython(nestLevel);
		}
		if (this.stringExpressionNode != null) {
			return this.idNode.convertToPython(nestLevel) + "="
				+ this.stringExpressionNode.convertToPython(nestLevel)
				+ this.endStatementNode.convertToPython(nestLevel);
		}
		if (this.booleanExpressionNode != null) {
			return this.idNode.convertToPython(nestLevel) + "="
				+ this.booleanExpressionNode.convertToPython(nestLevel)
				+ this.endStatementNode.convertToPython(nestLevel);
		}
		return "";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {

        if (!idNode.validateTree())
        {
            return false;
        }
        if (!endStatementNode.validateTree())
        {
            return false;
        }

        if (this.value != null) {
            if (!firstDeclaration) { //already exists but trying to declare type during assignment
                return false;
            }
            return symbolTable.addSymbol(idNode.getName(), value);
        }
        else {
            if (this.firstDeclaration) { //didn't already exist and not declaring type during assignment
                return false;
            } else {
                if (integerExpressionNode != null) {
                    if (symbolTable.getType(this.idNode.getName()).equals(integerExpressionNode.getType(symbolTable))) {
                        return integerExpressionNode.validateTree(symbolTable);
                    }
                }
                if (doubleExpressionNode != null) {
                    if (symbolTable.getType(this.idNode.getName()).equals(doubleExpressionNode.getType(symbolTable))) {
                        return doubleExpressionNode.validateTree(symbolTable);
                    }
                }
                if (stringExpressionNode != null) {
                    if (symbolTable.getType(this.idNode.getName()).equals("String")) {
                        return stringExpressionNode.validateTree(symbolTable);
                    }
                }
                if (booleanExpressionNode != null) {
                    if (symbolTable.getType(this.idNode.getName()).equals(booleanExpressionNode.getType(symbolTable))) {
                        return booleanExpressionNode.validateTree(symbolTable);
                    }
                }
                //TODO throw error
            }
        }
        return false;
    }
}
