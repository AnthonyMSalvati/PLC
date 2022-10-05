package Nodes;

import main.JottTree;
import main.Token;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Ben Froment
 */
public class DoubleExpressionNode implements JottTree {

    private final IdNode idNode;
    private final DoubleNode doubleNode1;
    private final DoubleNode doubleNode2;
    private final OperatorNode operatorNode;
    private final DoubleExpressionNode doubleExpressionNode;
    private final FunctionCallNode functionCallNode;

    public DoubleExpressionNode(IdNode idNode) {
        this.doubleNode1 = null;
        this.doubleNode2 = null;
        this.operatorNode = null;
        this.doubleExpressionNode = null;
        this.functionCallNode = null;

        this.idNode = idNode;
    }
    public DoubleExpressionNode(FunctionCallNode functionCallNode) {
        this.idNode = null;
        this.doubleNode1 = null;
        this.doubleNode2 = null;
        this.operatorNode = null;
        this.doubleExpressionNode = null;

        this.functionCallNode = functionCallNode;
    }
    public DoubleExpressionNode(DoubleNode doubleNode1) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleNode2 = null;
        this.operatorNode = null;
        this.doubleExpressionNode = null;

        this.doubleNode1 = doubleNode1;
    }
    public DoubleExpressionNode(DoubleNode doubleNode1, DoubleNode doubleNode2, OperatorNode operatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleExpressionNode = null;

        this.doubleNode1 = doubleNode1;
        this.doubleNode2 = doubleNode2;
        this.operatorNode = operatorNode;
    }
    public DoubleExpressionNode(DoubleNode doubleNode1, DoubleExpressionNode doubleExpressionNode,
                                OperatorNode operatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleNode2 = null;

        this.doubleNode1 = doubleNode1;
        this.doubleExpressionNode = doubleExpressionNode;
        this.operatorNode = operatorNode;
    }

    public static DoubleExpressionNode parseDoubleExpressionNode (ArrayList<Token> tokens) throws Exception {
        FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
        if (functionCallNode != null) {
            return new DoubleExpressionNode(functionCallNode);
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            return new DoubleExpressionNode(idNode);
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
                    throw new Exception("Error: <dbl> <op> not followed by <dbl>");
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
        return null;
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
