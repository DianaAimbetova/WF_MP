package runner;

import classloader.OwnClassLoader;

import java.util.Arrays;
import java.util.Scanner;


public class Runner {

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
        int swValue = scanner.nextInt();
        try {
            switch (swValue) {
                case 1:
                    System.out.println("Please enter class path and name:");
                    String className = scanner.next();
                    Class myClass = cl.addClass(className);
                    Arrays.stream(myClass.getDeclaredFields()).forEach(field -> System.out.println(field.getName()));
                    run(cl, scanner);
                    break;
                case 2:
                    System.out.println("Please enter class path and name:");
                    String updateClassName = scanner.next();
                    Class myUpdatedClass = cl.updateClass(updateClassName);
                    Arrays.stream(myUpdatedClass.getDeclaredFields()).forEach(field -> System.out.println(field.getName()));
                    run(cl, scanner);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
