package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Jott
{
    public static void main(String[] args) throws Exception
    {
        ArrayList<Token> tokenList;

        if (args.length != 3 || !(args[2].equals("Jott") || args[2].equals("Java") ||
                args[2].equals("C") || args[2].equals("Python"))) {
            // print help message
            System.out.println("""
                    Usage: java Jott <input_file> <output_file> <language>\s
                    - input_file - the .jott file to convert to new language
                    - output_file - the file to write the converted code into
                    - language - the language to convert to (Jott | Java | C | Python)
                    """);
            return;
        }

        tokenList = JottTokenizer.tokenize(args[0]);

        assert tokenList != null;
        JottTree tree = JottParser.parse(tokenList);
        if (tree != null) {
            try {
                boolean success = tree.validateTree(new SymbolTable());
                System.out.println(success);
                BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
                switch (args[2]) {
                    case "Jott":
                        writer.write(tree.convertToJott());
                        break;
                    case "C":
                        writer.write(tree.convertToC());
                        break;
                    case "Java":
                        writer.write(tree.convertToJava());
                        break;
                    case "Python":
                        writer.write(tree.convertToPython(0));
                        break;
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
