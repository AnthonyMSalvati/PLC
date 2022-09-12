/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename) throws IOException {
		ArrayList<Token> tokenList = new ArrayList<>();
		File file = new File(filename);
		FileReader reader;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		}
		int character;
		ArrayList<Character> fileContents = new ArrayList<>();
		while((character = reader.read()) != -1)
		{
			fileContents.add((char) character);
		}
		int lineNumber = 0;
		for (int i = 0; i < fileContents.size(); i++) {
			// if(newline) { lineNumber++;}

			if (false) {

			} else if (fileContents.get(i) == '/' || fileContents.get(i) == '+' ||
					fileContents.get(i) == '-' || fileContents.get(i) == '*') {
				Token token;
				token = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.MATH_OP);
				tokenList.add(token);
			} else if (fileContents.get(i) == ';') {
				Token token;
				token = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.SEMICOLON);
				tokenList.add(token);
			} else if (fileContents.get(i) == ':') {
				Token token;
				token = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.COLON);
				tokenList.add(token);
			} else if (fileContents.get(i) == '=') {
				Token tok;
				if (fileContents.get(i + 1) == '=') {
					tok = new Token("==", filename, lineNumber, TokenType.REL_OP);
				} else tok = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.ASSIGN);
				tokenList.add(tok);
			} else if (fileContents.get(i) == '!') {
				if (fileContents.get(i + 1) == '=') {
					Token tok = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.REL_OP);
					tokenList.add(tok);
				} else //deletes tokens previously found, adds error "token" so it doesn't print stuff when in error state
				{
					tokenList.clear();
					Token tok = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.REL_OP); //gets location and string
					tokenList.add(tok);
					return tokenList;
				}
			} else if (fileContents.get(i) == '<') {
				Token tok;
				if (fileContents.get(i + 1) == '=') {
					tok = new Token("<=", filename, lineNumber, TokenType.REL_OP);
				} else tok = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.REL_OP);
				tokenList.add(tok);
			} else if (fileContents.get(i) == '>') {
				Token tok;
				if (fileContents.get(i + 1) == '=') {
					tok = new Token(">=", filename, lineNumber, TokenType.REL_OP);
				} else tok = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.REL_OP);
				tokenList.add(tok);

			}
		}
		return tokenList;
	}
}