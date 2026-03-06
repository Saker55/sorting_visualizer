import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import strategy.sorting_strategy;
import visualization.visualization_element;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class visualization_controller {
    @FXML
    Spinner<Integer> size ;
    @FXML
    Spinner<Integer> points ;
    @FXML
    Spinner<Integer> min_size ;
    @FXML
    Spinner<Integer> max_size ;
    @FXML
    Spinner<Integer> run_numbers ;
    @FXML
    TableView table;
    @FXML
    ComboBox<String> type;
    @FXML
    ComboBox<String> sorting_type;
    @FXML
    ComboBox<String> sorting_type1;
    @FXML
    Label files_label;
    @FXML
    Slider speed;
    @FXML
    Label Speed_label;
    @FXML
    HBox bars;
    @FXML
    Label interchanges;
    @FXML
    Label comparisons;
    @FXML
    CheckBox box;

    long comparison = 0;
    long interchange = 0;
    ArrayList<visualization_element> steps = new ArrayList<>();
    boolean p = false;
    int BarsHeight = 0;
    Timeline timeline = new Timeline();
    private int[][] importedArrays = null;
    private List<String> importedFileNames = new ArrayList<>();

    @FXML
    public void initialize(){
        type.getItems().addAll("Random","Sorted","Reverse Sorted","Import from file");
        sorting_type.getItems().addAll(
                        "Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "Heap Sort",
                "Merge Sort",
                "Quick Sort"
        );
        sorting_type1.getItems().addAll(
                "Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "Heap Sort",
                "Merge Sort",
                "Quick Sort"
        );
        size.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,50));
        points.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,30));
        min_size.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000,1));
        max_size.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000,10000,1000));
        run_numbers.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,2000,100));
        speed.setMax(2000);
        speed.setMin(1);
        speed.setValue(50);
        speed.valueProperty().addListener((observable, oldValue, newValue) -> {
            Speed_label.setText("Delay: " + newValue.intValue() + "ms");
        });
        interchanges.setText(interchange + " ");
        comparisons.setText(comparison + " ");
    }
    @FXML
    public void generate_button(){
        timeline.stop();
        timeline.getKeyFrames().clear();
        steps.clear();
        p = false;
        BarsHeight = (int) bars.getHeight();
        timeline = new Timeline();
        interchange = 0;
        comparison = 0;

        int[] arr = new int[0]; // initialize empty

        if (type.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select an array type.");
            alert.show();
            return;
        }

        if (type.getValue().equals("Random")) {
            arr = new int[(int) size.getValue()];
            for (int i = 0; i < arr.length; i++){
                arr[i] = (int)(Math.random() * 1000);
            }
        } else if (type.getValue().equals("Sorted")) {
            arr = new int[(int) size.getValue()];
            for (int i = 0; i < arr.length; i++){
                arr[i] = (int)(Math.random() * 1000);
            }
            Arrays.sort(arr);
        } else if (type.getValue().equals("Reverse Sorted")) {
            arr = new int[(int) size.getValue()];
            for (int i = 0; i < arr.length; i++){
                arr[i] = (int)(Math.random() * 1000);
            }
            Arrays.sort(arr);
            for (int i = 0; i < arr.length / 2; i++){
                int temp = arr[i];
                arr[i] = arr[arr.length - i - 1];
                arr[arr.length - i - 1] = temp;
            }
        } else if (type.getValue().equals("Import from file")) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Integer Files");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            List<File> files = fileChooser.showOpenMultipleDialog(bars.getScene().getWindow());

            if (files == null || files.isEmpty()) return;

            List<Integer> allIntegers = new ArrayList<>();

            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        for (String token : line.split(",")) {
                            String trimmed = token.trim();
                            if (!trimmed.isEmpty()) {
                                allIntegers.add(Integer.parseInt(trimmed));
                            }
                        }
                    }
                } catch (IOException | NumberFormatException e) {
                    System.err.println("Error reading file: " + file.getName() + " - " + e.getMessage());
                }
            }

            if (allIntegers.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No valid integers found in the selected files.");
                alert.show();
                return;
            }

            arr = allIntegers.stream().mapToInt(Integer::intValue).toArray();
        }

        if (arr.length == 0) return;

        steps.addLast(new visualization_element(arr, -1, -1, false));
        visualize_array(arr);
    }

    public void visualize_array(int [] arr){
        bars.getChildren().clear();
        bars.setSpacing(5);
        int max = Arrays.stream(arr).max().orElse(-1);
        double width = (bars.getWidth()- arr.length * bars.getSpacing())/arr.length;
        double min_hight = 3;
        for (int i = 0; i < arr.length; i++) {
            double hight = ((double) arr[i] / max) * BarsHeight + min_hight;
            Rectangle rect= new Rectangle();
            rect.setWidth(width);
            rect.setHeight(hight);
            rect.setFill(Color.BLUE);
            Label value = new Label(String.valueOf(arr[i]));

            VBox barBox = new VBox(5);
            barBox.setAlignment(Pos.BOTTOM_CENTER);

            barBox.getChildren().addAll(rect, value);

            bars.getChildren().add(barBox);
        }
    }

    @FXML
    public void run_button() throws InterruptedException {
        sorting_strategy s = factory.getstrategy(sorting_type.getValue(), box.isSelected());
        s.sort(steps.getFirst().getArray(), steps);
        AtomicInteger i = new AtomicInteger(1);

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(speed.getValue()), e -> {

                    if (!p) {

                        if (i.get() >= steps.size()) {
                            timeline.stop();
                            return;
                        }

                        visualization_element step = steps.get(i.get());
                        drawStep(step);

                        i.getAndIncrement();
                    }

                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public boolean pause(){
        p = !p ;
        return p;
    }

    private void drawStep(visualization_element step){

        bars.getChildren().clear();
        bars.setSpacing(5);

        int[] arr = step.getArray();
        int i1 = Math.min(step.getI1(), step.getI2());
        int i2 = Math.max(step.getI1(), step.getI2());
        boolean compare = step.isCompare_or_swap();

        int max = Arrays.stream(arr).max().orElse(1);

        double width = (bars.getWidth() - arr.length * bars.getSpacing()) / arr.length;

        if (compare) {
            comparison++;
        }
        else {
            interchange++;
        }

        for(int j = 0; j < arr.length; j++){

            double height = ((double) arr[j] / max) * BarsHeight + 3;

            Rectangle rect = new Rectangle(width, height);
            rect.setFill(Color.BLUE);

            if(j == i1 || j == i2) {
                rect.setFill(Color.RED);
            }

            Label value = new Label(String.valueOf(arr[j]));

            VBox barBox = new VBox(rect, value);
            barBox.setAlignment(Pos.BOTTOM_CENTER);

            bars.getChildren().add(barBox);
        }
        interchanges.setText(interchange + " ");
        comparisons.setText(comparison + " ");
    }

    @FXML
    public void run_comp() {
        String selectedAlgorithm = sorting_type1.getValue();
        if (selectedAlgorithm == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a sorting algorithm.");
            alert.show();
            return;
        }

        // Setup columns once on UI thread
        ObservableList<TableColumn<ComparisonResult, ?>> cols = table.getColumns();
        ((TableColumn<ComparisonResult, String>)  cols.get(0)).setCellValueFactory(new PropertyValueFactory<>("algorithmName"));
        ((TableColumn<ComparisonResult, Integer>) cols.get(1)).setCellValueFactory(new PropertyValueFactory<>("arraySize"));
        ((TableColumn<ComparisonResult, String>)  cols.get(2)).setCellValueFactory(new PropertyValueFactory<>("generationMode"));
        ((TableColumn<ComparisonResult, Integer>) cols.get(3)).setCellValueFactory(new PropertyValueFactory<>("numberOfRuns"));
        ((TableColumn<ComparisonResult, Double>)  cols.get(4)).setCellValueFactory(new PropertyValueFactory<>("averageRuntime"));
        ((TableColumn<ComparisonResult, Long>)    cols.get(5)).setCellValueFactory(new PropertyValueFactory<>("minRuntime"));
        ((TableColumn<ComparisonResult, Long>)    cols.get(6)).setCellValueFactory(new PropertyValueFactory<>("maxRuntime"));
        ((TableColumn<ComparisonResult, Long>)    cols.get(7)).setCellValueFactory(new PropertyValueFactory<>("comparisons"));
        ((TableColumn<ComparisonResult, Long>)    cols.get(8)).setCellValueFactory(new PropertyValueFactory<>("interchanges"));

        int totalPoints = points.getValue();
        int minSz       = min_size.getValue();
        int maxSz       = max_size.getValue();
        int runs        = run_numbers.getValue();

        // Snapshot imported data before background thread
        int[][] importedSnapshot     = importedArrays;
        List<String> namesSnapshot   = new ArrayList<>(importedFileNames);

        Task<ObservableList<ComparisonResult>> task = new Task<>() {
            @Override
            protected javafx.collections.ObservableList<ComparisonResult> call() {
                javafx.collections.ObservableList<ComparisonResult> results =
                        javafx.collections.FXCollections.observableArrayList();

                String[] modes    = {"Random", "Sorted", "Reverse Sorted"};
                int pointsPerMode = totalPoints / modes.length;
                int remainder     = totalPoints % modes.length;

                // --- Generated arrays block ---
                for (int m = 0; m < modes.length; m++) {
                    String mode    = modes[m];
                    int modePoints = pointsPerMode + (m < remainder ? 1 : 0);

                    for (int pt = 0; pt < modePoints; pt++) {
                        int arraySize = (modePoints == 1)
                                ? minSz
                                : minSz + (int) ((double) pt / (modePoints - 1) * (maxSz - minSz));

                        long totalTime = 0, minTime = Long.MAX_VALUE, maxTime = Long.MIN_VALUE;
                        long totalComparisons = 0, totalInterchanges = 0;

                        for (int r = 0; r < runs; r++) {
                            int[] arr = generateArray(arraySize, mode);
                            ArrayList<visualization_element> runSteps = new ArrayList<>();
                            runSteps.add(new visualization_element(arr, -1, -1, false));

                            sorting_strategy s = factory.getstrategy(selectedAlgorithm, false);
                            long start   = System.nanoTime();
                            s.sort(arr, runSteps);
                            long elapsed = System.nanoTime() - start;

                            long runComparisons = 0, runInterchanges = 0;
                            for (int i = 1; i < runSteps.size(); i++) {
                                if (runSteps.get(i).isCompare_or_swap()) runComparisons++;
                                else runInterchanges++;
                            }

                            totalTime         += elapsed;
                            totalComparisons  += runComparisons;
                            totalInterchanges += runInterchanges;
                            if (elapsed < minTime) minTime = elapsed;
                            if (elapsed > maxTime) maxTime = elapsed;
                        }

                        double avgMs = (totalTime / runs) / 1_000_000.0;
                        long   minMs = minTime / 1_000_000;
                        long   maxMs = maxTime / 1_000_000;

                        results.add(new ComparisonResult(
                                selectedAlgorithm, arraySize, mode, runs,
                                Math.round(avgMs * 100.0) / 100.0,
                                minMs, maxMs,
                                totalComparisons / runs,
                                totalInterchanges / runs
                        ));
                    }
                }

                // --- Imported files block ---
                if (importedSnapshot != null) {
                    for (int f = 0; f < importedSnapshot.length; f++) {
                        int[] baseArr   = importedSnapshot[f];
                        String modeName = namesSnapshot.get(f);

                        long totalTime = 0, minTime = Long.MAX_VALUE, maxTime = Long.MIN_VALUE;
                        long totalComparisons = 0, totalInterchanges = 0;

                        for (int r = 0; r < runs; r++) {
                            int[] arr = Arrays.copyOf(baseArr, baseArr.length);
                            ArrayList<visualization_element> runSteps = new ArrayList<>();
                            runSteps.add(new visualization_element(arr, -1, -1, false));

                            sorting_strategy s = factory.getstrategy(selectedAlgorithm, false);
                            long start   = System.nanoTime();
                            s.sort(arr, runSteps);
                            long elapsed = System.nanoTime() - start;

                            long runComparisons = 0, runInterchanges = 0;
                            for (int i = 1; i < runSteps.size(); i++) {
                                if (runSteps.get(i).isCompare_or_swap()) runComparisons++;
                                else runInterchanges++;
                            }

                            totalTime         += elapsed;
                            totalComparisons  += runComparisons;
                            totalInterchanges += runInterchanges;
                            if (elapsed < minTime) minTime = elapsed;
                            if (elapsed > maxTime) maxTime = elapsed;
                        }

                        double avgMs = (totalTime / runs) / 1_000_000.0;
                        long   minMs = minTime / 1_000_000;
                        long   maxMs = maxTime / 1_000_000;

                        results.add(new ComparisonResult(
                                selectedAlgorithm, baseArr.length, modeName, runs,
                                Math.round(avgMs * 100.0) / 100.0,
                                minMs, maxMs,
                                totalComparisons / runs,
                                totalInterchanges / runs
                        ));
                    }
                }

                return results;
            }
        };

        // When done, update UI and save CSV back on UI thread
        task.setOnSucceeded(e -> {
            javafx.collections.ObservableList<ComparisonResult> results = task.getValue();
            table.setItems(results);

            String filename = selectedAlgorithm.replace(" ", "_") + ".csv";
            try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
                pw.println("Algorithm,Array Size,Generation Mode,Runs,Avg Runtime (ms),Min Runtime (ms),Max Runtime (ms),Comparisons,Interchanges");
                for (ComparisonResult r : results) {
                    pw.printf("%s,%d,%s,%d,%.2f,%d,%d,%d,%d%n",
                            r.getAlgorithmName(), r.getArraySize(), r.getGenerationMode(),
                            r.getNumberOfRuns(), r.getAverageRuntime(),
                            r.getMinRuntime(), r.getMaxRuntime(),
                            r.getComparisons(), r.getInterchanges());
                }
                System.out.println("CSV saved: " + filename);
            } catch (IOException ex) {
                System.err.println("Failed to save CSV: " + ex.getMessage());
            }
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Comparison failed: " + task.getException().getMessage());
            alert.show();
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    // Helper to generate arrays by mode
    private int[] generateArray(int size, String mode) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = (int)(Math.random() * 1000);
        if (mode.equals("Sorted")) {
            Arrays.sort(arr);
        } else if (mode.equals("Reverse Sorted")) {
            Arrays.sort(arr);
            for (int i = 0; i < size / 2; i++) {
                int tmp = arr[i]; arr[i] = arr[size-i-1]; arr[size-i-1] = tmp;
            }
        }
        return arr;
    }

    @FXML
    public void add_files_comp() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Integer Files");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        List<File> files = fileChooser.showOpenMultipleDialog(table.getScene().getWindow());

        if (files == null || files.isEmpty()) return;

        importedArrays = new int[files.size()][];
        importedFileNames.clear();

        for (int f = 0; f < files.size(); f++) {
            List<Integer> integers = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(files.get(f)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    for (String token : line.split(",")) {
                        String trimmed = token.trim();
                        if (!trimmed.isEmpty()) integers.add(Integer.parseInt(trimmed));
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error reading: " + files.get(f).getName());
            }
            importedArrays[f] = integers.stream().mapToInt(Integer::intValue).toArray();
            importedFileNames.add(files.get(f).getName());
        }

        files_label.setText(importedFileNames.size() + " file(s) loaded: " + String.join(", ", importedFileNames));
    }
}
