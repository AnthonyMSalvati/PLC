package Nodes;

import main.*;

import java.util.ArrayList;

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

    private final Token lastToken;

    // Double < id > = < d_expr > < end_statement >
    public AssignmentNode(String value, IdNode idNode,
                          DoubleExpressionNode doubleExpressionNode, EndStatementNode endStatementNode, Token token) {
        this.value = value;
        this.idNode = idNode;
        this.doubleExpressionNode = doubleExpressionNode;
        this.endStatementNode = endStatementNode;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = true;

        this.lastToken = token;
    }

    // Integer < id > = < i_expr > < end_statement >
    public AssignmentNode(String value, IdNode idNode, IntegerExpressionNode integerExpressionNode,
                           EndStatementNode endStatementNode, Token token) {
        this.value = value;
        this.idNode = idNode;
        this.integerExpressionNode = integerExpressionNode;
        this.endStatementNode = endStatementNode;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = true;

        this.lastToken = token;
    }

    // String < id > = < s_expr > < end_statement >
    public AssignmentNode(String value, IdNode idNode, StringExpressionNode stringExpressionNode,
                          EndStatementNode endStatementNode, Token token) {
        this.value = value;
        this.idNode = idNode;
        this.stringExpressionNode = stringExpressionNode;
        this.endStatementNode = endStatementNode;
        this.doubleExpressionNode = null;
        this.integerExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = true;

        this.lastToken = token;
    }

    // Boolean < id > = < b_expr > < end_statement >
    public AssignmentNode(String value, IdNode idNode, BooleanExpressionNode booleanExpressionNode,
                          EndStatementNode endStatementNode, Token token) {
        this.value = value;
        this.idNode = idNode;
        this.booleanExpressionNode = booleanExpressionNode;
        this.endStatementNode = endStatementNode;
        this.doubleExpressionNode = null;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.firstDeclaration = true;

        this.lastToken = token;
    }

    // < id > = < d_expr > < end_statement >
    public AssignmentNode(IdNode idNode, DoubleExpressionNode doubleExpressionNode,
                          EndStatementNode endStatementNode, Token token) {
        this.idNode = idNode;
        this.doubleExpressionNode = doubleExpressionNode;
        this.endStatementNode = endStatementNode;
        this.value = null;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = false;

        this.lastToken = token;
    }

    // < id > = < i_expr > < end_statement >
    public AssignmentNode(IdNode idNode, IntegerExpressionNode integerExpressionNode,
                          EndStatementNode endStatementNode, Token token) {
        this.idNode = idNode;
        this.integerExpressionNode = integerExpressionNode;
        this.endStatementNode = endStatementNode;
        this.value = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = false;

        this.lastToken = token;
    }

    // < id > = < s_expr > < end_statement >
    public AssignmentNode(IdNode idNode, StringExpressionNode stringExpressionNode,
                          EndStatementNode endStatementNode, Token token) {
        this.idNode = idNode;
        this.stringExpressionNode = stringExpressionNode;
        this.endStatementNode = endStatementNode;
        this.value = null;
        this.doubleExpressionNode = null;
        this.integerExpressionNode = null;
        this.booleanExpressionNode = null;
        this.firstDeclaration = false;

        this.lastToken = token;
    }

    // < id > = < b_expr > < end_statement >
    public AssignmentNode(IdNode idNode, BooleanExpressionNode booleanExpressionNode,
                          EndStatementNode endStatementNode, Token token) {
        this.idNode = idNode;
        this.booleanExpressionNode = booleanExpressionNode;
        this.endStatementNode = endStatementNode;
        this.value = null;
        this.doubleExpressionNode = null;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.firstDeclaration = false;

        this.lastToken = token;
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
                                        doubleExpressionNode, endStatementNode, token);
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
                                        integerExpressionNode, endStatementNode, token);
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
                                        stringExpressionNode, endStatementNode, token);
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
                                        booleanExpressionNode, endStatementNode, token);
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
                    switch (ExpressionNode.getExpressionType(tokens)) {
                        case "Integer":
                            IntegerExpressionNode integerExpressionNode =
                                    IntegerExpressionNode.parseIntegerExpressionNode(tokens);
                            if (integerExpressionNode != null) {
                                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                                return new AssignmentNode(idNode, integerExpressionNode, endStatementNode, token);
                            }
                            break;
                        case "Double":
                            DoubleExpressionNode doubleExpressionNode =
                                    DoubleExpressionNode.parseDoubleExpressionNode(tokens);
                            if (doubleExpressionNode != null) {
                                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                                return new AssignmentNode(idNode, doubleExpressionNode, endStatementNode, token);
                            }
                            break;
                        case "String":
                            StringExpressionNode stringExpressionNode =
                                    StringExpressionNode.parseStringExpressionNode(tokens);
                            if (stringExpressionNode != null) {
                                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                                return new AssignmentNode(idNode, stringExpressionNode, endStatementNode, token);
                            }
                            break;
                        case "Boolean":
                            BooleanExpressionNode booleanExpressionNode =
                                    BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                            if (booleanExpressionNode != null) {
                                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                                return new AssignmentNode(idNode, booleanExpressionNode, endStatementNode, token);
                            }
                            break;
                    }
                    DoubleExpressionNode doubleExpressionNode =
                            DoubleExpressionNode.parseDoubleExpressionNode(tokens);
                    if (doubleExpressionNode != null) {
                        EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                        return new AssignmentNode(idNode, doubleExpressionNode, endStatementNode, token);
                    }
                    IntegerExpressionNode integerExpressionNode =
                            IntegerExpressionNode.parseIntegerExpressionNode(tokens);
                    if (integerExpressionNode != null) {
                        EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                        return new AssignmentNode(idNode, integerExpressionNode, endStatementNode, token);
                    }
                    StringExpressionNode stringExpressionNode =
                            StringExpressionNode.parseStringExpressionNode(tokens);
                    if (stringExpressionNode != null) {
                        EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                        return new AssignmentNode(idNode, stringExpressionNode, endStatementNode, token);
                    }
                    BooleanExpressionNode booleanExpressionNode =
                            BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                    if (booleanExpressionNode != null) {
                        EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                        return new AssignmentNode(idNode, booleanExpressionNode, endStatementNode, token);
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
                    return this.value + " " + idNode.convertToJott() + "=" +
                            doubleExpressionNode.convertToJott() + endStatementNode.convertToJott();
                }
                if (integerExpressionNode != null) {
                    return this.value + " " + idNode.convertToJott() + "=" +
                            integerExpressionNode.convertToJott() + endStatementNode.convertToJott();
                }
                if (stringExpressionNode != null) {
                    return this.value + " " + idNode.convertToJott() + "=" +
                            stringExpressionNode.convertToJott() + endStatementNode.convertToJott();
                }
                if (booleanExpressionNode != null) {
                    return this.value + " " + idNode.convertToJott() + "=" +
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
        if (this.value != null){
            if (this.idNode != null){
                if (this.doubleExpressionNode != null) {
                    return "double" + " " + this.idNode.convertToJava() + "=" +
                            this.doubleExpressionNode.convertToJava() + this.endStatementNode.convertToJava();
                }
                if (integerExpressionNode != null) {
                    return "int" + " " + this.idNode.convertToJava() + "=" +
                            this.integerExpressionNode.convertToJava() + this.endStatementNode.convertToJava();
                }
                if (this.stringExpressionNode != null) {
                    return this.value + " " + this.idNode.convertToJava() + "=" +
                            this.stringExpressionNode.convertToJava() + this.endStatementNode.convertToJava();
                }
                if (this.booleanExpressionNode != null) {
                    return "boolean" + " " + this.idNode.convertToJava() + "=" +
                            this.booleanExpressionNode.convertToJava() + this.endStatementNode.convertToJava();
                }
            }
        }
        if (this.idNode != null) {
            if (this.doubleExpressionNode != null) {
                return this.idNode.convertToJava() + "=" +
                        this.doubleExpressionNode.convertToJava() + this.endStatementNode.convertToJava();
            }
            if (this.integerExpressionNode != null) {
                return idNode.convertToJava() + "=" +
                        this.integerExpressionNode.convertToJava() + this.endStatementNode.convertToJava();
            }
            if (this.stringExpressionNode != null) {
                return this.idNode.convertToJava() + "=" +
                        this.stringExpressionNode.convertToJava() + this.endStatementNode.convertToJava();
            }
            if (this.booleanExpressionNode != null) {
                return this.idNode.convertToJava() + "=" +
                        this.booleanExpressionNode.convertToJava() + this.endStatementNode.convertToJava();
            }
        }
        return "";
    }

    @Override
    public String convertToC() {
        if (this.value != null){
            if (this.idNode != null){
                if (this.doubleExpressionNode != null) {
                    return "double" + " " + this.idNode.convertToC() + "=" +
                            this.doubleExpressionNode.convertToC() + this.endStatementNode.convertToC();
                }
                if (integerExpressionNode != null) {
                    return "int" + " " + this.idNode.convertToC() + "=" +
                            this.integerExpressionNode.convertToC() + this.endStatementNode.convertToC();
                }
                if (this.stringExpressionNode != null) {
                    return "char *" + " " + this.idNode.convertToC() + "=" +
                            this.stringExpressionNode.convertToC() + this.endStatementNode.convertToC();
                }
                if (this.booleanExpressionNode != null) {
                    return "bool" + " " + this.idNode.convertToC() + "=" +
                            this.booleanExpressionNode.convertToC() + this.endStatementNode.convertToC();
                }
            }
        }
        if (this.idNode != null) {
            if (this.doubleExpressionNode != null) {
                return this.idNode.convertToC() + "=" +
                        this.doubleExpressionNode.convertToC() + this.endStatementNode.convertToC();
            }
            if (this.integerExpressionNode != null) {
                return idNode.convertToC() + "=" +
                        this.integerExpressionNode.convertToC() + this.endStatementNode.convertToC();
            }
            if (this.stringExpressionNode != null) {
                return this.idNode.convertToC() + "=" +
                        this.stringExpressionNode.convertToC() + this.endStatementNode.convertToC();
            }
            if (this.booleanExpressionNode != null) {
                return this.idNode.convertToC() + "=" +
                        this.booleanExpressionNode.convertToC() + this.endStatementNode.convertToC();
            }
        }
        return "";
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

        if (!idNode.validateTree(symbolTable))
        {
            return false;
        }
        if (!endStatementNode.validateTree(symbolTable))
        {
            return false;
        }

        if (this.value != null) {
            if (!firstDeclaration) { //already exists but trying to declare type during assignment
                return false;
            }
            symbolTable.addSymbol(idNode.getName(), value);

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
            }
        }
        throw new InvalidValidateException("Mismatched types", this.lastToken.getFilename(), this.lastToken.getLineNum());
    }
}
