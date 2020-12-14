import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

    public JackTokenizer(FileReader inputFile, FileWriter outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * @return true if there are more tokens in the input.
     */
    public boolean hasMoreTokens() {
        return false;
    }

    /**
     * Gets the next token from the input, and makes it the current token.
     * Should be called only if hasMoreTokens() is true.
     */
    public void advance() throws IOException {
        outputFile.write("<tokens>\n");
        outputFile.write("</tokens>\n");
        outputFile.close();
    }

    /**
     * @return the type of the current token.
     */
    public String tokenType() {
        return "KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST";

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
