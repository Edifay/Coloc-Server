package personnal.app.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import personnal.app.logic.utils.Priority;
import personnal.app.logic.utils.TodoAction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TodoListHandler {


    public static String FILE_NAME = "todo_list.json";

    private static final ArrayList<TodoAction> todoActions;
    private static final File file;

    static {
        file = new File(FILE_NAME);

        // Check if the file exists or not and create it if not.
        checkFile();

        // Load transactions
        try {
            todoActions = new ObjectMapper().readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkFile() {
        if (!file.exists()) save();
    }

    public static void save() {
        try {
            new ObjectMapper().writeValue(file, Objects.requireNonNullElseGet(todoActions,
                    () -> new ArrayList<TodoAction>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTodoAction(TodoAction action) {
        if (!todoActions.contains(action)) {
            todoActions.add(action);
            if (action.getId() == null) action.generateId();
        } else todoActions.set(todoActions.indexOf(action), action);
        save();
    }

    public static void removeTodoAction(TodoAction action) {
        todoActions.remove(action);
        save();
    }

    public static ArrayList<TodoAction> getTodoList() {
        ArrayList<TodoAction> sortedActions = new ArrayList<>();
        for (Priority value : Priority.values()) {
            sortedActions.add(new TodoAction("separator", "", value));
            for (TodoAction action : todoActions)
                if (action.getPriority().equals(value)) sortedActions.add(action);
        }
        return sortedActions;
    }

}
