package Nodes;

import main.JottTree;
import main.Token;
import java.util.ArrayList;
import java.util.Collections;

public class IntegerExpressionNode implements JottTree {

    private final IdNode idNode;
    private final IntegerNode integerNode1;
    private final IntegerNode integerNode2;
    private final OperatorNode operatorNode;
    private final IntegerExpressionNode integerExpressionNode;
    private final FunctionCallNode functionCallNode;

    public IntegerExpressionNode(IdNode idNode, IntegerNode integerNode1, IntegerNode integerNode2,
                                 OperatorNode operatorNode, IntegerExpressionNode integerExpressionNode,
                                 FunctionCallNode functionCallNode) {
        this.idNode = idNode;
        this.integerNode1 = integerNode1;
        this.integerNode2 = integerNode2;
        this.operatorNode = operatorNode;

        this.integerExpressionNode = integerExpressionNode;
        this.functionCallNode = functionCallNode;
    }

    public static IntegerExpressionNode parseIntegerExpressionNode (ArrayList<Token> tokens) throws Exception {
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
            if (functionCallNode != null) {
                return new IntegerExpressionNode(null, null, null, null,
                        null, functionCallNode);
            }
            return new IntegerExpressionNode(idNode, null, null, null, null,
                    null);
        }
        IntegerNode integerNode1 = IntegerNode.parseIntegerNode(tokens);
        if (integerNode1 != null) {
            OperatorNode operatorNode = OperatorNode.parseOperatorNode(tokens);
            if (operatorNode != null) {
                OperatorNode operatorNode2 = OperatorNode.parseOperatorNode(new ArrayList<>(Collections.singletonList(tokens.get(1))));
                if (operatorNode2 != null) {
                    IntegerExpressionNode integerExpressionNode = parseIntegerExpressionNode(tokens);
                    if (integerExpressionNode != null) {
                        return new IntegerExpressionNode(null, integerNode1, null, operatorNode,
                                integerExpressionNode, null);
                    }
                }

                IntegerNode integerNode2 = IntegerNode.parseIntegerNode(tokens);
                if (integerNode2 != null) {
                    return new IntegerExpressionNode(null, integerNode1, integerNode2, operatorNode, null,
                            null);
                } else {
                    throw new Exception("Error: <int> <op> not followed by <int>");
                }
            } else {
                return new IntegerExpressionNode(null, integerNode1, null, null, null,
                        null);
            }
        }

        return null;
    }

    @Override
    public String convertToJott() {
        if (idNode != null) {
            return idNode.convertToJott();
        }

        if (functionCallNode != null) {
            return functionCallNode.convertToJott();
        }

        if (integerNode1 != null) {
            if (operatorNode != null) {
                if (integerExpressionNode != null) {
                    return integerNode1.convertToJott() + operatorNode.convertToJott() +
                            integerExpressionNode.convertToJott();
                }
                return integerNode1.convertToJott() + operatorNode.convertToJott() +
                        integerNode2.convertToJott();
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
