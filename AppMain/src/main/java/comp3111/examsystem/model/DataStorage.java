package comp3111.examsystem.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataStorage {
    private final DataSerializer dataSerializer = new DataSerializer();
    private final String path;

    public DataStorage(String path) {
        this.path = path;
    }

    public DataCollection load() {
        try {
            Path filePath = Paths.get(path);
            byte[] fileBytes = Files.readAllBytes(filePath);
            Charset utf8 = StandardCharsets.UTF_8;
            String string = new String(fileBytes, utf8);
            return dataSerializer.deserialize(string);
        } catch (IOException e) {
            return null;
        }
    }

    public void store(DataCollection dataCollection) {
        try (PrintWriter writer = new PrintWriter(path)) {
            String serialized = dataSerializer.serialize(dataCollection);
            writer.print(serialized);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
