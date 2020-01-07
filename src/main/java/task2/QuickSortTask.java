package task2;

import java.util.concurrent.RecursiveTask;

/**
 * Created by Diana_Aimbetova on 1/5/2020
 **/

public class QuickSortTask extends RecursiveTask {
    private Integer[] array;
    private Integer start, end;

    public QuickSortTask(Integer[] array, Integer start, Integer end) {
        this.array = array;
        this.start = start;
        this.end = end;

    }

    @Override
    public Integer[] compute() {
        if (start < end) {
            int i = start, j = end;
            int cur = i - (i - j) / 2;
            while (i < j) {
                while (i < cur && (array[i] <= array[cur])) {
                    i++;
                }
                while (j > cur && (array[cur] <= array[j])) {
                    j--;
                }
                if (i < j) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    if (i == cur)
                        cur = j;
                    else if (j == cur)
                        cur = i;
                }
            }

            invokeAll(new QuickSortTask(array, start, cur), new QuickSortTask(array, cur + 1, end));
            return array;
        }

        return null;
    }
}
