package personnal.app.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import personnal.app.logic.utils.Person;
import personnal.app.logic.utils.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class TransactionsManager {

    public static final DecimalFormat df = new DecimalFormat("0.00");

    public static String FILE_NAME = "transactions.json";
    public static String FILE_NAME_SAVES = "transactions_dd_MM_yyyy.json";

    private static final ArrayList<Transaction> transactions;
    private static final File file;

    static {

        // ---- Read current transactions
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


        return Float.parseFloat(df.format(expenses));
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

        float equilibrateAmount = Float.parseFloat(df.format(Math.abs(firstExpenses - secondExpenses) / 2f));

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

    public static String[] getSavedTransactionsList() {

        File savesDirectory = new File("saves");

        DateFormat formatInput = new SimpleDateFormat(nameToSimplified(FILE_NAME_SAVES));
        DateFormat formatOuput = new SimpleDateFormat("dd/MM/yyyy");

        if (!savesDirectory.exists())
            savesDirectory.mkdirs();

        ArrayList<Date> dates = new ArrayList<>();

        for (File file : savesDirectory.listFiles()) {
            if (file.getName().startsWith("transactions") && file.getName().endsWith(".json")) {
                try {
                    dates.add(formatInput.parse(nameToSimplified(file.getName())));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        dates.sort(Comparator.reverseOrder());

        return dates.stream().map(formatOuput::format).toArray(String[]::new);
    }

    private static String nameToSimplified(String name) {
        return name.substring(13, 23);
    }

    public static ArrayList<Transaction> getTransactionsSaved(String date) throws Exception {
        File file = dateToFile(date);

        return new ObjectMapper().readValue(file, new TypeReference<>() {
        });
    }


    private static File dateToFile(String date) throws ParseException, FileNotFoundException {
        DateFormat formatInput = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatOuput = new SimpleDateFormat("dd_MM_yyyy");


        String name = "saves/transactions_" + formatOuput.format(formatInput.parse(date)) + ".json";
        File file = new File(name);

        if (file.exists())
            return file;

        throw new FileNotFoundException("The save doesn't exist !");
    }

    public static void transferToSave() throws Exception {
        Date date = Date.from(Instant.now());
        DateFormat format = new SimpleDateFormat("dd_MM_yyyy");
        File ouput_file = new File("saves/transactions_" + format.format(date) + ".json");

        if (ouput_file.exists())
            throw new Exception("The save already exists for this day!");

        new ObjectMapper().writeValue(ouput_file, transactions);
        transactions.clear();

        save();
    }

    public static float getTotalExpensesOfPersonDate(final Person person, final String date) throws Exception {
        ArrayList<Transaction> transactions = getTransactionsSaved(date);

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


        return Float.parseFloat(df.format(expenses));
    }
}