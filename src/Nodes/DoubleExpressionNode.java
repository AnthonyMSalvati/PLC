package Nodes;

import main.InvalidParseException;
import main.JottTree;
import main.SymbolTable;
import main.Token;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Ben Froment
 *
 * Node representing a double expression
 */
public class DoubleExpressionNode implements JottTree {

    private final IdNode idNode;
    private final DoubleNode doubleNode1;
    private final DoubleNode doubleNode2;
    private final OperatorNode operatorNode;
    private final DoubleExpressionNode doubleExpressionNode1;
    private final DoubleExpressionNode doubleExpressionNode2;
    private final FunctionCallNode functionCallNode;

    // < id >
    public DoubleExpressionNode(IdNode idNode) {
        this.doubleNode1 = null;
        this.doubleNode2 = null;
        this.operatorNode = null;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;
        this.functionCallNode = null;

        this.idNode = idNode;
    }

    // < func_call >
    public DoubleExpressionNode(FunctionCallNode functionCallNode) {
        this.idNode = null;
        this.doubleNode1 = null;
        this.doubleNode2 = null;
        this.operatorNode = null;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;

        this.functionCallNode = functionCallNode;
    }

    // < dbl >
    public DoubleExpressionNode(DoubleNode doubleNode1) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleNode2 = null;
        this.operatorNode = null;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;

        this.doubleNode1 = doubleNode1;
    }

    // < dbl > < op > < dbl >
    public DoubleExpressionNode(DoubleNode doubleNode1, DoubleNode doubleNode2, OperatorNode operatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;

        this.doubleNode1 = doubleNode1;
        this.doubleNode2 = doubleNode2;
        this.operatorNode = operatorNode;
    }

    // < dbl > < op> < d_expr >
    public DoubleExpressionNode(DoubleNode doubleNode1, DoubleExpressionNode doubleExpressionNode2,
                                OperatorNode operatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleNode2 = null;
        this.doubleExpressionNode1 = null;

        this.doubleNode1 = doubleNode1;
        this.doubleExpressionNode2 = doubleExpressionNode2;
        this.operatorNode = operatorNode;
    }

    // < d_expr > < op > < dbl >
    public DoubleExpressionNode(DoubleExpressionNode doubleExpressionNode, DoubleNode doubleNode,
                                OperatorNode operatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleNode2 = null;
        this.doubleExpressionNode2 = null;

        this.doubleExpressionNode1 = doubleExpressionNode;
        this.doubleNode1 = doubleNode;
        this.operatorNode = operatorNode;
    }

    // < d_expr > < op > < d_expr >
    public DoubleExpressionNode(DoubleExpressionNode doubleExpressionNode1, DoubleExpressionNode doubleExpressionNode2,
                                OperatorNode operatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleNode1 = null;
        this.doubleNode2 = null;

        this.doubleExpressionNode1 = doubleExpressionNode1;
        this.doubleExpressionNode2 = doubleExpressionNode2;
        this.operatorNode = operatorNode;
    }

    // Function called by its parent node to parse the list of tokens
    public static DoubleExpressionNode parseDoubleExpressionNode (ArrayList<Token> tokens) throws Exception {
        FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
        if (functionCallNode != null) {
            return new DoubleExpressionNode(functionCallNode);
        }

        DoubleNode doubleNode1 = DoubleNode.parseDoubleNode(tokens);
        if (doubleNode1 != null) {
            OperatorNode operatorNode = OperatorNode.parseOperatorNode(tokens);
            if (operatorNode != null) {
                OperatorNode operatorNode2 = OperatorNode.parseOperatorNode(new ArrayList<>(Collections.singletonList(tokens.get(1))));
                if (operatorNode2 != null) {
                    DoubleExpressionNode doubleExpressionNode = parseDoubleExpressionNode(tokens);
                    if (doubleExpressionNode != null) {
                        return new DoubleExpressionNode(doubleNode1, doubleExpressionNode, operatorNode);
                    }
                }

                IdNode idNode = IdNode.parseIdNode(tokens);
                if (idNode != null) {
                    return new DoubleExpressionNode(doubleNode1, new DoubleExpressionNode(idNode), operatorNode);
                }

                DoubleNode doubleNode2 =  DoubleNode.parseDoubleNode(tokens);
                if (doubleNode2 != null) {
                    return new DoubleExpressionNode(doubleNode1, doubleNode2, operatorNode);
                } else {
                    throw new InvalidParseException("Error: <dbl> <op> not followed by <dbl>", tokens.get(0).getFilename(),
                            tokens.get(0).getLineNum());
                }
            } else {
                return new DoubleExpressionNode(doubleNode1);
            }
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            if (tokens.size() > 1) {
                OperatorNode operatorNode = OperatorNode.parseOperatorNode(tokens);
                if (operatorNode != null) {
                    DoubleExpressionNode doubleExpressionNode1 = new DoubleExpressionNode(idNode);
                    OperatorNode operatorNode2 = OperatorNode.parseOperatorNode(new ArrayList<>(Collections.singletonList(tokens.get(1))));
                    if (operatorNode2 != null) {
                        DoubleExpressionNode doubleExpressionNode2 = parseDoubleExpressionNode(tokens);
                        if (doubleExpressionNode2 != null) {
                            return new DoubleExpressionNode(doubleExpressionNode1, doubleExpressionNode2, operatorNode);
                        }
                    }
                    DoubleNode doubleNode = DoubleNode.parseDoubleNode(tokens);
                    if (doubleNode != null) {
                        return new DoubleExpressionNode(doubleExpressionNode1, doubleNode, operatorNode);
                    } else {
                        throw new InvalidParseException("Error: <i_expr> <op> not followed by <int>",
                                tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                    }
                }
            }
            return new DoubleExpressionNode(idNode);
        }

        return null;
    }

    public String getType(SymbolTable symbolTable) throws Exception {
        if (functionCallNode != null) {
            return functionCallNode.getType(symbolTable);
        }

        if (idNode != null) {
            return symbolTable.getType(idNode.getName());
        }

        if (doubleExpressionNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    if (doubleExpressionNode1.getType(symbolTable).equals(doubleExpressionNode2.getType(symbolTable))){
                        return "Double";
                    }
                    return null;
                }
                if(doubleNode1 != null) {
                    return "Double";
                }
            }
            return "Double";
        }

        if (doubleNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    if (doubleExpressionNode2.getType(symbolTable).equals("Double"))
                        return "Double";
                }
                if(doubleNode2 != null) {
                    return "Double";
                }
            }
            return "Double";
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

        if (doubleExpressionNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    return doubleExpressionNode1.convertToJott() + operatorNode.convertToJott() +
                            doubleExpressionNode2.convertToJott();
                }
                if(doubleNode1 != null) {
                    return doubleExpressionNode1.convertToJott() + operatorNode.convertToJott() +
                            doubleNode1.convertToJott();
                }
            }
            return doubleExpressionNode1.convertToJott();
        }

        if (doubleNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    return doubleNode1.convertToJott() + operatorNode.convertToJott() +
                            doubleExpressionNode2.convertToJott();
                }
                if(doubleNode2 != null) {
                    return doubleNode1.convertToJott() + operatorNode.convertToJott() +
                            doubleNode2.convertToJott();
                }
            }
            return doubleNode1.convertToJott();
        }
        return "";
    }

    @Override
    public String convertToJava() {
        if (functionCallNode != null) {
            return functionCallNode.convertToJava();
        }

        if (idNode != null) {
            return idNode.convertToJava();
        }

        if (doubleExpressionNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    return doubleExpressionNode1.convertToJava() + operatorNode.convertToJava() +
                            doubleExpressionNode2.convertToJava();
                }
                if(doubleNode1 != null) {
                    return doubleExpressionNode1.convertToJava() + operatorNode.convertToJava() +
                            doubleNode1.convertToJava();
                }
            }
            return doubleExpressionNode1.convertToJava();
        }

        if (doubleNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    return doubleNode1.convertToJava() + operatorNode.convertToJava() +
                            doubleExpressionNode2.convertToJava();
                }
                if(doubleNode2 != null) {
                    return doubleNode1.convertToJava() + operatorNode.convertToJava() +
                            doubleNode2.convertToJava();
                }
            }
            return doubleNode1.convertToJava();
        }
        return "";
    }

    @Override
    public String convertToC() {
        if (functionCallNode != null) {
            return functionCallNode.convertToC();
        }

        if (idNode != null) {
            return idNode.convertToC();
        }

        if (doubleExpressionNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    return doubleExpressionNode1.convertToC() + operatorNode.convertToC() +
                            doubleExpressionNode2.convertToC();
                }
                if(doubleNode1 != null) {
                    return doubleExpressionNode1.convertToC() + operatorNode.convertToC() +
                            doubleNode1.convertToC();
                }
            }
            return doubleExpressionNode1.convertToC();
        }

        if (doubleNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    return doubleNode1.convertToC() + operatorNode.convertToC() +
                            doubleExpressionNode2.convertToC();
                }
                if(doubleNode2 != null) {
                    return doubleNode1.convertToC() + operatorNode.convertToC() +
                            doubleNode2.convertToC();
                }
            }
            return doubleNode1.convertToC();
        }
        return "";
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
        if (functionCallNode != null) {
            return functionCallNode.convertToPython(nestLevel);
        }

        if (idNode != null) {
            return idNode.convertToPython(nestLevel);
        }

        if (doubleExpressionNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    return doubleExpressionNode1.convertToPython(nestLevel) + operatorNode.convertToPython(nestLevel) +
                            doubleExpressionNode2.convertToPython(nestLevel);
                }
                if(doubleNode1 != null) {
                    return doubleExpressionNode1.convertToPython(nestLevel) + operatorNode.convertToPython(nestLevel) +
                            doubleNode1.convertToPython(nestLevel);
                }
            }
            return doubleExpressionNode1.convertToPython(nestLevel);
        }

        if (doubleNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    return doubleNode1.convertToPython(nestLevel) + operatorNode.convertToPython(nestLevel) +
                            doubleExpressionNode2.convertToPython(nestLevel);
                }
                if(doubleNode2 != null) {
                    return doubleNode1.convertToPython(nestLevel) + operatorNode.convertToPython(nestLevel) +
                            doubleNode2.convertToPython(nestLevel);
                }
            }
            return doubleNode1.convertToPython(nestLevel);
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

        if (doubleExpressionNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    if (doubleExpressionNode1.getType(symbolTable).equals(doubleExpressionNode2.getType(symbolTable))) {
                        return doubleExpressionNode1.validateTree(symbolTable) && operatorNode.validateTree(symbolTable) &&
                                doubleExpressionNode2.validateTree(symbolTable);
                    }
                }
                if (doubleNode1 != null) {
                    if (doubleExpressionNode1.getType(symbolTable).equals("Double")) {
                        return doubleExpressionNode1.validateTree(symbolTable) && operatorNode.validateTree(symbolTable) &&
                                doubleNode1.validateTree(symbolTable);
                    }
                }
            }
            return doubleExpressionNode1.validateTree(symbolTable);
        }

        if (doubleNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    if (doubleExpressionNode2.getType(symbolTable).equals("Double")) {
                        return doubleNode1.validateTree(symbolTable) && operatorNode.validateTree(symbolTable) &&
                                doubleExpressionNode2.validateTree(symbolTable);
                    }
                }
                if (doubleNode2 != null) {
                    return doubleNode1.validateTree(symbolTable) && operatorNode.validateTree(symbolTable) &&
                            doubleNode2.validateTree(symbolTable);
                }
            }
            return doubleNode1.validateTree(symbolTable);
        }
        return false;
    }
}
