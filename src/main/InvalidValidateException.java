package main;

/**
 * @author Ben Froment
 */
public class InvalidValidateException extends Exception{

    private final String message;
    private final String fileName;
    private final int lineNumber;

    public InvalidValidateException(String message, String fileName, int lineNumber) {
        this.message = message;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    // Prints the parse exception to stdErr
    public void printError(){
        System.err.println("Semantic Error: ");
        System.err.println(this.message);
        System.err.println(this.fileName + ":" + this.lineNumber);
    }
}