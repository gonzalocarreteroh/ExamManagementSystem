package comp3111.examsystem.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles the persistence of application data to and from disk storage.
 * This class provides functionality to save and load {@link DataCollection} objects
 * using file-based storage with UTF-8 encoding. The data is serialized and deserialized
 * using a {@link DataSerializer} instance.
 */
public class DataStorage {
    /** The serializer used to convert between DataCollection objects and string representations */
    private final DataSerializer dataSerializer = new DataSerializer();

    /** The file system path where the data will be stored */
    private final String path;

    /**
     * Creates a new DataStorage instance that will read from and write to the specified path.
     *
     * @param path The file system path where the data should be stored or loaded from
     */
    public DataStorage(String path) {
        this.path = path;
    }

    /**
     * Loads and deserializes a DataCollection from disk storage.
     * Reads the file specified by the path, interprets it as a UTF-8 encoded string,
     * and deserializes it into a DataCollection object.
     *
     * @return The deserialized DataCollection object, or null if the file cannot be read
     *         or an IO error occurs
     */
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

    /**
     * Serializes and stores a DataCollection to disk storage.
     * Converts the DataCollection object to a string representation and writes it
     * to the file specified by the path.
     *
     * @param dataCollection The DataCollection object to be stored
     * @throws RuntimeException if an IO error occurs while writing the file
     */
    public void store(DataCollection dataCollection) {
        try (PrintWriter writer = new PrintWriter(path)) {
            String serialized = dataSerializer.serialize(dataCollection);
            writer.print(serialized);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}