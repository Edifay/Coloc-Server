package personnal.app.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import personnal.app.logic.utils.ShoppingItem;
import personnal.app.logic.utils.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ShoppingListManager {

    public static String FILE_NAME = "shopping_list.json";

    private static final ArrayList<ShoppingItem> shoppingItems;
    private static final File file;

    static {
        file = new File(FILE_NAME);

        // Check if the file exists or not and create it if not.
        checkFile();

        // Load transactions
        try {
            shoppingItems = new ObjectMapper().readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkFile() {
        if (!file.exists())
            save();
    }

    public static void save() {
        try {
            new ObjectMapper().writeValue(file, Objects.requireNonNullElseGet(shoppingItems,
                    () -> new ArrayList<ShoppingItem>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addItem(ShoppingItem item) {
        if (!shoppingItems.contains(item)) {
            shoppingItems.add(item);
            if (item.getId() == null)
                item.generateId();
        } else
            shoppingItems.set(shoppingItems.indexOf(item), item);
        save();
    }

    public static void removeItem(ShoppingItem item) {
        shoppingItems.remove(item);
        save();
    }

    public static ArrayList<ShoppingItem> getShoppingList(int start, int end) {
        if (start > end) {
            int temp = end;
            end = start;
            start = temp;
        }
        if (start < 0)
            start = 0;
        if (end > shoppingItems.size())
            end = shoppingItems.size();

        return new ArrayList<>(shoppingItems.subList(start, end));

    }

}
