public class InvalidTokenException extends Exception {

    public InvalidTokenException(String token, String fileName, String lineNumber)
    {
        System.out.println("Syntax Error");
        System.out.println("Invalid Token \"" + token + "\"");
        System.out.println(fileName + ":" + lineNumber);
    }
}
