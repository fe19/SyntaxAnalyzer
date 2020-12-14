import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Implements the lexical elements of the Jack grammar.
 * - Advancing the input one token at a time.
 * - Getting the value and type of the current token
 * - Ignoring white space
 * Output <, >, ", and & as &lt, &gt, &quot, and &amp since they have special meaning in XML
 */
public class JackTokenizer {
    FileReader inputFile;
    FileWriter outputFile;
    Scanner scanner;

    String currentToken;

    private static final List<String> KEYWORDS = Arrays.asList("class", "method", "function", "constructor", "int", "boolean", "char",
            "void", "var", "static", "field", "let", "do", "if", "else", "while", "return", "true", "false", "null", "this");

    public JackTokenizer(FileReader inputFile, FileWriter outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        scanner = new Scanner(inputFile);
    }

    /**
     * @return true if there are more tokens in the input.
     */
    public boolean hasMoreTokens() {
        return scanner.hasNextLine();
    }

    /**
     * Gets the next token from the input, and makes it the current token.
     * Should be called only if hasMoreTokens() is true.
     */
    public void advance() throws IOException {
        outputFile.write("<tokens>\n");
        // Loop through file
        while (hasMoreTokens()) {
            String line = scanner.nextLine();
            // remove trailing comments
            if (line.contains("//") && line.length() != 2) {
                line = line.split("//")[0];   // take only part before comment
            }
            // ignore empty lines and begining comments
            if (!line.isEmpty() && !line.substring(0, 1).contains("/")) {
                Scanner scanLine = new Scanner(line);
                // Loop through line
                while (scanLine.hasNext()) {
                    String word = scanLine.next();

                    currentToken = word;
                    outputFile.write("<" + tokenType() + ">");
                    outputFile.write(word);
                    outputFile.write("</" + tokenType() + ">");
                    outputFile.write("\n");

                }
            }
        }
        outputFile.write("</tokens>\n");
        outputFile.close();
    }

    /**
     * @return the type of the current token.
     */
    public String tokenType() {
        if (KEYWORDS.contains(currentToken)) {
            return "keyword";
        } else if (false){
            return "symbol, identifier, int_const, string_const";
        } else {
            return "";
        }
    }

    /**
     * Should be only called if tokenType is KEYWORD
     *
     * @return the keyword which is the current token.
     */
    public String keyWord() {
        return "CLASS, METHOD, FUNCTION, CONSTRUCTOR, INT, BOOLEAN, CHAR, VOID, VAR, STATIC, FIELD," +
                "LET, DO, IF, ELSE, WHILE, RETURN, TRUE, FALSE, NULL THIS";
    }

    /**
     * Should be called only if tokenType is SYMBOL.
     *
     * @return the character which is the current token.
     */
    public char symbol() {
        return ' ';
    }

    /**
     * Should be called only if tokenType is IDENTIFIER.
     *
     * @return the identifier which is the current token.
     */
    public String identifier() {
        return "";
    }

    /**
     * Should be called only if tokenType is INT_CONST.
     *
     * @return the integer value of the current token.
     */
    public int intVal() {
        return 0;
    }

    /**
     * Should be called only if tokenType is STRING_CONST.
     *
     * @return the string value of the current token, without the two double quotes.
     */
    public String stringVal() {
        return "";
    }
}
