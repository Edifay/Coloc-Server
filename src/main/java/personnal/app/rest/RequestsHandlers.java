package personnal.app.rest;

import org.springframework.web.bind.annotation.*;
import personnal.app.logic.ShoppingListManager;
import personnal.app.logic.TransactionsManager;
import personnal.app.logic.utils.Person;
import personnal.app.logic.utils.ShoppingItem;
import personnal.app.logic.utils.Transaction;

import java.util.ArrayList;

import static personnal.app.Main.password;

@CrossOrigin(origins = "*")
@RestController
public class RequestsHandlers {

    @GetMapping("/get-transactions")
    public static ArrayList<Transaction> getTransactions(@RequestParam(value = "start", defaultValue = "0") int start,
                                                         @RequestParam(value = "end", defaultValue =
                                                                 Integer.MAX_VALUE + "") int end,
                                                         @RequestParam(value = "code") String code) throws Exception {
        if (!code.equals(password))
            throw new Exception("Wrong code !");
        return TransactionsManager.getTransactions(start, end);
    }

    @PostMapping("/create-transaction")
    public static boolean createTransaction(@RequestBody Transaction transaction,
                                            @RequestParam(value = "code") String code) throws Exception {
        if (!code.equals(password))
            throw new Exception("Wrong code !");
        TransactionsManager.addTransaction(transaction);
        return true;
    }

    @GetMapping("/delete-transaction")
    public static boolean deleteTransaction(@RequestParam(value = "transaction_id") String id, @RequestParam(value =
            "code") String code) throws Exception {
        if (!code.equals(password))
            throw new Exception("Wrong code !");
        TransactionsManager.removeTransaction(new Transaction(id));
        return true;
    }

    @GetMapping("/get-expenses")
    public static float getExpenses(@RequestParam(value = "person") Person person,
                                    @RequestParam(value = "code") String code) throws Exception {
        if (!code.equals(password))
            throw new Exception("Wrong code !");
        return TransactionsManager.getTotalExpensesOfPerson(person);
    }

    @GetMapping("/get-equilibrate-transaction")
    public static Transaction getTransaction(@RequestParam(value = "code") String code) throws Exception {
        if (!code.equals(password))
            throw new Exception("Wrong code !");
        return TransactionsManager.getEquilibrateTransaction();
    }

    @PostMapping("/edit-transaction")
    public static boolean editTransaction(@RequestParam(value = "transaction_id") String idOld,
                                          @RequestBody Transaction current,
                                          @RequestParam(value = "code") String code) throws Exception {
        if (!code.equals(password))
            throw new Exception("Wrong code !");
        if (TransactionsManager.contain(new Transaction(idOld)))
            TransactionsManager.replaceTransaction(new Transaction(idOld), current);
        else TransactionsManager.addTransaction(current);
        return true;
    }


    // --------------------- SHOPPING LIST ---------------------


    @GetMapping("/get-shopping-list")
    public static ArrayList<ShoppingItem> getShoppingList(@RequestParam(value = "code") String code) throws Exception {
        if (!code.equals(password))
            throw new Exception("Wrong code !");
        return ShoppingListManager.getShoppingList();
    }


    @PostMapping("/add-item")
    public static boolean addItem(@RequestBody ShoppingItem item,
                                            @RequestParam(value = "code") String code) throws Exception {
        if (!code.equals(password))
            throw new Exception("Wrong code !");
        ShoppingListManager.addItem(item);
        return true;
    }

    @GetMapping("/remove-item")
    public static boolean removeItem(@RequestParam(value = "item_id") String id, @RequestParam(value =
            "code") String code) throws Exception {
        if (!code.equals(password))
            throw new Exception("Wrong code !");
        ShoppingListManager.removeItem(new ShoppingItem(id));
        return true;
    }

}