package strategy;

import visualization.visualization_element;

import java.util.ArrayList;

public interface sorting_strategy  {
    int[] sort(int[] array, ArrayList<visualization_element> steps );
}