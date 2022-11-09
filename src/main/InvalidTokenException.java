package main;

public class InvalidTokenException extends Exception {

    private final String token;
    private final String fileName;
    private final String lineNumber;

    public InvalidTokenException(String token, String fileName, String lineNumber)
    {
        this.token = token;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public void printError(){
        System.err.println("Syntax Error: ");
        System.err.println("Invalid Token \"" + this.token + "\"");
        System.err.println(this.fileName + ":" + this.lineNumber);
    }
}
