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

    /**
     * Creates a new compilation engine with the given input and output.
     *
     * @param inputFile
     * @param outputFile
     */
    public CompilationEngine(FileReader inputFile, FileWriter outputFile) {
        this.outputFile = outputFile;
        inputScanner = new Scanner(inputFile);
    }

    /**
     * Compiles a complete class.
     */
    public void compileClass() throws IOException {

        outputFile.write("<class>\n");

        if (inputScanner.hasNextLine()){

            String firstToken = inputScanner.nextLine();
            if (!firstToken.equals("<tokens>")){
                System.out.println("Parser error. Tokenized file does not begin with tag '<tokens>'");
            }
            String startToken = inputScanner.nextLine();

            if (startToken.equals("<keyword> while </keyword>")){
                System.out.println("   compileWhile()");
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
    public void compileStatements() {

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
    public void compileWhile() {
        eat("while");
        eat("(");
        compileExpression();
        eat(")");
        eat("{");
        compileStatements();
        eat("}");
    }

    /**
     * Eats the current token and moves on to the next token.
     */
    private void eat(String token) {
        String line = inputScanner.nextLine();

        if (!line.contains(token)){
            System.out.println("Parser error. Invalid token '" + token + "'");
        }
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
     * Compiles an expression.
     */
    public void compileExpression() {

    }

    /**
     * Compiles a (possibly empty) comma-separated list of expressions.
     */
    public void compileExpressionList() {

    }

    /**
     * Compiles a term.
     * LL(2): If the current token is an identifier, the routine must distinguish between a variable, an array entry, or a subroutine call.
     * A single look-ahead token, which may be one of [, ( or . suffices to distinguish it.
     * Any other token is not part of this term and should not be advanced over.
     */
    public void compileTerm() {

    }
}
