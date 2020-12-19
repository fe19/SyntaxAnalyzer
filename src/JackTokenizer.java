import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Implements the lexical elements of the Jack grammar.
 * - Advancing the input one token at a time.
 * - Getting the value and type of the current token
 * - Ignoring white space and comments
 * Output <, >, ", and & as &lt, &gt, &quot, and &amp since they have special meaning in XML
 */
public class JackTokenizer {
    FileWriter outputFile;
    Scanner scanner;

    /**
     * @param inputFile  .jack file with source code
     * @param outputFile .xml file with all tokens
     */
    public JackTokenizer(FileReader inputFile, FileWriter outputFile) {
        this.outputFile = outputFile;
        scanner = new Scanner(inputFile);
    }

    /**
     * Groups characters into tokens and finds type of token.
     * Gets the next token from the input, and makes it the current token.
     * Should be called only if hasMoreTokens() is true.
     */
    public void advance() throws IOException {

        outputFile.write("<tokens>\n");

        String currentToken = "";
        String currentTokenWithSpaces = "";
        boolean isMultiLineComment = false;

        // Loop through file
        while (scanner.hasNextLine()) {

            String line = scanner.nextLine().trim();

            if (line.startsWith("/**") && !line.endsWith("*/")){
                isMultiLineComment = true;
            }
            if (!isMultiLineComment){
                currentToken = "";
                currentTokenWithSpaces = "";
            }
            if (line.startsWith("*/")){
                isMultiLineComment = false;
            }

            for (int i = 0; i < line.length(); i++) {
                char currentChar = line.charAt(i);
                char nextChar = line.charAt(Math.min(i + 1, line.length() - 1));
                currentToken += currentChar;
                currentTokenWithSpaces += currentChar;
                currentToken = currentToken.trim();



                if (currentToken.length() > 0 && !isComment(currentToken)) {
                    if (isStringVal(currentToken)) {
                        if (currentToken.length() > 1 && currentChar == '"') {

                            outputFile.write("<" + tokenType(currentToken) + ">");
                            outputFile.write(" " + tokenValue(currentTokenWithSpaces.trim()).substring(1, currentTokenWithSpaces.trim().length() - 1) + " ");
                            outputFile.write("</" + tokenType(currentToken) + ">");
                            outputFile.write("\n");

                            currentToken = "";
                            currentTokenWithSpaces = "";
                        }

                    } else if (isTermination(currentChar, nextChar)) {

                        outputFile.write("<" + tokenType(currentToken) + ">");
                        outputFile.write(" " + tokenValue(currentToken) + " ");
                        outputFile.write("</" + tokenType(currentToken) + ">");
                        outputFile.write("\n");

                        currentToken = "";
                        currentTokenWithSpaces = "";
                    }
                }
            }
        }

        outputFile.write("</tokens>\n");
        outputFile.close();
    }

    /**
     * @returns true if the current token is a comment
     */
    private boolean isComment(String currentToken) {
        return currentToken.startsWith("//") || currentToken.startsWith("/*");
    }

    /**
     * @returns true if the current token is terminated.
     */
    private boolean isTermination(char currentChar, char nextChar) {
        if (currentChar == '/') {
            return nextChar == ' ';
        } else {
            return isSymbol(currentChar) || nextChar == ' ' || isSymbol(nextChar);
        }
    }

    /**
     * @returns the type of the current token.
     */
    private String tokenType(String currentToken) {
        if (isSymbol(currentToken.charAt(0))) {
            return "symbol";
        } else if (isKeyword(currentToken)) {
            return "keyword";
        } else if (isIntVal(currentToken)) {
            return "integerConstant";
        } else if (isStringVal(currentToken)) {
            return "stringConstant";
        } else if (isIdentifier(currentToken)) {
            return "identifier";
        } else if (isComment(currentToken)) {
            return "comment";
        } else {
            System.out.println("Token type unknown: " + currentToken);
            return "";
        }
    }

    /**
     * @returns the value of the current token. Normally it is the given token. But there are some special characters in XML.
     */
    private String tokenValue(String currentToken) {
        char c = currentToken.charAt(0);
        if (isSymbol(c)) {
            switch (c) {
                case '<':
                    return "&lt;";
                case '>':
                    return "&gt;";
                case '"':
                    return "&quot;";
                case '&':
                    return "&amp;";
            }
            return currentToken;
        } else {
            return currentToken;
        }
    }

    /**
     * @returns true if the character is a valid symbol.
     */
    private boolean isSymbol(char c) {
        switch (c) {
            case '{':
            case '}':
            case '(':
            case ')':
            case '[':
            case ']':
            case '.':
            case ',':
            case ';':
            case '+':
            case '-':
            case '*':
            case '/':
            case '&':
            case '|':
            case '<':
            case '>':
            case '=':
            case '~':
                return true;
            default:
                return false;
        }
    }

    /**
     * @returns true if the current token is a valid keyword.
     */
    private boolean isKeyword(String currentToken) {
        switch (currentToken) {
            case "class":
            case "method":
            case "function":
            case "constructor":
            case "int":
            case "boolean":
            case "char":
            case "void":
            case "var":
            case "static":
            case "field":
            case "let":
            case "do":
            case "if":
            case "else":
            case "while":
            case "return":
            case "true":
            case "false":
            case "null":
            case "this":
                return true;
            default:
                return false;
        }
    }

    /**
     * @returns true if the current token is a valid identifier. Identifier must start with a letter or underscore.
     */
    private boolean isIdentifier(String currentToken) {
        return Character.isLetter(currentToken.charAt(0)) || currentToken.charAt(0) == '_';
    }

    /**
     * @returns true if the current token is a string.
     */
    private boolean isStringVal(String currentToken) {
        return currentToken.charAt(0) == '"';
    }

    /**
     * @return true if the current token is an integer
     * <p>
     * Should be called only if tokenType is INT_CONST.
     */
    private boolean isIntVal(String currentToken) {
        boolean isInt = true;
        if (currentToken.length() > 0) {
            for (char c : currentToken.toCharArray()) {
                if (!Character.isDigit(c)) {
                    isInt = false;
                }
            }
        } else {
            isInt = false;
        }
        return isInt;
    }
}
