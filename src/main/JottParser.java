package main;

import Nodes.*;
import java.util.ArrayList;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author Ben Froment
 */
public class JottParser {

    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens){

        ProgramNode node;
        try {
            node = ProgramNode.parseProgramNode(tokens);
        } catch (InvalidParseException ipe) {
            ipe.printError();
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return node;
    }
}
