package strategy;

import visualization.visualization_element;

import java.util.ArrayList;
import java.util.Arrays;

public class selection_sort implements sorting_strategy  {
    private boolean visualization;
    public selection_sort(boolean visualization) {
        this.visualization = visualization;
    }
    public int[] sort(int[] array, ArrayList<visualization_element> steps) {
        if (visualization){
            int min = 0;
            for (int i = 0; i < array.length; i++) {
                for (int j = i; j < array.length; j++) {
                    steps.add(new visualization_element(Arrays.copyOfRange(array, 0, array.length), j, min, true));
                    if (array[j] < array[min]) {
                        min = j;
                    }
                }
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
                steps.add(new visualization_element(Arrays.copyOfRange(array, 0, array.length), i, min, false));
                min = i + 1;
            }
            return array;
        }
        else {
            int min = 0;
            for (int i = 0; i < array.length; i++) {
                for (int j = i; j < array.length; j++) {
                    if (array[j] < array[min]) {
                        min = j;
                    }
                }
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
                min = i + 1;
            }
            steps.add(new visualization_element(Arrays.copyOfRange(array, 1, array.length), 0, 0, false));
            return array;
        }
    }
}
