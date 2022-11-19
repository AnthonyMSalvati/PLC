package Nodes;

import main.InvalidValidateException;
import main.JottTree;
import main.SymbolTable;
import main.Token;

import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node that represents the entire Program as a tree
 */
public class ProgramNode implements JottTree {

    private final FunctionListNode functionListNode;
    private final Token token;

    // < function_list >
    public ProgramNode(FunctionListNode functionListNode, Token token) {
        this.functionListNode = functionListNode;
        this.token = token;
    }

    // Function that calls its child nodes to parse the list of tokens
    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception {
        Token token = null;
        if (tokens.size() > 0){
            token = tokens.get(0);
        }
        FunctionListNode functionListNode = FunctionListNode.parseFunctionListNode(tokens);
        if (functionListNode != null) {
            return new ProgramNode(functionListNode, token);
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (functionListNode != null) {
            return functionListNode.convertToJott();
        }
        return "";
    }

    @Override
    public String convertToJava() {

        String fileName = token.getFilename();
        String className = fileName.split("\\.")[0];
        String header = "import java.util.Scanner;\n";
        header = header + "public class " + className + " {\n";
        // input function
        header = header + "public static String input(String msg, int length){\n";
        header = header + "\tSystem.out.print(msg);\n\tScanner in = new Scanner(System.in);\n";
        header = header + "\tString input = in.nextLine();\n\treturn input;\n}\n";

        // concat function
        header = header + "public static String concat(String s1, String s2){\n";
        header = header + "\treturn s1.concat(s2);\n}\n";

        // length function
        header = header + "public static int length(String s){\n";
        header = header + "\treturn s.length();\n}\n";

        if (functionListNode != null) {
            return header + functionListNode.convertToJava() + " }";
        }
        return "";
    }

    @Override
    public String convertToC() {
        String header = "#include <stdio.h>\n"
                + "#include <string.h>\n"
                + "#include <stdlib.h>\n"
                + "#include <stdbool.h>\n";
        // input function
        header = header + "char * input(char * msg, int length){\n";
        header = header + "\tprintf(\"%s\", msg);\n\tfflush (stdout);\n\tchar s[length];\n";
        header = header + "\tfgets(s, length, stdin);\n\treturn s;\n}\n";

        // concat function
        header = header + "char * concat(char * s1, char * s2){\n";
        header = header + "\treturn strcat(s1, s2);\n}\n";

        // length function
        header = header + "int length(char * s){\n";
        header = header + "\treturn strlen(s);\n}\n";

        // print functions
        // int
        header = header + "void print(int val){\n";
        header = header + "\tprintf(\"%d\\n\", val);\n";
        header = header + "\tfflush (stdout);\n}\n";

        // double
        header = header + "void print(double val){\n";
        header = header + "\tprintf(\"%lf\\n\", val);\n";
        header = header + "\tfflush (stdout);\n}\n";

        // string
        header = header + "void print(char* val){\n";
        header = header + "\tprintf(\"%s\\n\", val);\n";
        header = header + "\tfflush (stdout);\n}\n";

        // boolean
        header = header + "void print(bool val){\n";
        header = header + "\tprintf(\"%B\\n\", val);\n";
        header = header + "\tfflush (stdout);\n}\n";

        if (functionListNode != null) {
            return header + functionListNode.convertToC();
        }
        return "";
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
        // input function
        String builtInFunctions = "def inputFunc(msg, length):\n";
        builtInFunctions = builtInFunctions + "\treturn input(msg)\n";

        // concat function
        builtInFunctions = builtInFunctions + "def concat(s1, s2):\n";
        builtInFunctions = builtInFunctions + "\treturn s1 + s2\n";

        // length function
        builtInFunctions = builtInFunctions + "def length(s):\n";
        builtInFunctions = builtInFunctions + "\treturn len(s)\n";

        if (functionListNode != null) {
			// nestLevel should be zero
			return builtInFunctions + functionListNode.convertToPython(0) + "main()";
		}
		return "";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (functionListNode != null) {
            if (functionListNode.validateTree(symbolTable)) {
                if (!symbolTable.hasFunction("main")) {
                    if (token != null) {
                        throw new InvalidValidateException("Missing Void function main", token.getFilename(), token.getLineNum());
                    }
                    return false;
                }
                if (!symbolTable.getFunctionReturnType("main").equals("Void")){
                    if (token != null) {
                        throw new InvalidValidateException("main function is not type Void", token.getFilename(), token.getLineNum());
                    }
                    return false;
                }
                if (symbolTable.hasKeywords()){
                    if (token != null) {
                        throw new InvalidValidateException("Cannot use keyword as variable name or function name",
                                token.getFilename(), token.getLineNum());
                    }
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
