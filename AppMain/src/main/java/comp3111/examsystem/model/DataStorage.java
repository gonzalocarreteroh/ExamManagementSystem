package comp3111.examsystem.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataStorage {
    private final DataSerializer dataSerializer = new DataSerializer();
    private final String path;

    public DataStorage(String path) {
        this.path = path;
    }

    public DataCollection load() {
        try {
            String string = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            return dataSerializer.deserialize(string);
        } catch (IOException e) {
            return null;
        }
    }

    public void store(DataCollection dataCollection) {
        try (PrintWriter writer = new PrintWriter(path)) {
            writer.print(dataSerializer.serialize(dataCollection));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
