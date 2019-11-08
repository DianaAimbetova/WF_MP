package runner;

import classloader.OwnClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;


/**
 * Class to load the classes entities.Cat and entities.Dog into collection Animals by your own class loader and run the methods "Play", "Voice".
 *
 * @author Diana_Aimbetova
 * @version 4.0
 */
public class Runner {
    private static Logger logger = LogManager.getLogger(Runner.class);

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
        int swValue = scanner.nextInt();
        try {
            switch (swValue) {
                case 1:
                    System.out.println("Please enter class path and name:");
                    String className = scanner.next();
                    cl.addClass(className);
                    run(cl, scanner);
                case 2:
                    System.out.println("Please enter class path and name:");
                    String updateClassName = scanner.next();
                    cl.updateClass(updateClassName);
                    run(cl, scanner);
                case 3:
                    break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
