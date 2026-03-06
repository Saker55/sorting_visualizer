import strategy.*;

public class factory {
    public static sorting_strategy getstrategy(String strategyType,boolean visualization){

        if(strategyType == null){
            return null;
        }

        if(strategyType.equalsIgnoreCase("Quick Sort")){
            return new quick_sort(visualization);

        }
        else if(strategyType.equalsIgnoreCase("Merge Sort")){
            return new merge_sort(visualization);

        }
        else if(strategyType.equalsIgnoreCase("Bubble Sort")){
            return new bubble_sort(visualization);
        }
        else if(strategyType.equalsIgnoreCase("Selection Sort")){
            return new selection_sort(visualization);
        }
        else if(strategyType.equalsIgnoreCase("Insertion Sort")){
            return new insertion_sort(visualization);
        }
        else if(strategyType.equalsIgnoreCase("Heap Sort")){
            return new heap_sort(visualization);
        }
        return null;
    }
}
