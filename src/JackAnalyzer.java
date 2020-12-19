import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Input:   name.jack file(s) that contain(s) Jack classes
 * Output:  nameT.xml file(s) that contain(s) all tokens from .jack files (intermediate result)
 *          name.xml file(s) that contain(s) the parsing tree.
 */
public class JackAnalyzer {

    public static void main(String[] args) throws IOException {
        String pathName = "test/Example/";
        File file = new File(pathName);

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                if (f.getName().endsWith(".jack")) {
                    String fileName = pathName + f.getName().substring(0, f.getName().length() - 5);

                    FileReader fileReader = new FileReader(f);
                    FileWriter outputTokenizer = new FileWriter(fileName + "T.xml");
                    JackTokenizer jackTokenizer = new JackTokenizer(fileReader, outputTokenizer);
                    jackTokenizer.advance();
                    System.out.println("Tokenizer completed class '" + fileName + "'");

                    FileReader tokenizedFile = new FileReader(fileName + "T.xml");
                    FileWriter output = new FileWriter(fileName + ".xml");
                    CompilationEngine compilationEngine = new CompilationEngine(tokenizedFile, output);
                    compilationEngine.compileClass();
                    System.out.println("Parser completed class '" + fileName + "'");
                }

            }
        } else {
            FileReader inputFile = new FileReader(file + ".jack");
            FileWriter outputTokenizer = new FileWriter(file + "T.xml");
            JackTokenizer jackTokenizer = new JackTokenizer(inputFile, outputTokenizer);
            jackTokenizer.advance();
            System.out.println("Tokenizer completed class '" + file + "'");

            FileReader tokenizedFile = new FileReader(file + "T.xml");
            FileWriter output = new FileWriter(file + ".xml");
            CompilationEngine compilationEngine = new CompilationEngine(tokenizedFile, output);
            compilationEngine.compileClass();
            System.out.println("Parser completed class '" + file + "'");
        }

        System.out.println("Jack Syntax Analyzer completed");
    }
}
