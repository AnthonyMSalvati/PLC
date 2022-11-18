package Nodes;

import main.JottTree;
import main.SymbolTable;
import main.Token;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Ben Froment
 *
 * Node represeting different kinds of expressions
 */
public class ExpressionNode implements JottTree {

    private final IdNode idNode;
    private final FunctionCallNode functionCallNode;
    private final IntegerExpressionNode integerExpressionNode;
    private final DoubleExpressionNode doubleExpressionNode;
    private final StringExpressionNode stringExpressionNode;
    private final BooleanExpressionNode booleanExpressionNode;

    // < id >
    public ExpressionNode(IdNode idNode) {
        this.functionCallNode = null;
        this.integerExpressionNode = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;

        this.idNode = idNode;
    }

    // < func_call >
    public ExpressionNode(FunctionCallNode functionCallNode) {
        this.idNode = null;
        this.integerExpressionNode = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;

        this.functionCallNode = functionCallNode;
    }

    // < i_expr >
    public ExpressionNode(IntegerExpressionNode integerExpressionNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;

        this.integerExpressionNode = integerExpressionNode;
    }

    // < d_expr >
    public ExpressionNode(DoubleExpressionNode doubleExpressionNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;

        this.doubleExpressionNode = doubleExpressionNode;
    }

    // < s_expr >
    public ExpressionNode(StringExpressionNode stringExpressionNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.integerExpressionNode = null;
        this.doubleExpressionNode = null;
        this.booleanExpressionNode = null;

        this.stringExpressionNode = stringExpressionNode;
    }

    // < b_expr >
    public ExpressionNode(BooleanExpressionNode booleanExpressionNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.integerExpressionNode = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;

        this.booleanExpressionNode = booleanExpressionNode;
    }

    // Function called by its parent node to parse the list of tokens
    public static ExpressionNode parseExpressionNode (ArrayList<Token> tokens) throws Exception {
        FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
        if (functionCallNode != null) {
            return new ExpressionNode(functionCallNode);
        }
        if (tokens.size() > 1) {
            OperatorNode operatorNode = OperatorNode.parseOperatorNode(
                    new ArrayList<>(Collections.singletonList(tokens.get(1))));
            if (operatorNode != null) {
                if (tokens.size() > 2) {
                    try {
                        Integer.parseInt(tokens.get(2).getToken());
                        IntegerExpressionNode integerExpressionNode = IntegerExpressionNode.parseIntegerExpressionNode(tokens);
                        if (integerExpressionNode != null) {
                            return new ExpressionNode(integerExpressionNode);
                        }
                    } catch (NumberFormatException ignored) {

                    }
                    DoubleExpressionNode doubleExpressionNode = DoubleExpressionNode.parseDoubleExpressionNode(tokens);
                    if (doubleExpressionNode != null) {
                        return new ExpressionNode(doubleExpressionNode);
                    }
                    StringExpressionNode stringExpressionNode = StringExpressionNode.parseStringExpressionNode(tokens);
                    if (stringExpressionNode != null) {
                        return new ExpressionNode(stringExpressionNode);
                    }
                }
            }
            RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(
                    new ArrayList<>(Collections.singletonList(tokens.get(1))));
            if (relationOperatorNode != null) {
                BooleanExpressionNode booleanExpressionNode = BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                if (booleanExpressionNode != null) {
                    return new ExpressionNode(booleanExpressionNode);
                }
            }
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            return new ExpressionNode(idNode);
        }
        IntegerExpressionNode integerExpressionNode = IntegerExpressionNode.parseIntegerExpressionNode(tokens);
        if (integerExpressionNode != null) {
            return new ExpressionNode(integerExpressionNode);
        }
        DoubleExpressionNode doubleExpressionNode = DoubleExpressionNode.parseDoubleExpressionNode(tokens);
        if (doubleExpressionNode != null) {
            return new ExpressionNode(doubleExpressionNode);
        }
        StringExpressionNode stringExpressionNode = StringExpressionNode.parseStringExpressionNode(tokens);
        if (stringExpressionNode != null) {
            return new ExpressionNode(stringExpressionNode);
        }
        return null;
    }

    public static String getExpressionType(ArrayList<Token> tokens) throws Exception {
        if (tokens.size() > 1) {
            OperatorNode operatorNode = OperatorNode.parseOperatorNode(
                    new ArrayList<>(Collections.singletonList(tokens.get(1))));
            if (operatorNode != null) {
                if (tokens.size() > 2) {
                    try {
                        Integer.parseInt(tokens.get(2).getToken());
                        IntegerExpressionNode integerExpressionNode = IntegerExpressionNode.parseIntegerExpressionNode(
                                new ArrayList<>(tokens));
                        if (integerExpressionNode != null) {
                            return "Integer";
                        }
                    } catch (NumberFormatException ignored) {

                    }
                    DoubleExpressionNode doubleExpressionNode = DoubleExpressionNode.parseDoubleExpressionNode(
                            new ArrayList<>(tokens));
                    if (doubleExpressionNode != null) {
                        return "Double";
                    }
                    StringExpressionNode stringExpressionNode = StringExpressionNode.parseStringExpressionNode(
                            new ArrayList<>(tokens));
                    if (stringExpressionNode != null) {
                        return "String";
                    }
                }
            }
            RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(
                    new ArrayList<>(Collections.singletonList(tokens.get(1))));
            if (relationOperatorNode != null) {
                BooleanExpressionNode booleanExpressionNode = BooleanExpressionNode.parseBooleanExpressionNode(
                        new ArrayList<>(tokens));
                if (booleanExpressionNode != null) {
                    return "Boolean";
                }
            }
        }
        return "";
    }

    public String getType(SymbolTable symbolTable) throws Exception{
        if (functionCallNode != null) {
            functionCallNode.getType(symbolTable);
        }
        if (idNode != null) {
            return symbolTable.getType(idNode.getName());
        }
        if (booleanExpressionNode != null) {
            return booleanExpressionNode.getType(symbolTable);
        }
        if (doubleExpressionNode != null) {
            return doubleExpressionNode.getType(symbolTable);
        }
        if (integerExpressionNode != null) {
            return integerExpressionNode.getType(symbolTable);
        }
        if (stringExpressionNode != null) {
            return "String";
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (functionCallNode != null) {
            return functionCallNode.convertToJott();
        }
        if (idNode != null) {
            return idNode.convertToJott();
        }
        if (booleanExpressionNode != null) {
            return booleanExpressionNode.convertToJott();
        }
        if (doubleExpressionNode != null) {
            return doubleExpressionNode.convertToJott();
        }
        if (integerExpressionNode != null) {
            return integerExpressionNode.convertToJott();
        }
        if (stringExpressionNode != null) {
            return stringExpressionNode.convertToJott();
        }
        return "";
    }

    @Override
    public String convertToJava() {
        if (this.idNode != null) {
			return this.idNode.convertToJava();
		}
		if (this.functionCallNode != null) {
			return this.functionCallNode.convertToJava();
		}
		if (this.integerExpressionNode != null) {
			return this.integerExpressionNode.convertToJava();
		}
		if (this.doubleExpressionNode != null) {
			return this.doubleExpressionNode.convertToJava();
		}
		if (this.stringExpressionNode != null) {
			return this.stringExpressionNode.convertToJava();
		}
		if (this.booleanExpressionNode != null) {
			return this.booleanExpressionNode.convertToJava();
		}
        return "";
    }

    @Override
    public String convertToC() {
        if (this.idNode != null) {
			return this.idNode.convertToC();
		}
		if (this.functionCallNode != null) {
			return this.functionCallNode.convertToC();
		}
		if (this.integerExpressionNode != null) {
			return this.integerExpressionNode.convertToC();
		}
		if (this.doubleExpressionNode != null) {
			return this.doubleExpressionNode.convertToC();
		}
		if (this.stringExpressionNode != null) {
			return this.stringExpressionNode.convertToC();
		}
		if (this.booleanExpressionNode != null) {
			return this.booleanExpressionNode.convertToC();
		}
        return "";
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
		if (this.idNode != null) {
			return this.idNode.convertToPython(nestLevel);
		}
		if (this.functionCallNode != null) {
			return this.functionCallNode.convertToPython(nestLevel);
		}
		if (this.integerExpressionNode != null) {
			return this.integerExpressionNode.convertToPython(nestLevel);
		}
		if (this.doubleExpressionNode != null) {
			return this.doubleExpressionNode.convertToPython(nestLevel);
		}
		if (this.stringExpressionNode != null) {
			return this.stringExpressionNode.convertToPython(nestLevel);
		}
		if (this.booleanExpressionNode != null) {
			return this.booleanExpressionNode.convertToPython(nestLevel);
		}
        return "";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (functionCallNode != null) {
            return functionCallNode.validateTree(symbolTable);
        }
        if (idNode != null) {
            return idNode.validateTree(symbolTable);
        }
        if (booleanExpressionNode != null) {
            return booleanExpressionNode.validateTree(symbolTable);
        }
        if (doubleExpressionNode != null) {
            return doubleExpressionNode.validateTree(symbolTable);
        }
        if (integerExpressionNode != null) {
            return integerExpressionNode.validateTree(symbolTable);
        }
        if (stringExpressionNode != null) {
            return stringExpressionNode.validateTree(symbolTable);
        }
        return false;
    }
}
