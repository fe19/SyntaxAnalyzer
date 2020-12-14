import java.io.FileReader;
import java.io.FileWriter;

/**
 * Implements the program structure, statements, and expressions of the Jack grammar.
 * Takes input from JackTokenizer.
 * Described in unit 4.5
 */
public class CompilationEngine {

    /**
     * Creates a new compilation engine with the given input and output.
     *
     * @param inputFile
     * @param outputFile
     */
    public CompilationEngine(FileReader inputFile, FileWriter outputFile) {
    }

    /**
     * Compiles a complete class.
     */
    public void compileClass() {

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
    public void CompileStatements() {

    }

    /**
     * Compiles a let statement.
     */
    public void CompileLet() {

    }

    /**
     * Compiles an if statement possibly followed by an else clause.
     */
    public void CompileIf() {

    }

    /**
     * Compiles a while statement.
     */
    public void CompileWhile() {

    }

    /**
     * Compiles a do statement.
     */
    public void CompileDo() {

    }

    /**
     * Compiles a return statement.
     */
    public void CompileReturn() {

    }

    /**
     * Compiles an expression.
     */
    public void CompileExpression() {

    }

    /**
     * Compiles a (possibly empty) comma-separated list of expressions.
     */
    public void CompileExpressionList() {

    }

    /**
     * Compiles a term.
     * LL(2): If the current token is an identifier, the routine must distinguish between a variable, an array entry, or a subroutine call.
     * A single look-ahead token, which may be one of [, ( or . suffices to distinguish it.
     * Any other token is not part of this term and should not be advanced over.
     */
    public void CompileTerm() {

    }
}
