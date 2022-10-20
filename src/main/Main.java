package main;

import java.util.ArrayList;


public class Main
{
    public static void main(String[] args)
    {
        ArrayList<Token> tokenList;

        tokenList = JottTokenizer.tokenize(args[0]);

        assert tokenList != null;
        for (Token token : tokenList) {
            System.out.print(token.getTokenType() + " ");
        }
        System.out.println();




    }
}
