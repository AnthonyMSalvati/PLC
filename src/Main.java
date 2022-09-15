

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main
{
    public static void main(String[] args)
    {
        ArrayList<Token> tokenList;

        try {
            tokenList = JottTokenizer.tokenize(args[0]);
        }
        catch (InvalidTokenException e)
        {
            return;
        }

        assert tokenList != null;
        for (Token token : tokenList) {
            System.out.print(token.getTokenType() + " ");
        }
        System.out.println();




    }
}
