package main;

public class InvalidTokenException extends Exception {

    public InvalidTokenException(String token, String fileName, String lineNumber)
    {
        System.err.println("Syntax Error");
        System.err.println("Invalid Token \"" + token + "\"");
        System.err.println(fileName + ":" + lineNumber);
    }
}
