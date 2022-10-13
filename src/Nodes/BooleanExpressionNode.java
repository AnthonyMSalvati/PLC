package Nodes;

import main.InvalidParseException;
import main.JottTree;
import main.Token;
import java.util.ArrayList;

/**
 * @author Ben Froment
 */
public class BooleanExpressionNode implements JottTree {

    private final IdNode idNode;
    private final FunctionCallNode functionCallNode;
    private final BooleanNode booleanNode;
    private final IntegerExpressionNode integerExpressionNode1;
    private final IntegerExpressionNode integerExpressionNode2;
    private final DoubleExpressionNode doubleExpressionNode1;
    private final DoubleExpressionNode doubleExpressionNode2;
    private final StringExpressionNode stringExpressionNode1;
    private final StringExpressionNode stringExpressionNode2;
    private final BooleanExpressionNode booleanExpressionNode1;
    private final BooleanExpressionNode booleanExpressionNode2;
    private final RelationOperatorNode relationOperatorNode;

    public BooleanExpressionNode(IdNode idNode) {
        this.idNode = idNode;
        this.functionCallNode = null;
        this.booleanNode = null;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;
        this.stringExpressionNode1 = null;
        this.stringExpressionNode2 = null;
        this.booleanExpressionNode1 = null;
        this.booleanExpressionNode2 = null;
        this.relationOperatorNode = null;
    }
    public BooleanExpressionNode(FunctionCallNode functionCallNode) {
        this.idNode = null;
        this.functionCallNode = functionCallNode;
        this.booleanNode = null;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;
        this.stringExpressionNode1 = null;
        this.stringExpressionNode2 = null;
        this.booleanExpressionNode1 = null;
        this.booleanExpressionNode2 = null;
        this.relationOperatorNode = null;
    }
    public BooleanExpressionNode(BooleanNode booleanNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.booleanNode = booleanNode;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;
        this.stringExpressionNode1 = null;
        this.stringExpressionNode2 = null;
        this.booleanExpressionNode1 = null;
        this.booleanExpressionNode2 = null;
        this.relationOperatorNode = null;
    }
    public BooleanExpressionNode(IntegerExpressionNode integerExpressionNode1, IntegerExpressionNode integerExpressionNode2,
                                 RelationOperatorNode relationOperatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.booleanNode = null;
        this.integerExpressionNode1 = integerExpressionNode1;
        this.integerExpressionNode2 = integerExpressionNode2;
        this.relationOperatorNode = relationOperatorNode;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;
        this.stringExpressionNode1 = null;
        this.stringExpressionNode2 = null;
        this.booleanExpressionNode1 = null;
        this.booleanExpressionNode2 = null;
    }
    public BooleanExpressionNode(DoubleExpressionNode doubleExpressionNode1, DoubleExpressionNode doubleExpressionNode2,
                                 RelationOperatorNode relationOperatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.booleanNode = null;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;
        this.doubleExpressionNode1 = doubleExpressionNode1;
        this.doubleExpressionNode2 = doubleExpressionNode2;
        this.relationOperatorNode = relationOperatorNode;
        this.stringExpressionNode1 = null;
        this.stringExpressionNode2 = null;
        this.booleanExpressionNode1 = null;
        this.booleanExpressionNode2 = null;
    }
    public BooleanExpressionNode(StringExpressionNode stringExpressionNode1, StringExpressionNode stringExpressionNode2,
                                 RelationOperatorNode relationOperatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.booleanNode = null;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;
        this.stringExpressionNode1 = stringExpressionNode1;
        this.stringExpressionNode2 = stringExpressionNode2;
        this.relationOperatorNode = relationOperatorNode;
        this.booleanExpressionNode1 = null;
        this.booleanExpressionNode2 = null;
    }
    public BooleanExpressionNode(BooleanExpressionNode booleanExpressionNode1, BooleanExpressionNode booleanExpressionNode2,
                                 RelationOperatorNode relationOperatorNode) {
        this.idNode = null;
        this.functionCallNode = null;
        this.booleanNode = null;
        this.integerExpressionNode1 = null;
        this.integerExpressionNode2 = null;
        this.doubleExpressionNode1 = null;
        this.doubleExpressionNode2 = null;
        this.stringExpressionNode1 = null;
        this.stringExpressionNode2 = null;
        this.booleanExpressionNode1 = booleanExpressionNode1;
        this.booleanExpressionNode2 = booleanExpressionNode2;
        this.relationOperatorNode = relationOperatorNode;
    }

    public static BooleanExpressionNode parseBooleanExpressionNode (ArrayList<Token> tokens) throws Exception {

        BooleanNode booleanNode = BooleanNode.parseBooleanNode(tokens);
        if (booleanNode != null) {
            return new BooleanExpressionNode(booleanNode);
        }
        if (tokens.size() > 1) {
            if (tokens.size() > 2) {
                try {
                    Integer.parseInt(tokens.get(2).getToken());
                    IntegerExpressionNode integerExpressionNode1 = IntegerExpressionNode.parseIntegerExpressionNode(tokens);
                    if (integerExpressionNode1 != null) {
                        RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(tokens);
                        if (relationOperatorNode != null) {
                            IntegerExpressionNode integerExpressionNode2 = IntegerExpressionNode.parseIntegerExpressionNode(tokens);
                            if (integerExpressionNode2 != null) {
                                return new BooleanExpressionNode(integerExpressionNode1, integerExpressionNode2, relationOperatorNode);
                            }
                            throw new InvalidParseException("Error: expected <i_expr>", tokens.get(0).getFilename(),
                                    tokens.get(0).getLineNum());
                        }
                        throw new InvalidParseException("Error: expected <rel_op>", tokens.get(0).getFilename(),
                                tokens.get(0).getLineNum());
                    }
                }   catch (NumberFormatException ignored) {
                    DoubleExpressionNode doubleExpressionNode1 = DoubleExpressionNode.parseDoubleExpressionNode(tokens);
                    if (doubleExpressionNode1 != null) {
                        RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(tokens);
                        if (relationOperatorNode != null) {
                            DoubleExpressionNode doubleExpressionNode2 = DoubleExpressionNode.parseDoubleExpressionNode(tokens);
                            if (doubleExpressionNode2 != null) {
                                return new BooleanExpressionNode(doubleExpressionNode1, doubleExpressionNode2, relationOperatorNode);
                            }
                            throw new InvalidParseException("Error: expected <d_expr>", tokens.get(0).getFilename(),
                                    tokens.get(0).getLineNum());
                        }
                        throw new InvalidParseException("Error: expected <rel_op>", tokens.get(0).getFilename(),
                                tokens.get(0).getLineNum());
                    }
                }
                StringExpressionNode stringExpressionNode1 = StringExpressionNode.parseStringExpressionNode(tokens);
                if (stringExpressionNode1 != null) {
                    RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(tokens);
                    if (relationOperatorNode != null) {
                        StringExpressionNode stringExpressionNode2 = StringExpressionNode.parseStringExpressionNode(tokens);
                        if (stringExpressionNode2 != null) {
                            return new BooleanExpressionNode(stringExpressionNode1, stringExpressionNode2, relationOperatorNode);
                        }
                        throw new InvalidParseException("Error: expected <s_expr>", tokens.get(0).getFilename(),
                                tokens.get(0).getLineNum());
                    }
                    throw new InvalidParseException("Error: expected <rel_op>", tokens.get(0).getFilename(),
                            tokens.get(0).getLineNum());
                }
                BooleanExpressionNode booleanExpressionNode1 = BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                if (booleanExpressionNode1 != null) {
                    RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(tokens);
                    if (relationOperatorNode != null) {
                        BooleanExpressionNode booleanExpressionNode2 = BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                        if (booleanExpressionNode2 != null) {
                            return new BooleanExpressionNode(booleanExpressionNode1, booleanExpressionNode2, relationOperatorNode);
                        }
                        throw new InvalidParseException("Error: expected <b_expr>", tokens.get(0).getFilename(),
                                tokens.get(0).getLineNum());
                    }
                    throw new InvalidParseException("Error: expected <rel_op>", tokens.get(0).getFilename(),
                            tokens.get(0).getLineNum());
                }
            }
        }
        IntegerExpressionNode integerExpressionNode1 = IntegerExpressionNode.parseIntegerExpressionNode(tokens);
        if (integerExpressionNode1 != null) {
            RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(tokens);
            if (relationOperatorNode != null) {
                IntegerExpressionNode integerExpressionNode2 = IntegerExpressionNode.parseIntegerExpressionNode(tokens);
                if (integerExpressionNode2 != null) {
                    return new BooleanExpressionNode(integerExpressionNode1, integerExpressionNode2, relationOperatorNode);
                }
                throw new InvalidParseException("Error: expected <i_expr>", tokens.get(0).getFilename(),
                        tokens.get(0).getLineNum());
            }
            throw new InvalidParseException("Error: expected <rel_op>", tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum());
        }
        DoubleExpressionNode doubleExpressionNode1 = DoubleExpressionNode.parseDoubleExpressionNode(tokens);
        if (doubleExpressionNode1 != null) {
            RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(tokens);
            if (relationOperatorNode != null) {
                DoubleExpressionNode doubleExpressionNode2 = DoubleExpressionNode.parseDoubleExpressionNode(tokens);
                if (doubleExpressionNode2 != null) {
                    return new BooleanExpressionNode(doubleExpressionNode1, doubleExpressionNode2, relationOperatorNode);
                }
                throw new InvalidParseException("Error: expected <d_expr>", tokens.get(0).getFilename(),
                        tokens.get(0).getLineNum());
            }
            throw new InvalidParseException("Error: expected <rel_op>", tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum());
        }
        StringExpressionNode stringExpressionNode1 = StringExpressionNode.parseStringExpressionNode(tokens);
        if (stringExpressionNode1 != null) {
            RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(tokens);
            if (relationOperatorNode != null) {
                StringExpressionNode stringExpressionNode2 = StringExpressionNode.parseStringExpressionNode(tokens);
                if (stringExpressionNode2 != null) {
                    return new BooleanExpressionNode(stringExpressionNode1, stringExpressionNode2, relationOperatorNode);
                }
                throw new InvalidParseException("Error: expected <s_expr>", tokens.get(0).getFilename(),
                        tokens.get(0).getLineNum());
            }
            throw new InvalidParseException("Error: expected <rel_op>", tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum());
        }
        BooleanExpressionNode booleanExpressionNode1 = BooleanExpressionNode.parseBooleanExpressionNode(tokens);
        if (booleanExpressionNode1 != null) {
            RelationOperatorNode relationOperatorNode = RelationOperatorNode.parseRelationOperatorNode(tokens);
            if (relationOperatorNode != null) {
                BooleanExpressionNode booleanExpressionNode2 = BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                if (booleanExpressionNode2 != null) {
                    return new BooleanExpressionNode(booleanExpressionNode1, booleanExpressionNode2, relationOperatorNode);
                }
                throw new InvalidParseException("Error: expected <b_expr>", tokens.get(0).getFilename(),
                        tokens.get(0).getLineNum());
            }
            throw new InvalidParseException("Error: expected <rel_op>", tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum());
        }
        FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
        if (functionCallNode != null) {
            return new BooleanExpressionNode(functionCallNode);
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            return new BooleanExpressionNode(idNode);
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
        if (booleanNode != null) {
            return booleanNode.convertToJott();
        }
        if (integerExpressionNode1 != null) {
            if (relationOperatorNode != null) {
                if (integerExpressionNode2 != null) {
                    return integerExpressionNode1.convertToJott() + relationOperatorNode.convertToJott() +
                            integerExpressionNode2.convertToJott();
                }
            }
        }
        if (doubleExpressionNode1 != null) {
            if (relationOperatorNode != null) {
                if (doubleExpressionNode2 != null) {
                    return doubleExpressionNode1.convertToJott() + relationOperatorNode.convertToJott() +
                            doubleExpressionNode2.convertToJott();
                }
            }
        }
        if (stringExpressionNode1 != null) {
            if (relationOperatorNode != null) {
                if (stringExpressionNode2 != null) {
                    return stringExpressionNode1.convertToJott() + relationOperatorNode.convertToJott() +
                            stringExpressionNode2.convertToJott();
                }
            }
        }
        if (booleanExpressionNode1 != null) {
            if (relationOperatorNode != null) {
                if (booleanExpressionNode2 != null) {
                    return booleanExpressionNode1.convertToJott() + relationOperatorNode.convertToJott() +
                            booleanExpressionNode2.convertToJott();
                }
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
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
