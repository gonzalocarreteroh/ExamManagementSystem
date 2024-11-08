package comp3111.examsystem.controller;

import comp3111.examsystem.model.*;

public abstract class ControllerBase {
    private static final DataStorage storage = new DataStorage("storage.json");
    private static DataCollection collection = null;

    protected DataCollection loadData() {
        if (collection == null) {
            collection = storage.load();
        }
        return collection;
    }

    protected void storeData(DataCollection dataCollection) {
        collection = dataCollection;
        storage.store(dataCollection);
    }
}
