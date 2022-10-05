package Nodes;

import main.JottTree;
import main.Token;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Ben Froment
 */
public class IntegerExpressionNode implements JottTree {

    private final IdNode idNode;
    private final IntegerNode integerNode1;
    private final IntegerNode integerNode2;
    private final OperatorNode operatorNode;
    private final IntegerExpressionNode integerExpressionNode;
    private final FunctionCallNode functionCallNode;

    public IntegerExpressionNode(IdNode idNode) {
        this.integerNode1 = null;
        this.integerNode2 = null;
        this.operatorNode = null;
        this.integerExpressionNode = null;
        this.functionCallNode = null;

        this.idNode = idNode;
    }

    public IntegerExpressionNode (FunctionCallNode functionCallNode){
        this.idNode = null;
        this.integerNode1 = null;
        this.integerNode2 = null;
        this.operatorNode = null;
        this.integerExpressionNode = null;

        this.functionCallNode = functionCallNode;
    }

    public IntegerExpressionNode (IntegerNode integerNode1) {
        this.idNode = null;
        this.integerNode2 = null;
        this.operatorNode = null;
        this.integerExpressionNode = null;
        this.functionCallNode = null;

        this.integerNode1 = integerNode1;
    }

    public IntegerExpressionNode (IntegerNode integerNode1, IntegerNode integerNode2, OperatorNode operatorNode) {
        this.idNode = null;
        this.integerExpressionNode = null;
        this.functionCallNode = null;

        this.integerNode1 = integerNode1;
        this.integerNode2 = integerNode2;
        this.operatorNode = operatorNode;
    }

    public IntegerExpressionNode (IntegerNode integerNode1, IntegerExpressionNode integerExpressionNode,
                                  OperatorNode operatorNode) {
        this.idNode = null;
        this.integerNode2 = null;
        this.functionCallNode = null;

        this.integerNode1 = integerNode1;
        this.operatorNode = operatorNode;
        this.integerExpressionNode = integerExpressionNode;
    }

    public static IntegerExpressionNode parseIntegerExpressionNode (ArrayList<Token> tokens) throws Exception {
        FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
        if (functionCallNode != null) {
            return new IntegerExpressionNode(functionCallNode);
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            return new IntegerExpressionNode(idNode);
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

                IntegerNode integerNode2 = IntegerNode.parseIntegerNode(tokens);
                if (integerNode2 != null) {
                    return new IntegerExpressionNode(integerNode1, integerNode2, operatorNode);
                } else {
                    throw new Exception("Error: <int> <op> not followed by <int>");
                }
            } else {
                return new IntegerExpressionNode(integerNode1);
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

        if (integerNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode != null) {
                    return integerNode1.convertToJott() + operatorNode.convertToJott() +
                            integerExpressionNode.convertToJott();
                }
                if(integerNode2 != null) {
                    return integerNode1.convertToJott() + operatorNode.convertToJott() +
                            integerNode2.convertToJott();
                }
            }
            return integerNode1.convertToJott();
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
