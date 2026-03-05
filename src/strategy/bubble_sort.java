package strategy;

import visualization.visualization_element;

import java.util.ArrayList;
import java.util.Arrays;

public class bubble_sort implements sorting_strategy  {
    private boolean visualization;
    public bubble_sort(boolean visualization) {
        this.visualization = visualization;
    }
    public int[] sort(int[] array, ArrayList<visualization_element> steps) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length-i-1; j++) {
                steps.add(new visualization_element(Arrays.copyOfRange(array, 0, array.length),j,j+1,true));
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    steps.addLast(new visualization_element(Arrays.copyOfRange(array, 0, array.length),j,j+1,false));
                }
            }
        }
        return array;
    }
}