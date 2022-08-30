package personnal.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import personnal.app.logic.TransactionsManager;
import personnal.app.logic.utils.Person;
import personnal.app.logic.utils.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static personnal.app.logic.Utils.getStringOfPerson;

@SpringBootApplication
public class Main {

    public static String password;

    static {
        File file = new File("password.txt");
        if (file.exists()) {
            try {
                password = new Scanner(file).nextLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
            password = "default";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
