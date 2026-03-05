package strategy;

import visualization.visualization_element;

import java.util.ArrayList;
import java.util.Arrays;

public class heap_sort implements sorting_strategy {
    private boolean visualization;
    public heap_sort(boolean visualization) {
        this.visualization = visualization;
    }
    void max_heapify (int[] array, int size, int index, ArrayList<visualization_element> steps) {
        int left = index * 2;
        int right = (index * 2) + 1;
        int largest = index;
        steps.add(new visualization_element(Arrays.copyOfRange(array, 1, array.length),left-1,largest-1,true));
        if ( left <= size && array[largest] < array[left]) {
            largest = left;
        }

        steps.add(new visualization_element(Arrays.copyOfRange(array, 1, array.length),right-1,largest-1,true));
        if ( right <= size && array[largest] < array[right]) {
            largest = right;
        }

        steps.add(new visualization_element(Arrays.copyOfRange(array, 1, array.length),index-1,largest-1,true));
        if (largest != index){
            int temp = array[largest];
            array[largest] = array[index];
            array[index] = temp;
            steps.add(new visualization_element(Arrays.copyOfRange(array, 1, array.length),index-1,largest-1,false));
            max_heapify (array,size,largest, steps);
        }
    }

    void build_max_heap (int[] array, int size, ArrayList<visualization_element> steps) {
        for (int i = size/2 ; i >= 1 ;i--){
            max_heapify(array,size,i,  steps);
        }
    }


    public int[] sort(int[] array, ArrayList<visualization_element> steps) {
        int[] arr = new int[array.length+1];
        for (int i = 1; i < array.length+1 ; i++){
            arr[i]=array[i-1];
        }

        int heap_size = array.length;
        while (heap_size > 0) {
            build_max_heap(arr, heap_size,  steps);
            int temp = arr[1];
            arr[1] = arr[heap_size];
            arr[heap_size] = temp;
            heap_size--;
            max_heapify(arr, heap_size, 1,  steps);
        }

        for (int i = 0; i < array.length ; i++){
            array[i]=arr[i+1];
        }

        if (visualization == false) {
            steps= null;
        }

        return array;
    }
}