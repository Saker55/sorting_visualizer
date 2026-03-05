package strategy;

import visualization.visualization_element;

import java.util.ArrayList;
import java.util.Arrays;

public class quick_sort implements sorting_strategy  {
    private boolean visualization;
    public quick_sort(boolean visualization) {
        this.visualization = visualization;
    }
    public int[] sort(int[] array, ArrayList<visualization_element> steps) {

        return sort(array,steps,0,array.length-1);
    }
    public int[] sort(int[] array, ArrayList<visualization_element> steps,int start,int end) {

        if (start >= end) {
            return array;
        }

        int pivot = array[start];
        int j = start;
        for (int i = start+1; i <= end; i++) {
            steps.add(new visualization_element(Arrays.copyOfRange(array, 0, array.length),i,start,true));
            if (array[i] <= pivot) {
                j++;
                int temp = array[j];
                array[j] = array[i];
                array[i] = temp;
                steps.add(new visualization_element(Arrays.copyOfRange(array, 0, array.length),i,j,false));
            }
        }
        int temp = array[j];
        array[j] = array[start];
        array[start] = temp;
        steps.add(new visualization_element(Arrays.copyOfRange(array, 0, array.length),start,j,false));
        sort(array,steps,j+1,end);
        sort(array,steps,start,j-1);
        return array;
    }
}