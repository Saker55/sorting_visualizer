package strategy;

import visualization.visualization_element;

import java.util.ArrayList;
import java.util.Arrays;

public class merge_sort implements sorting_strategy  {
    private boolean visualization;
    public merge_sort(boolean visualization) {
        this.visualization = visualization;
    }
    int[] merge(int[] array,ArrayList<visualization_element> steps ,int first,int mid,int last){
        if (visualization){
            int size1 = mid - first + 1;
            int size2 = last - mid;
            int[] arr = Arrays.copyOf(array, array.length);
            int p1 = 0;
            int p2 = 0;
            int k = first;
            while (p1 < size1 && p2 < size2) {
                steps.add(new visualization_element(Arrays.copyOfRange(arr, 0, array.length), first + p1, mid + 1 + p2, true));
                if (array[first + p1] <= array[mid + 1 + p2]) {
                    arr[k] = array[first + p1];
                    steps.add(new visualization_element(Arrays.copyOfRange(arr, 0, array.length), first + p1, k, false));
                    p1++;
                    k++;
                } else {
                    arr[k] = array[mid + 1 + p2];
                    steps.add(new visualization_element(Arrays.copyOfRange(arr, 0, array.length), mid + 1 + p2, k, false));
                    p2++;
                    k++;
                }
            }

            while (p1 < size1) {
                arr[k] = array[first + p1];
                steps.add(new visualization_element(Arrays.copyOfRange(arr, 0, array.length), first + p1, k, false));
                p1++;
                k++;
            }

            while (p2 < size2) {
                arr[k] = array[mid + 1 + p2];
                steps.add(new visualization_element(Arrays.copyOfRange(arr, 0, array.length), mid + 1 + p2, k, false));
                p2++;
                k++;
            }
            return arr;
        }
        else{
            int size1 = mid - first + 1;
            int size2 = last - mid;
            int[] arr = Arrays.copyOf(array, array.length);
            int p1 = 0;
            int p2 = 0;
            int k = first;
            while (p1 < size1 && p2 < size2) {
                if (array[first + p1] <= array[mid + 1 + p2]) {
                    arr[k] = array[first + p1];
                    p1++;
                    k++;
                } else {
                    arr[k] = array[mid + 1 + p2];
                    p2++;
                    k++;
                }
            }

            while (p1 < size1) {
                arr[k] = array[first + p1];
                p1++;
                k++;
            }

            while (p2 < size2) {
                arr[k] = array[mid + 1 + p2];
                p2++;
                k++;
            }
            return arr;
        }
    }
    private int[] mergeSort(int[] array, ArrayList<visualization_element> steps, int first, int last) {

        if (first >= last) {
            return array;
        }

        int mid = (first + last) / 2;

        array = mergeSort(array, steps, first, mid);
        array = mergeSort(array, steps, mid + 1, last);

        return merge(array, steps, first, mid, last);
    }

    public int[] sort(int[] array, ArrayList<visualization_element> steps) {
        array = mergeSort(array, steps, 0, array.length - 1);
        if (!visualization){
            steps.add(new visualization_element(Arrays.copyOfRange(array, 1, array.length), 0, 0, false));
        }
        return array;
    }

}