package strategy;

import visualization.visualization_element;

import java.util.ArrayList;
import java.util.Arrays;

public class insertion_sort implements sorting_strategy  {
    private boolean visualization;
    public insertion_sort(boolean visualization) {
        this.visualization = visualization;
    }
    public int[] sort(int[] array, ArrayList<visualization_element> steps) {

        if (visualization){
            for (int i = 1; i < array.length; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    steps.add(new visualization_element(Arrays.copyOfRange(array, 0, array.length), j, j + 1, true));
                    if (array[j] > array[j + 1]) {
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                        steps.add(new visualization_element(Arrays.copyOfRange(array, 0, array.length), j, j + 1, false));
                    } else {
                        break;
                    }
                }
            }
            return array;
        }
        else {
            for (int i = 1; i < array.length; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (array[j] > array[j + 1]) {
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    } else {
                        break;
                    }
                }
            }
            steps.add(new visualization_element(Arrays.copyOfRange(array, 1, array.length), 0, 0, false));
            return array;
        }
    }
}
