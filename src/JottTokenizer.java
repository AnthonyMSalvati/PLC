/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.io.*;
import java.util.ArrayList;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename) {
		ArrayList<Token> tokenList;
		try {
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
			while ((character = reader.read()) != -1) {
				fileContents.add((char) character);
			}
			tokenList = tokenizeHelper(fileContents, filename);
		} catch (IOException io) {
			System.out.println("IO error");
			return null;
		}
		return tokenList;
	}


	private static ArrayList<Token> tokenizeHelper (ArrayList<Character> fileContents, String filename) {
		ArrayList<Token> tokenList = new ArrayList<>();

		int lineNumber = 1;
		for (int i = 0; i < fileContents.size(); i++) {
			// if(newline) { lineNumber++;}

			// skip over whitespaces and handle single-line comments
			// need to change later to handle multi-line comments
			while (i != fileContents.size() && (fileContents.get(i) == ' ' || fileContents.get(i) == '#')) {
				if (fileContents.get(i) == '#') { //handle single-line comments
					while (i != fileContents.size() && !(fileContents.get(i).equals('\n'))) {
						i += 1;
					}
					lineNumber += 1; //this may not play nicely with multi-line comments yet
				} else {
					i += 1;

				}
			}

			if (fileContents.get(i) == ',') { //COMMA
				tokenList.add(new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.COMMA));
			} else if (fileContents.get(i) == '[') { //BRACKET LEFT
				tokenList.add(new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.L_BRACKET));
			} else if (fileContents.get(i) == ']') { //BRACKET RIGHT
				tokenList.add(new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.R_BRACKET));
			} else if (fileContents.get(i) == '{') { //BRACE LEFT
				tokenList.add(new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.L_BRACE));
			} else if (fileContents.get(i) == '}') { //BRACE RIGHT
				tokenList.add(new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.R_BRACE));
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
				if ((fileContents.size() > (i + 1)) && fileContents.get(i + 1) == '=') {
					i++;
					tok = new Token("==", filename, lineNumber, TokenType.REL_OP);
				} else tok = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.ASSIGN);
				tokenList.add(tok);
			} else if (fileContents.get(i) == '!') {
				if ((fileContents.size() > (i + 1)) && fileContents.get(i + 1) == '=') {
					i++;
					Token tok = new Token("!=", filename, lineNumber, TokenType.REL_OP);
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
				if ((fileContents.size() > (i + 1)) && fileContents.get(i + 1) == '=') {
					i++;
					tok = new Token("<=", filename, lineNumber, TokenType.REL_OP);
				} else tok = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.REL_OP);
				tokenList.add(tok);
			} else if (fileContents.get(i) == '>') {
				Token tok;
				if ((fileContents.size() > (i + 1)) && fileContents.get(i + 1) == '=') {
					i++;
					tok = new Token(">=", filename, lineNumber, TokenType.REL_OP);
				} else tok = new Token(fileContents.get(i).toString(), filename, lineNumber, TokenType.REL_OP);
				tokenList.add(tok);
			} else if (fileContents.get(i) == '.') {    // Code for dealing with decimals first
				Token tok;
				String token = ".";
				if ((fileContents.size() > (i + 1)) && (int) fileContents.get(i + 1) > 47 && (int) fileContents.get(i + 1) < 57) { // Checking if it's just a point
					while ((int) fileContents.get(i + 1) > 47 && (int) fileContents.get(i + 1) < 57) { // Loops over all numbers
						token += fileContents.get(i + 1);
						i++;
					}
					tok = new Token(token, filename, lineNumber, TokenType.NUMBER);
					tokenList.add(tok);
				}
			} else if ((int) fileContents.get(i) > 47 && (int) fileContents.get(i) < 57) {    // Code for integers
				Token tok;
				String token = fileContents.get(i).toString();
				while ((fileContents.size() > (i + 1)) && (int) fileContents.get(i + 1) > 47 && (int) fileContents.get(i + 1) < 57) {    // Loop over whole integer
					token += fileContents.get(i + 1);
					i++;
				}
				if (fileContents.get(i + 1) == '.') {    // If followed by a decimal, continue code for decimals
					token += ".";
					i++;
					while ((fileContents.size() > (i + 1)) && (int) fileContents.get(i + 1) > 47 && (int) fileContents.get(i + 1) < 57) {
						token += fileContents.get(i + 1);
						i++;
					}
				}
				tok = new Token(token, filename, lineNumber, TokenType.NUMBER);
				tokenList.add(tok);
			} else if (Character.isLetter(fileContents.get(i))) {// Handling Id_Keyword
				String token = "";
				while ((fileContents.size() > (i)) && (Character.isLetter(fileContents.get(i)) || Character.isDigit(fileContents.get(i)))) {
					token += fileContents.get(i).toString();
					i++;
				}
				Token tok = new Token(token, filename, lineNumber, TokenType.ID_KEYWORD);
				tokenList.add(tok);

			} else if (fileContents.get(i).equals('"')) {// Handling String
				String token;
				token = fileContents.get(i).toString();
				i++;
				while (Character.isLetter(fileContents.get(i)) || Character.isDigit(fileContents.get(i)) || fileContents.get(i) == ' ') {
					token += fileContents.get(i).toString();
					i++;
				}
				if (fileContents.get(i).equals('\"')) {
					token += fileContents.get(i).toString();
					i++;
					Token tok = new Token(token, filename, lineNumber, TokenType.STRING);
					tokenList.add(tok);
				} else {
					// throw exception
				}

			}
		}
		return tokenList;
    }
}