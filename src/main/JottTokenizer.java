package main; /**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author Ben Froment
 * @author Seth Walker
 * @author Anthony Salvati
 * @author Jason Raab
 * @author Ian Paap-Gray
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
		} catch (InvalidTokenException ite) {
			return null;
		}

		return tokenList;
	}


	private static ArrayList<Token> tokenizeHelper (ArrayList<Character> fileContents, String filename) throws InvalidTokenException {
		ArrayList<Token> tokenList = new ArrayList<>();

		int lineNumber = 1;
		for (int i = 0; i < fileContents.size(); i++) {

			// skip over whitespaces and handle single-line comments (multi-line arent in Jott)
			while ((fileContents.get(i) == ' ' || fileContents.get(i) == '#' || fileContents.get(i) == '\n')) {
				if (fileContents.get(i) == '#') { //handle single-line comments
					while (i != fileContents.size() && !(fileContents.get(i) == '\n')) {
						i += 1;
					}
				} else {
					if (fileContents.get(i) == '\n') { //handle newline (out of comment)
						lineNumber += 1;
					}
					i += 1;
				}
				if (i == fileContents.size()) {
					return tokenList;
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
					throw new InvalidTokenException(fileContents.get(i).toString(), filename, Integer.toString(lineNumber));
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
				if ((fileContents.size() > (i + 1)) && Character.isDigit(fileContents.get(i + 1))) { // Checking if it's just a point
					while (Character.isDigit(fileContents.get(i + 1))) { // Loops over all numbers
						token += fileContents.get(i + 1);
						i++;
					}
					tok = new Token(token, filename, lineNumber, TokenType.NUMBER);
					tokenList.add(tok);
				} else {
					throw new InvalidTokenException(fileContents.get(i).toString(), filename, Integer.toString(lineNumber));
				}
			} else if (Character.isDigit(fileContents.get(i))) {    // Code for integers
				Token tok;
				String token = fileContents.get(i).toString();
				while ((fileContents.size() > (i + 1)) && Character.isDigit(fileContents.get(i + 1))) {    // Loop over whole integer
					token += fileContents.get(i + 1);
					i++;
				}
				if (fileContents.get(i + 1) == '.') {    // If followed by a decimal, continue code for decimals
					token += ".";
					i++;
					while ((fileContents.size() > (i + 1)) && Character.isDigit(fileContents.get(i + 1))) {
						token += fileContents.get(i + 1);
						i++;
					}
				}
				tok = new Token(token, filename, lineNumber, TokenType.NUMBER);
				tokenList.add(tok);
			} else if (Character.isLetter(fileContents.get(i))) {// Handling Id_Keyword
				String token="";
				while ((fileContents.size() > (i)) && (Character.isLetter(fileContents.get(i)) || Character.isDigit(fileContents.get(i)))) {
					token += fileContents.get(i).toString();
					i++;
				}
				Token tok = new Token(token, filename, lineNumber, TokenType.ID_KEYWORD);
				tokenList.add(tok);
				i--;
			} else if (fileContents.get(i).equals('"')) {// Handling String
				String token = fileContents.get(i).toString();
				i++;
				while (Character.isLetter(fileContents.get(i)) || Character.isDigit(fileContents.get(i)) || fileContents.get(i) == ' ') {
					token += fileContents.get(i).toString();
					i++;
				}
				if (fileContents.get(i).equals('\"')) {
					token += fileContents.get(i).toString();
					Token tok = new Token(token, filename, lineNumber, TokenType.STRING);
					tokenList.add(tok);
				} else {
					throw new InvalidTokenException(fileContents.get(i).toString(), filename, Integer.toString(lineNumber));
				}

			}
		}
		return tokenList;
    }
}

