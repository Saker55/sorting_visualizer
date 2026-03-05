package visualization;

public class visualization_element {
    int[] array;
    int i1;
    int i2;
    boolean compare_or_swap;

    public visualization_element(int[] array, int i1, int i2, boolean compare_or_swap) {
        this.array = array;
        this.i1 = i1;
        this.i2 = i2;
        this.compare_or_swap = compare_or_swap;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public int getI2() {
        return i2;
    }

    public void setI2(int i2) {
        this.i2 = i2;
    }

    public boolean isCompare_or_swap() {
        return compare_or_swap;
    }

    public void setCompare_or_swap(boolean compare_or_swap) {
        this.compare_or_swap = compare_or_swap;
    }
}
