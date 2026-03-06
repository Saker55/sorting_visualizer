import javafx.beans.property.*;

public class ComparisonResult {
    private final StringProperty algorithmName;
    private final IntegerProperty arraySize;
    private final StringProperty generationMode;
    private final IntegerProperty numberOfRuns;
    private final DoubleProperty averageRuntime;
    private final LongProperty minRuntime;
    private final LongProperty maxRuntime;
    private final LongProperty comparisons;
    private final LongProperty interchanges;

    public ComparisonResult(String algorithmName, int arraySize, String generationMode,
                            int numberOfRuns, double averageRuntime, long minRuntime,
                            long maxRuntime, long comparisons, long interchanges) {
        this.algorithmName   = new SimpleStringProperty(algorithmName);
        this.arraySize       = new SimpleIntegerProperty(arraySize);
        this.generationMode  = new SimpleStringProperty(generationMode);
        this.numberOfRuns    = new SimpleIntegerProperty(numberOfRuns);
        this.averageRuntime  = new SimpleDoubleProperty(averageRuntime);
        this.minRuntime      = new SimpleLongProperty(minRuntime);
        this.maxRuntime      = new SimpleLongProperty(maxRuntime);
        this.comparisons     = new SimpleLongProperty(comparisons);
        this.interchanges    = new SimpleLongProperty(interchanges);
    }

    public String getAlgorithmName()  { return algorithmName.get(); }
    public int getArraySize()         { return arraySize.get(); }
    public String getGenerationMode() { return generationMode.get(); }
    public int getNumberOfRuns()      { return numberOfRuns.get(); }
    public double getAverageRuntime() { return averageRuntime.get(); }
    public long getMinRuntime()       { return minRuntime.get(); }
    public long getMaxRuntime()       { return maxRuntime.get(); }
    public long getComparisons()      { return comparisons.get(); }
    public long getInterchanges()     { return interchanges.get(); }
}
