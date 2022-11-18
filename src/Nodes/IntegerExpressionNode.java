package Nodes;

import main.JottTree;
import main.SymbolTable;
import main.Token;
import java.util.ArrayList;
import java.util.Collections;
import main.InvalidParseException;

/**
 * @author Ben Froment
 *
 * Node representing an integer expression
 */
public class IntegerExpressionNode implements JottTree {

    private final IdNode idNode;
    private final IntegerNode integerNode1;
    private final IntegerNode integerNode2;
    private final OperatorNode operatorNode;
    private final IntegerExpressionNode integerExpressionNode1;
    private final IntegerExpressionNode integerExpressionNode2;
    private final FunctionCallNode functionCallNode;

    // < id >
    public IntegerExpressionNode(IdNode idNode) {
        this.integerNode1 = null;
        this.integerNode2 = null;
        this.operatorNode = null;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;
        this.functionCallNode = null;

        this.idNode = idNode;
    }

    // < func_call >
    public IntegerExpressionNode (FunctionCallNode functionCallNode){
        this.idNode = null;
        this.integerNode1 = null;
        this.integerNode2 = null;
        this.operatorNode = null;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;

        this.functionCallNode = functionCallNode;
    }

    // < int >
    public IntegerExpressionNode (IntegerNode integerNode1) {
        this.idNode = null;
        this.integerNode2 = null;
        this.operatorNode = null;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;
        this.functionCallNode = null;

        this.integerNode1 = integerNode1;
    }

    // < int > < op > < int >
    public IntegerExpressionNode (IntegerNode integerNode1, IntegerNode integerNode2, OperatorNode operatorNode) {
        this.idNode = null;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;
        this.functionCallNode = null;

        this.integerNode1 = integerNode1;
        this.integerNode2 = integerNode2;
        this.operatorNode = operatorNode;
    }

    // < int > < op > < i_expr >
    public IntegerExpressionNode (IntegerNode integerNode1, IntegerExpressionNode integerExpressionNode2,
                                  OperatorNode operatorNode) {
        this.idNode = null;
        this.integerNode2 = null;
        this.integerExpressionNode1 = null;
        this.functionCallNode = null;

        this.integerNode1 = integerNode1;
        this.operatorNode = operatorNode;
        this.integerExpressionNode2 = integerExpressionNode2;
    }

    // < i_expr > < op > < int >
    public IntegerExpressionNode (IntegerExpressionNode integerExpressionNode, IntegerNode integerNode1,
                                  OperatorNode operatorNode) {
        this.idNode = null;
        this.integerNode2 = null;
        this.integerExpressionNode2 = null;
        this.functionCallNode = null;

        this.integerNode1 = integerNode1;
        this.operatorNode = operatorNode;
        this.integerExpressionNode1 = integerExpressionNode;
    }

    // < i_expr > < op> < i_expr >
    public IntegerExpressionNode (IntegerExpressionNode integerExpressionNode1, IntegerExpressionNode integerExpressionNode2,
                                  OperatorNode operatorNode) {
        this.idNode = null;
        this.integerNode2 = null;
        this.functionCallNode = null;
        this.integerNode1 = null;

        this.operatorNode = operatorNode;
        this.integerExpressionNode1 = integerExpressionNode1;
        this.integerExpressionNode2 = integerExpressionNode2;
    }

    // Function called by its parent node to parse the list of tokens
    public static IntegerExpressionNode parseIntegerExpressionNode (ArrayList<Token> tokens) throws Exception {
        FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
        if (functionCallNode != null) {
            return new IntegerExpressionNode(functionCallNode);
        }
        IntegerNode integerNode1 = IntegerNode.parseIntegerNode(tokens);
        if (integerNode1 != null) {
            OperatorNode operatorNode = OperatorNode.parseOperatorNode(tokens);
            if (operatorNode != null) {
                OperatorNode operatorNode2 = OperatorNode.parseOperatorNode(new ArrayList<>(Collections.singletonList(tokens.get(1))));
                if (operatorNode2 != null) {
                    IntegerExpressionNode integerExpressionNode = parseIntegerExpressionNode(tokens);
                    if (integerExpressionNode != null) {
                        return new IntegerExpressionNode(integerNode1, integerExpressionNode, operatorNode);
                    }
                }
                IdNode idNode = IdNode.parseIdNode(tokens);
                if (idNode != null) {
                    return new IntegerExpressionNode(integerNode1, new IntegerExpressionNode(idNode), operatorNode);
                }

                IntegerNode integerNode2 = IntegerNode.parseIntegerNode(tokens);
                if (integerNode2 != null) {
                    return new IntegerExpressionNode(integerNode1, integerNode2, operatorNode);
                } else {
                    throw new InvalidParseException("Error: <int> <op> not followed by <int>", 
                    tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                }
            } else {
                return new IntegerExpressionNode(integerNode1);
            }
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            if (tokens.size() > 1) {
                OperatorNode operatorNode = OperatorNode.parseOperatorNode(tokens);
                if (operatorNode != null) {
                    IntegerExpressionNode integerExpressionNode1 = new IntegerExpressionNode(idNode);
                    OperatorNode operatorNode2 = OperatorNode.parseOperatorNode(new ArrayList<>(Collections.singletonList(tokens.get(1))));
                    if (operatorNode2 != null) {
                        IntegerExpressionNode integerExpressionNode2 = parseIntegerExpressionNode(tokens);
                        if (integerExpressionNode2 != null) {
                            return new IntegerExpressionNode(integerExpressionNode1, integerExpressionNode2, operatorNode);
                        }
                    }
                    IntegerNode integerNode = IntegerNode.parseIntegerNode(tokens);
                    if (integerNode != null) {
                        return new IntegerExpressionNode(integerExpressionNode1, integerNode, operatorNode);
                    } else {
                        throw new InvalidParseException("Error: <i_expr> <op> not followed by <int>",
                                tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                    }
                }
            }
            return new IntegerExpressionNode(idNode);
        }

        return null;
    }

    public String getType(SymbolTable symbolTable) throws Exception{
        if (functionCallNode != null) {
            return functionCallNode.getType(symbolTable);
        }

        if (idNode != null) {
            return symbolTable.getType(idNode.getName());
        }

        if (integerExpressionNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    if (integerExpressionNode1.getType(symbolTable).equals(integerExpressionNode2.getType(symbolTable))){
                        return "Integer";
                    }
                    return null;
                }
                if(integerNode1 != null) {
                    return "Integer";
                }
            }
            return "Integer";
        }

        if (integerNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    if (integerExpressionNode2.getType(symbolTable).equals("Integer"))
                    return "Integer";
                }
                if(integerNode2 != null) {
                    return "Integer";
                }
            }
            return "Integer";
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

        if (integerExpressionNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    return integerExpressionNode1.convertToJott() + operatorNode.convertToJott() +
                            integerExpressionNode2.convertToJott();
                }
                if(integerNode1 != null) {
                    return integerExpressionNode1.convertToJott() + operatorNode.convertToJott() +
                            integerNode1.convertToJott();
                }
            }
            return integerExpressionNode1.convertToJott();
        }

        if (integerNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    return integerNode1.convertToJott() + operatorNode.convertToJott() +
                            integerExpressionNode2.convertToJott();
                }
                if(integerNode2 != null) {
                    return integerNode1.convertToJott() + operatorNode.convertToJott() +
                            integerNode2.convertToJott();
                }
            }
            return integerNode1.convertToJott();
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

        if (integerExpressionNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    return integerExpressionNode1.convertToJava() + operatorNode.convertToJava() +
                            integerExpressionNode2.convertToJava();
                }
                if(integerNode1 != null) {
                    return integerExpressionNode1.convertToJava() + operatorNode.convertToJava() +
                            integerNode1.convertToJava();
                }
            }
            return integerExpressionNode1.convertToJava();
        }

        if (integerNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    return integerNode1.convertToJava() + operatorNode.convertToJava() +
                            integerExpressionNode2.convertToJava();
                }
                if(integerNode2 != null) {
                    return integerNode1.convertToJava() + operatorNode.convertToJava() +
                            integerNode2.convertToJava();
                }
            }
            return integerNode1.convertToJava();
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

        if (integerExpressionNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    return integerExpressionNode1.convertToC() + operatorNode.convertToC() +
                            integerExpressionNode2.convertToC();
                }
                if(integerNode1 != null) {
                    return integerExpressionNode1.convertToC() + operatorNode.convertToC() +
                            integerNode1.convertToC();
                }
            }
            return integerExpressionNode1.convertToC();
        }

        if (integerNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    return integerNode1.convertToC() + operatorNode.convertToC() +
                            integerExpressionNode2.convertToC();
                }
                if(integerNode2 != null) {
                    return integerNode1.convertToC() + operatorNode.convertToC() +
                            integerNode2.convertToC();
                }
            }
            return integerNode1.convertToC();
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

        if (integerExpressionNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    return integerExpressionNode1.convertToPython(nestLevel) + operatorNode.convertToPython(nestLevel) +
                            integerExpressionNode2.convertToPython(nestLevel);
                }
                if(integerNode1 != null) {
                    return integerExpressionNode1.convertToPython(nestLevel) + operatorNode.convertToPython(nestLevel) +
                            integerNode1.convertToPython(nestLevel);
                }
            }
            return integerExpressionNode1.convertToPython(nestLevel);
        }

        if (integerNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    return integerNode1.convertToPython(nestLevel) + operatorNode.convertToPython(nestLevel) +
                            integerExpressionNode2.convertToPython(nestLevel);
                }
                if(integerNode2 != null) {
                    return integerNode1.convertToPython(nestLevel) + operatorNode.convertToPython(nestLevel) +
                            integerNode2.convertToPython(nestLevel);
                }
            }
            return integerNode1.convertToPython(nestLevel);
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

        if (integerExpressionNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    if (integerExpressionNode1.getType(symbolTable).equals(integerExpressionNode2.getType(symbolTable))) {
                        return integerExpressionNode1.validateTree(symbolTable) && operatorNode.validateTree(symbolTable) &&
                                integerExpressionNode2.validateTree(symbolTable);
                    }
                }
                if(integerNode1 != null) {
                    if (integerExpressionNode1.getType(symbolTable).equals("Integer")) {
                        return integerExpressionNode1.validateTree(symbolTable) && operatorNode.validateTree(symbolTable) &&
                                integerNode1.validateTree(symbolTable);
                    }
                }
            }
            return integerExpressionNode1.validateTree(symbolTable);
        }

        if (integerNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode2 != null) {
                    if (integerExpressionNode2.getType(symbolTable).equals("Integer")) {
                        return integerNode1.validateTree(symbolTable) && operatorNode.validateTree(symbolTable) &&
                                integerExpressionNode2.validateTree(symbolTable);
                    }
                }
                if(integerNode2 != null) {
                    return integerNode1.validateTree(symbolTable) && operatorNode.validateTree(symbolTable) &&
                            integerNode2.validateTree(symbolTable);
                }
            }
            return integerNode1.validateTree(symbolTable);
        }
        return false;
    }
}
