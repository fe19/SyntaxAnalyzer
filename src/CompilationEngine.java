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
     * Compiles a complete class. Grammar class: 'class' className '{' classVarDec* subroutingeDec* '}'
     */
    public void compileClass() throws IOException {

        currentToken = inputScanner.nextLine();
        if (!currentToken.equals("<tokens>")) {
            System.out.println(ERROR_MESSAGE + "Tokenized file does not begin with tag '<tokens>'");
        }
        currentToken = inputScanner.nextLine();

        outputFile.write("<class>\n");

        eat("<keyword> class </keyword>");
        if (currentToken.startsWith("<identifier>")) {
            eat(currentToken);
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid className tag '" + currentToken + "'");
            eat(currentToken);
        }
        eat("<symbol> { </symbol>");

        // (classVarDec)*
        while (currentToken.equals("<keyword> static </keyword>") || currentToken.equals("<keyword> field </keyword>")) {
            compileClassVarDec();
        }

        // (subroutingeDec)*
        while (currentToken.equals("<keyword> constructor </keyword>") ||
                currentToken.equals("<keyword> function </keyword>") || currentToken.equals("<keyword> method </keyword>")) {
            compileSubroutineDec();
        }

        eat("<symbol> } </symbol>");

        outputFile.write("</class>\n");
        outputFile.close();
    }

    /**
     * Compiles class variable declaration. Grammar classVarDec: ('static' | 'field') type varName (',', varName)* ';'
     */
    public void compileClassVarDec() throws IOException {
        outputFile.write("<classVarDec>\n");

        eat(currentToken);  // 'static' | 'field'
        eatType(currentToken);
        if (currentToken.startsWith("<identifier>")) {
            eat(currentToken);
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid variable name '" + currentToken + "'");
            eat(currentToken);
        }

        while (currentToken.equals("<symbol> , </symbol>")) {
            eat(currentToken);  // ','
            if (currentToken.startsWith("<identifier>")) {
                eat(currentToken);
            } else {
                System.out.println(ERROR_MESSAGE + "Invalid variable name '" + currentToken + "'");
                eat(currentToken);
            }
        }

        eat("<symbol> ; </symbol>");

        outputFile.write("</classVarDec>\n");
    }

    /**
     * Compiles a subroutine declaration. Grammar subroutineDec: (method | function | constructor) (void | type) subroutineName '(' parameterList ')' subroutineBody
     */
    public void compileSubroutineDec() throws IOException {
        outputFile.write("<subroutineDec>\n");

        // (method | function | constructor)
        eat(currentToken);

        // (void | type)
        if (currentToken.equals("<keyword> boolean </keyword>") || currentToken.equals("<keyword> int </keyword>") ||
                currentToken.equals("<keyword> char </keyword>") || currentToken.equals("<keyword> void </keyword>") ||
                currentToken.startsWith("<identifier>")) {
            eat(currentToken);
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid subroutine return type '" + currentToken + "'");
            eat(currentToken);
        }

        // subroutineName
        if (currentToken.startsWith("<identifier>")) {
            eat(currentToken);
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid subroutine return type '" + currentToken + "'");
            eat(currentToken);
        }

        eat("<symbol> ( </symbol>");
        compileParameterList();
        eat("<symbol> ) </symbol>");
        compileSubroutineBody();

        outputFile.write("</subroutineDec>\n");
    }

    /**
     * Compiles a parameter list. Grammar parameterList: ( (type varName) (',' type varName)* )?
     * Possibly empty, does not handle the enclosing ()
     */
    public void compileParameterList() throws IOException {
        outputFile.write("<parameterList>\n");

        if (currentToken.equals("<keyword> boolean </keyword>") || currentToken.equals("<keyword> int </keyword>") ||
                currentToken.equals("<keyword> char </keyword>") || currentToken.startsWith("<identifier>")) {
            // type
            eat(currentToken);
            // varName
            eatIdentifier(currentToken);
            // (',' type varName)*
            while (currentToken.equals("<symbol> , </symbol>")) {
                // ','
                eat(currentToken);
                // type
                if (currentToken.equals("<keyword> boolean </keyword>") || currentToken.equals("<keyword> int </keyword>") ||
                        currentToken.equals("<keyword> char </keyword>") || currentToken.startsWith("<identifier>")) {
                    eat(currentToken);
                } else {
                    System.out.println(ERROR_MESSAGE + "Invalid parameter list type '" + currentToken + "'");
                    eat(currentToken);
                }
                // varName
                eatIdentifier(currentToken);
            }
        }

        outputFile.write("</parameterList>\n");
    }

    /**
     * Compiles a subroutine's body. Grammar subroutineBody: '{' varDec* statements '}'
     */
    public void compileSubroutineBody() throws IOException {
        outputFile.write("<subroutineBody>\n");

        // '{'
        eat(currentToken);
        // (varDec)*
        while (currentToken.equals("<keyword> var </keyword>")) {
            compileVarDec();
        }
        compileStatements();
        // '}'
        eat(currentToken);

        outputFile.write("</subroutineBody>\n");
    }

    /**
     * Compiles a variable declaration. Grammar varDec: 'var' type varName (',' varName)* ';'
     */
    public void compileVarDec() throws IOException {
        outputFile.write("<varDec>\n");

        eat("<keyword> var </keyword>");
        eatType(currentToken);
        eatIdentifier(currentToken);
        while (currentToken.equals("<symbol> , </symbol>")) {
            eat("<symbol> , </symbol>");
            eatIdentifier(currentToken);
        }
        eat("<symbol> ; </symbol>");

        outputFile.write("</varDec>\n");
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
        eatIdentifier(currentToken);
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

    /**
     * Eats the current type and moves on to the next token.
     */
    private void eatIdentifier(String token) throws IOException {
        if (token.startsWith("<identifier>")) {
            eat(token);
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid identifier '" + token + "'");
            eat(token);
        }
    }

    /**
     * Eats the current type and moves on to the next token.
     */
    private void eatType(String token) throws IOException {
        if (token.equals("<keyword> boolean </keyword>") || token.equals("<keyword> int </keyword>") ||
                token.equals("<keyword> char </keyword>") || token.startsWith("<identifier>")) {
            eat(token);
        } else {
            System.out.println(ERROR_MESSAGE + "Invalid type '" + token + "'");
            eat(token);
        }
    }

}
