import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MavenDependencyTreeReader {

    public static List<String> readDependencyTree(String projectDirectory) throws IOException, InterruptedException {
        List<String> outputLines = new ArrayList<>();
        ProcessBuilder processBuilder = new ProcessBuilder("mvn", "dependency:tree");
        processBuilder.directory(new java.io.File(projectDirectory));
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputLines.add(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    System.err.println(errorLine);
                }
            }
            throw new IOException("Maven dependency:tree command failed with exit code: " + exitCode);
        }

        return outputLines;
    }

    public static void main(String[] args) {
        String projectDir = "/path/to/your/maven/project"; // Replace with the actual path
        try {
            List<String> dependencyTree = readDependencyTree(projectDir);
            for (String line : dependencyTree) {
                System.out.println(line);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
