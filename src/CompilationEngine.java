import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Implements the program structure, statements, and expressions of the Jack grammar.
 * Takes input from JackTokenizer.
 * Described in unit 4.5. The Jack grammar is also presented in that unit.
 */
public class CompilationEngine {
    FileWriter outputFile;
    Scanner inputScanner;

    String currentToken;

    private static final String ERROR_MESSAGE = "Parser Error: ";

    /**
     * Creates a new compilation engine with the given input and output.
     *
     * @param inputFile
     * @param outputFile
     */
    public CompilationEngine(FileReader inputFile, FileWriter outputFile) {
        this.outputFile = outputFile;
        inputScanner = new Scanner(inputFile);
        currentToken = "";
    }

    /**
     * Compiles a complete class.
     */
    public void compileClass() throws IOException {

        outputFile.write("<class>\n");

        currentToken = inputScanner.nextLine();
        if (!currentToken.equals("<tokens>")) {
            System.out.println(ERROR_MESSAGE + "Tokenized file does not begin with tag '<tokens>'");
        }

        currentToken = inputScanner.nextLine();

        while (inputScanner.hasNextLine()) {

            if (currentToken.equals("<keyword> if </keyword>")) {
                compileIf();
            } else if (currentToken.equals("<keyword> while </keyword>")) {
                compileWhile();
            } else if (currentToken.equals("<keyword> let </keyword>")) {
                compileLet();
            }

        }

        outputFile.write("</class>\n");
        outputFile.close();
    }

    /**
     * Compiles a static or field variable declaration.
     */
    public void compileClassVarDec() {

    }

    /**
     * Compiles a complete method, function, or constructor.
     */
    public void compileSubroutineDec() {

    }

    /**
     * Compiles a (possible empty) parameter list.
     * Does not handle the enclosing ()
     */
    public void compileParameterList() {

    }

    /**
     * Compiles a subroutine's body.
     */
    public void compileSubroutineBody() {

    }

    /**
     * Compiles a variable declaration.
     */
    public void compileVarDec() {

    }

    /**
     * Compiles a sequence of statements. Grammar statements: (ifStatement | whileStatement | letStatement)*
     * Does not handle the enclosing {}
     */
    public void compileStatements() throws IOException {
        outputFile.write("<statements>\n");

        if (currentToken.equals("<keyword> if </keyword>")) {
            compileIf();
        } else if (currentToken.equals("<keyword> while </keyword>")) {
            compileWhile();
        } else if (currentToken.equals("<keyword> let </keyword>")) {
            compileLet();
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid statement '" + currentToken + "'");
        }

        outputFile.write("</statements>\n");
    }

    /**
     * Compiles an if statement. Grammar ifStatement: 'if' '(' expression ')' '{' statements '}'
     * Possibly followed by an else clause.
     */
    public void compileIf() throws IOException {
        outputFile.write("<ifStatement>\n");

        eat("<keyword> if </keyword>");
        eat("<symbol> ( </symbol>");
        compileExpression();
        eat("<symbol> ) </symbol>");
        eat("<symbol> { </symbol>");
        compileStatements();
        eat("<symbol> } </symbol>");

        outputFile.write("</ifStatement>\n");

    }

    /**
     * Compiles a while statement. Grammar whileStatement: 'while' '(' expression ')' '{' statements'}'
     */
    public void compileWhile() throws IOException {
        outputFile.write("<whileStatement>\n");

        eat("<keyword> while </keyword>");
        eat("<symbol> ( </symbol>");
        compileExpression();
        eat("<symbol> ) </symbol>");
        eat("<symbol> { </symbol>");
        compileStatements();
        eat("<symbol> } </symbol>");

        outputFile.write("</whileStatement>\n");
    }

    /**
     * Compiles a let statement. Grammar letStatement: 'let' varName '=' expression ';'
     */
    public void compileLet() throws IOException {
        outputFile.write("<letStatement>\n");

        eat("<keyword> let </keyword>");
        if (currentToken.startsWith("<identifier>")) {
            eat(currentToken);
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid term '" + currentToken + "' must have <identifier> tag");
            eat(currentToken);
        }
        eat("<symbol> = </symbol>");
        compileExpression();
        eat("<symbol> ; </symbol>");

        outputFile.write("</letStatement>\n");
    }

    /**
     * Compiles a do statement.
     */
    public void compileDo() {

    }

    /**
     * Compiles a return statement.
     */
    public void compileReturn() {

    }

    /**
     * Compiles an expression. Grammar: expression: term (op term)?
     */
    public void compileExpression() throws IOException {
        outputFile.write("<expression>\n");

        compileTerm();
        // TODO Optional part of expression

        outputFile.write("</expression>\n");
    }

    /**
     * Compiles a (possibly empty) comma-separated list of expressions.
     */
    public void compileExpressionList() {

    }

    /**
     * Compiles a term. Grammar: term: varName | constant
     * LL(2): If the current token is an identifier, the routine must distinguish between a variable, an array entry, or a subroutine call.
     * A single look-ahead token, which may be one of [, ( or . suffices to distinguish it.
     * Any other token is not part of this term and should not be advanced over.
     */
    public void compileTerm() throws IOException {
        outputFile.write("<term>\n");

        if (currentToken.startsWith("<identifier>") || currentToken.startsWith("<integerConstant>")) {
            eat(currentToken);
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid term '" + currentToken + "' must have <identifier> or <integerConstant> tag");
            eat(currentToken);
        }

        outputFile.write("</term>\n");
    }

    /**
     * Eats the current token and moves on to the next token.
     */
    private void eat(String token) throws IOException {
        if (currentToken.equals(token)) {
            outputFile.write(token + "\n");
            currentToken = inputScanner.nextLine();
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid token '" + currentToken + "' instead of '" + token + "'");
            currentToken = inputScanner.nextLine();
        }
    }
}
