package personnal.app.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import personnal.app.logic.utils.Person;
import personnal.app.logic.utils.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TransactionsManager {

    public static String FILE_NAME = "transactions.json";

    private static final ArrayList<Transaction> transactions;
    private static final File file;

    static {
        file = new File(FILE_NAME);

        // Check if the file exists or not and create it if not.
        checkFile();

        // Load transactions
        try {
            transactions = new ObjectMapper().readValue(file, new TypeReference<>() {
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
            new ObjectMapper().writeValue(file, Objects.requireNonNullElseGet(transactions,
                    () -> new ArrayList<Transaction>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTransaction(final Transaction transaction) {
        transactions.add(0, transaction);
        if (transaction.getId() == null)
            transaction.generateId();
        if (transaction.getCreationDate() == null || transaction.getCreationDate() == 0)
            transaction.setCreationDate(System.currentTimeMillis());
        save();
    }

    public static void removeTransaction(final Transaction transaction) {
        transactions.remove(transaction);
        save();
    }

    public static void replaceTransaction(final Transaction old, final Transaction current) {
        transactions.set(transactions.indexOf(old), current);
    }

    public static int getTransactionsCount() {
        return transactions.size();
    }

    public static float getTotalExpensesOfPerson(final Person person) {
        save();
        if (person == Person.EMPTY) {
            System.err.println("This person cannot have any expenses !");
            return 0f;
        }

        float expenses = 0f;

        for (Transaction transaction : transactions)
            if (transaction.getActor() == person)
                expenses += transaction.getAmount();
            else if (transaction.getRecipient() == person)
                expenses += -transaction.getAmount();


        return expenses;
    }

    public static ArrayList<Transaction> getTransactions(int start, int end) {
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        if (start < 0)
            start = 0;
        if (end > TransactionsManager.getTransactionsCount())
            end = TransactionsManager.getTransactionsCount();

        return new ArrayList<>(transactions.subList(start, end));
    }

    public static Transaction getEquilibrateTransaction() {
        float firstExpenses = getTotalExpensesOfPerson(Person.ARNAUD);
        float secondExpenses = getTotalExpensesOfPerson(Person.DARIUS);

        float equilibrateAmount = Math.abs(firstExpenses - secondExpenses) / 2f;

        if (firstExpenses == secondExpenses)
            return new Transaction("equilibrate", equilibrateAmount, Person.ARNAUD, Person.DARIUS);
        else if (firstExpenses < secondExpenses)
            return new Transaction("equilibrate", -equilibrateAmount, Person.DARIUS, Person.ARNAUD);
        else
            return new Transaction("equilibrate", -equilibrateAmount, Person.ARNAUD, Person.DARIUS);

    }

    public static boolean contain(Transaction transaction) {
        return transactions.contains(transaction);
    }

}