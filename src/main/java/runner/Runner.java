package runner;

import classloader.JarClassLoader;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Runner {

    public static void main(String[] args) {
        JarClassLoader cl = new JarClassLoader();
        Scanner scanner = new Scanner(System.in);
        System.out.println("|   MENU SELECTION     |");
        System.out.println("| Options:                 |");
        System.out.println("|        1. Add new JAR     |");
        System.out.println("|        2. Update existing class       |");
        System.out.println("|        3. Exit           |");
        run(cl, scanner);
    }

    private static void run(JarClassLoader cl, Scanner scanner) {
        System.out.println("Please choose method or enter 3 to exit...");
        int swValue = scanner.nextInt();
        try {
            switch (swValue) {
                case 1:
                    System.out.println("Please enter class path and name:");
                    String className = scanner.next();
                    List<Class> myClasses = cl.addClasses(className);
                    System.out.println("Number of classes: " + myClasses.size());
                    run(cl, scanner);
                    break;
                case 2:
                    System.out.println("Please enter class path and name:");
                    String updateClassName = scanner.next();
                    Class oldClass = cl.get(updateClassName);
                    //those outputs just to test that updated class is loaded
                    // delete or add fields and compare fields number
                    // don't forget to build the project to load updated class
                    // sorry for that
                    // know that should've write unit tests
                    //but there were no requirements to cover with unit tests :)
                    System.out.println("Fields number before change:" + Arrays.stream(oldClass.getDeclaredFields()).collect(Collectors.toList()).size());
                    Class myUpdatedClass = cl.updateClass(updateClassName);
                    System.out.println("Fields number after change:" + Arrays.stream(myUpdatedClass.getDeclaredFields()).collect(Collectors.toList()).size());
                    run(cl, scanner);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        } catch (ClassNotFoundException | JarClassLoader.NoSuchClassException e) {
            e.printStackTrace();
        }
    }
}
