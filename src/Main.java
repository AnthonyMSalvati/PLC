

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

        tokenList = JottTokenizer.tokenize(args[0]);


        assert tokenList != null;
        if (tokenList.size() == 1)
        {
            System.out.println("Syntax Error");
            System.out.println("Invalid Token \"" + tokenList.get(0).getToken() + "\"");
            System.out.println(args[0] + ":" + tokenList.get(0).getLineNum());
        }
        else
        {
            for (Token token : tokenList) {
                System.out.print(token.getTokenType() + " ");
            }
            System.out.println();
        }
    }
}
