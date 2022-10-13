package main;

public class InvalidParseException extends Exception{

    public InvalidParseException(String message, String fileName, String lineNumber)
    {
        System.err.println("Syntax Error");
        System.err.println(message);
        System.err.println(fileName + ":" + lineNumber);
    }
}
