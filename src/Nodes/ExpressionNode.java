package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Ben Froment
 */
public class ExpressionNode implements JottTree {

    private final IdNode idNode;
    private final FunctionCallNode functionCallNode;
    private final IntegerExpressionNode integerExpressionNode;
    private final DoubleExpressionNode doubleExpressionNode;
    private final StringExpressionNode stringExpressionNode;
    private final BooleanExpressionNode booleanExpressionNode;

    public ExpressionNode(IdNode idNode) {
        this.functionCallNode = null;
        this.integerExpressionNode = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;

        this.idNode = idNode;
    }
    public ExpressionNode(FunctionCallNode functionCallNode) {
        this.idNode = null;
        this.integerExpressionNode = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;

        this.functionCallNode = functionCallNode;
    }
    public ExpressionNode(IntegerExpressionNode integerExpressionNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;

        this.integerExpressionNode = integerExpressionNode;
    }
    public ExpressionNode(DoubleExpressionNode doubleExpressionNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.integerExpressionNode = null;
        this.stringExpressionNode = null;
        this.booleanExpressionNode = null;

        this.doubleExpressionNode = doubleExpressionNode;
    }
    public ExpressionNode(StringExpressionNode stringExpressionNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.integerExpressionNode = null;
        this.doubleExpressionNode = null;
        this.booleanExpressionNode = null;

        this.stringExpressionNode = stringExpressionNode;
    }
    public ExpressionNode(BooleanExpressionNode booleanExpressionNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.integerExpressionNode = null;
        this.doubleExpressionNode = null;
        this.stringExpressionNode = null;

        this.booleanExpressionNode = booleanExpressionNode;
    }

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
