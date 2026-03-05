import strategy.*;

public class factory {
    public static sorting_strategy getstrategy(String strategyType,boolean visualization){

        if(strategyType == null){
            return null;
        }

        if(strategyType.equalsIgnoreCase("quick")){
            return new quick_sort(visualization);

        }
        else if(strategyType.equalsIgnoreCase("merge")){
            return new merge_sort(visualization);

        }
        else if(strategyType.equalsIgnoreCase("bubble")){
            return new bubble_sort(visualization);
        }
        else if(strategyType.equalsIgnoreCase("selection")){
            return new selection_sort(visualization);
        }
        else if(strategyType.equalsIgnoreCase("insertion")){
            return new insertion_sort(visualization);
        }
        else if(strategyType.equalsIgnoreCase("heap")){
            return new heap_sort(visualization);
        }
        return null;
    }
}
