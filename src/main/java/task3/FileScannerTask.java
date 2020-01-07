package task3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Diana Aimbetova on 1/7/2020
 **/

public class FileScannerTask extends RecursiveTask<Map<String,String>> {

    private String folderPath;
    private static final String IS_FILE = "file";
    private static final String IS_FOLDER = "folder";

    public static String getIsFile() {
        return IS_FILE;
    }

    public static String getIsFolder() {
        return IS_FOLDER;
    }

    public FileScannerTask(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    protected Map<String, String> compute() {
        Map<String, String> result = new HashMap<>();
        List<FileScannerTask> tasks = new ArrayList<>();
        File file = new File(folderPath);
        File[] filesList = file.listFiles();
        if (filesList != null) {
            for (final File f : filesList) {
                if (f.isDirectory()) {
                    FileScannerTask task = new FileScannerTask(f.getAbsolutePath());
                    task.fork();
                    tasks.add(task);
                    result.put(f.getAbsolutePath(),IS_FOLDER);
                }
                else {
                    result.put(f.getAbsolutePath(),IS_FILE);
                }
            }
        }
        if (tasks.size() > 10) {
            System.out.println(" Wait until tasks run....");
        }
        addResultsFromTasks(result, tasks);
        return result;
    }


    private void addResultsFromTasks(Map<String, String> map, List<FileScannerTask> tasks) {
        for (FileScannerTask task : tasks) {
            map.putAll(task.join());
        }
    }


}
