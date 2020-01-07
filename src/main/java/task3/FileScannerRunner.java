package task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by Diana Aimbetova on 1/7/2020
 **/

public class FileScannerRunner {

    public static void main(String[] args) throws IOException {
        final ForkJoinPool pool = new ForkJoinPool();
        System.out.print("Type an folder path: ");
        Scanner in = new Scanner(System.in);
        String folderPath = in.nextLine();
        FileScannerTask task = new FileScannerTask(folderPath);
        pool.invoke(task);

        Map<String, String> results = task.join();
        Integer sumFile = results.values().stream().filter(s -> s.equals(task.getIsFile())).toArray().length;
        Integer sumFolder = results.values().stream().filter(s -> s.equals(task.getIsFolder())).toArray().length;
        long size = Files.walk(Paths.get(folderPath)).mapToLong(p -> p.toFile().length() ).sum()/1024/1024;

        System.out.println("Number of files are: " + sumFile + ". Sum of folders are: " + sumFolder + ". Total size of files are: " + size + " MB.");

    }
}
