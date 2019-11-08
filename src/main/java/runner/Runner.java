package runner;

import classloader.OwnClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Class to load the classes entities.Cat and entities.Dog into collection Animals by your own class loader and run the methods "Play", "Voice".
 *
 * @author Diana_Aimbetova
 * @version 4.0
 */
public class Runner {
    private static Logger logger = LogManager.getLogger(Runner.class);
    private static  int swValue;

    public static void main(String[] args) {
        OwnClassLoader cl = new OwnClassLoader();
        Scanner scanner = new Scanner(System.in);
        System.out.println("|   MENU SELECTION     |");
        System.out.println("| Options:                 |");
        System.out.println("|        1. Add new class     |");
        System.out.println("|        2. Update existing class       |");
        System.out.println("|        3. Exit           |");
        run(cl, scanner);
    }

    private static void run(OwnClassLoader cl, Scanner scanner) {
        System.out.println("Please choose method or enter 3 to exit...");
        swValue = scanner.nextInt();
        try {
            switch (swValue) {
                case 1:
                    System.out.println("Please enter class path and name:");
                    String className = scanner.next();
                    Class myClass = cl.addClass(className);
                    Arrays.stream(myClass.getDeclaredFields()).forEach(field -> System.out.println(field.getName()));
                    run(cl, scanner);
                case 2:
                    System.out.println("Please enter class path and name:");
                    String updateClassName = scanner.next();
                    Class myUpdatedClass = cl.updateClass(updateClassName);
                    Arrays.stream(myUpdatedClass.getDeclaredFields()).forEach(field -> System.out.println(field.getName()));
                    run(cl, scanner);
                case 3:
                    break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
