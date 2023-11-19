package examresults;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CsvFileWriter {

    public static void writeToCsvFile(String directoryPath, String fileName, String content) throws IOException {
        Path fullPath = resolveFilePath(directoryPath, fileName);
        Files.write(fullPath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static Path resolveFilePath(String directoryPath, String fileName) throws IOException {
        Path path = Paths.get(directoryPath).resolve(fileName);

        if (Files.notExists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        if (!Files.isWritable(path.getParent())) {
            throw new IOException("Cannot write to directory: " + path.getParent().toString());
        }

        return path;
    }
}
