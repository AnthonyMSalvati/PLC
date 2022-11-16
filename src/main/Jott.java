package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
                BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
                switch (args[2]) {
                    case "Jott" -> writer.write(tree.convertToJott());
                    case "C" -> writer.write(tree.convertToC());
                    case "Java" -> writer.write(tree.convertToJava());
                    case "Python" -> writer.write(tree.convertToPython(0));
                }
                writer.close();
            } catch (InvalidValidateException ive) {
                ive.printError();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println();




    }
}
