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

        while (inputScanner.hasNextLine()) {

            currentToken = inputScanner.nextLine();

            if (currentToken.equals("<keyword> while </keyword>")) {
                compileWhile();
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
     * Compiles a sequence of statements.
     * Does not handle the enclosing {}
     */
    public void compileStatements() throws IOException {
        System.out.println("   compileStatements()");
        outputFile.write("<statements>\n");

        outputFile.write("</statements>\n");
    }

    /**
     * Compiles a let statement.
     */
    public void compileLet() {

    }

    /**
     * Compiles an if statement possibly followed by an else clause.
     */
    public void compileIf() {

    }

    /**
     * Compiles a while statement.
     */
    public void compileWhile() throws IOException {
        System.out.println("   compileWhile()");
        outputFile.write("<whileStatement>\n");
        eat("while");
        eat("(");
        compileExpression();
        eat(")");
        eat("{");
        compileStatements();
        eat("}");
        outputFile.write("</whileStatement>\n");
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
        System.out.println("   compileExpression()");

        outputFile.write("<expression>\n");
        compileTerm();
        outputFile.write("</expression>\n");
        // TODO Optional part of expression

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
        System.out.println("   compileTerm()");
        outputFile.write("<term>\n");
        if (currentToken.startsWith("<identifier>") || currentToken.startsWith("<integerConstant>")) {
            outputFile.write(currentToken + "\n");
        } else{
            System.out.println(ERROR_MESSAGE + "Invalid term '" + currentToken + "'");
        }
        outputFile.write("</term>\n");
    }

    /**
     * Eats the current token and moves on to the next token.
     */
    private void eat(String token) {
        if (!currentToken.contains(token)) {
            System.out.println(ERROR_MESSAGE + "Invalid token '" + token + "'");
        }
        currentToken = inputScanner.nextLine();
    }
}
