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
        String fileOrDirectoryName = "Square";
        File file = new File(pathName + fileOrDirectoryName);

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File f : files) {
                if (f.getName().contains(".jack")){
                    FileReader fileReader = new FileReader(f);
                    String fileName = f.getName().substring(0, f.getName().length() - 5);
                    FileWriter outputTokenizer = new FileWriter(pathName + fileOrDirectoryName + "/" + fileName + "T.xml");
                    JackTokenizer jackTokenizer = new JackTokenizer(fileReader, outputTokenizer);
                    jackTokenizer.advance();
                }

            }
        } else {
            FileReader inputFile = new FileReader(file + ".jack");

            FileWriter outputTokenizer = new FileWriter(file + "T.xml");
            JackTokenizer jackTokenizer = new JackTokenizer(inputFile, outputTokenizer);
            jackTokenizer.advance();

            // FileWriter output = new FileWriter(file + ".xml");
            // CompilationEngine compilationEngine = new CompilationEngine(inputFile, output);
            // compilationEngine.compileClass();
        }

        System.out.println("JackTokenizer completed");
    }
}
