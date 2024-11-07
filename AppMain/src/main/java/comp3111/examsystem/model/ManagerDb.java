package comp3111.examsystem.model;

import java.util.*;

public class ManagerDb {
    private int lastId;
    private final HashMap<Integer, Manager> managers;

    public ManagerDb(HashMap<Integer, Manager> managers) {
        lastId = managers.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        this.managers = managers;
    }

    public ManagerDb() {
        this(new HashMap<>());
    }

    public void add(String password, String username) {
        lastId += 1;
        managers.put(lastId, new Manager(lastId, password, username));
    }

    public void update(Manager manager) {
        if (managers.containsKey(manager.getId())) {
            managers.put(manager.getId(), manager);
        }
    }

    public Manager get(int id) {
        return managers.get(id);
    }

    public void remove(int id) {
        managers.remove(id);
    }

    public Manager[] list(String username) {
        return managers.values().stream().filter(s -> s.getUsername().startsWith(username)).toList().toArray(new Manager[0]);
    }

    public Manager[] list() {
        return list("");
    }

    public int size() {
        return managers.size();
    }
}
