import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Input:   .jack file(s)
 * Output:  .xml file(s)
 */
public class JackAnalyzer {

    public static void main(String[] args) throws IOException {
        String pathName = "test/";
        String fileName = "Main";
        File file = new File(pathName + fileName);

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                FileReader fileReader = new FileReader(f);
                FileWriter outputTokenizer = new FileWriter(f + "T.xml");
                JackTokenizer jackTokenizer = new JackTokenizer(fileReader, outputTokenizer);
                jackTokenizer.advance();
            }
        } else {
            FileReader inputFile = new FileReader(file + ".jack");

            FileWriter outputTokenizer = new FileWriter(file + "T.xml");
            JackTokenizer jackTokenizer = new JackTokenizer(inputFile, outputTokenizer);
            jackTokenizer.advance();

            FileWriter output = new FileWriter(file + ".xml");
            CompilationEngine compilationEngine = new CompilationEngine(inputFile, output);
            compilationEngine.compileClass();
        }

        System.out.println("JackTokenizer completed");
    }
}
