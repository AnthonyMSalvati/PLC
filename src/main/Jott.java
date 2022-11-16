package main;

import Nodes.ProgramNode;

import java.util.ArrayList;


public class Jott
{
    public static void main(String[] args) throws Exception
    {
        ArrayList<Token> tokenList;

        tokenList = JottTokenizer.tokenize(args[0]);

        assert tokenList != null;
        JottTree tree = JottParser.parse(tokenList);
        if (tree != null) {
            try {
                boolean success = tree.validateTree(new SymbolTable());
                System.out.println(success);
            } catch (InvalidValidateException ive) {
                ive.printError();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println();




    }
}
