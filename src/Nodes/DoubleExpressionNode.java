package Nodes;

import main.InvalidParseException;
import main.JottTree;
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
    private final DoubleExpressionNode doubleExpressionNode;
    private final FunctionCallNode functionCallNode;

    // < id >
    public DoubleExpressionNode(IdNode idNode) {
        this.doubleNode1 = null;
        this.doubleNode2 = null;
        this.operatorNode = null;
        this.doubleExpressionNode = null;
        this.functionCallNode = null;

        this.idNode = idNode;
    }

    // < func_call >
    public DoubleExpressionNode(FunctionCallNode functionCallNode) {
        this.idNode = null;
        this.doubleNode1 = null;
        this.doubleNode2 = null;
        this.operatorNode = null;
        this.doubleExpressionNode = null;

        this.functionCallNode = functionCallNode;
    }

    // < dbl >
    public DoubleExpressionNode(DoubleNode doubleNode1) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleNode2 = null;
        this.operatorNode = null;
        this.doubleExpressionNode = null;

        this.doubleNode1 = doubleNode1;
    }

    // < dbl > < op > < dbl >
    public DoubleExpressionNode(DoubleNode doubleNode1, DoubleNode doubleNode2, OperatorNode operatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleExpressionNode = null;

        this.doubleNode1 = doubleNode1;
        this.doubleNode2 = doubleNode2;
        this.operatorNode = operatorNode;
    }

    // < dbl > < op> < d_expr >
    public DoubleExpressionNode(DoubleNode doubleNode1, DoubleExpressionNode doubleExpressionNode,
                                OperatorNode operatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleNode2 = null;

        this.doubleNode1 = doubleNode1;
        this.doubleExpressionNode = doubleExpressionNode;
        this.operatorNode = operatorNode;
    }

    // < id > < op > < d_expr >
    public DoubleExpressionNode(IdNode idNode, DoubleExpressionNode doubleExpressionNode,
                                OperatorNode operatorNode) {
        this.functionCallNode = null;
        this.doubleNode1 = null;
        this.doubleNode2 = null;

        this.idNode = idNode;
        this.doubleExpressionNode = doubleExpressionNode;
        this.operatorNode = operatorNode;
    }

    // < id > < op > < dbl >
    public DoubleExpressionNode(IdNode idNode, DoubleNode doubleNode,
                                OperatorNode operatorNode) {
        this.functionCallNode = null;
        this.doubleNode2 = null;
        this.doubleExpressionNode = null;

        this.idNode = idNode;
        this.doubleNode1 = doubleNode;
        this.operatorNode = operatorNode;
    }

    // Function called by its parent node to parse the list of tokens
    public static DoubleExpressionNode parseDoubleExpressionNode (ArrayList<Token> tokens) throws Exception {
        FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
        if (functionCallNode != null) {
            return new DoubleExpressionNode(functionCallNode);
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            OperatorNode operatorNode = OperatorNode.parseOperatorNode(tokens);
            if (operatorNode != null) {
                OperatorNode operatorNode2 = OperatorNode.parseOperatorNode(new ArrayList<>(Collections.singletonList(tokens.get(1))));
                if (operatorNode2 != null) {
                    DoubleExpressionNode doubleExpressionNode = parseDoubleExpressionNode(tokens);
                    if (doubleExpressionNode != null) {
                        return new DoubleExpressionNode(idNode, doubleExpressionNode, operatorNode);
                    }
                }

                DoubleNode doubleNode =  DoubleNode.parseDoubleNode(tokens);
                if (doubleNode != null) {
                    return new DoubleExpressionNode(idNode, doubleNode, operatorNode);
                } else {
                    throw new InvalidParseException("Error: <dbl> <op> not followed by <dbl>", tokens.get(0).getFilename(),
                            tokens.get(0).getLineNum());
                }
            } else {
                return new DoubleExpressionNode(idNode);
            }
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

        return null;
    }

    @Override
    public String convertToJott() {

        if (functionCallNode != null) {
            return functionCallNode.convertToJott();
        }

        if (idNode != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode != null) {
                    return idNode.convertToJott() + operatorNode.convertToJott() +
                            doubleExpressionNode.convertToJott();
                }
                if (doubleNode1 != null) {
                    return idNode.convertToJott() + operatorNode.convertToJott() +
                            doubleNode1.convertToJott();
                }
            }
            return idNode.convertToJott();
        }

        if (doubleNode1 != null) {
            if (operatorNode != null) {
                if (doubleExpressionNode != null) {
                    return doubleNode1.convertToJott() + operatorNode.convertToJott() +
                            doubleExpressionNode.convertToJott();
                }
                if (doubleNode2 != null) {
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
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
