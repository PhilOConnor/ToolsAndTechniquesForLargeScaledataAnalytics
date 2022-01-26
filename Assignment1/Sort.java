import java.util.Arrays;
import java.util.Comparator;
public class Sort<T> extends Thread{ // Class Sort is a generic class with type parameter T
    T[] array; // The array of objects of type T we want to sort
    Comparator<T> comp; // A Comparator instance suitable for comparing objects of type T
    public static void main(String[] args) {
	/*
	// A comparator for objects of type String:
        Comparator<String> compString = new Comparator<String>() {
            public int compare(String a, String b) {
                if (a.compareTo(b) > 0)
                    return 1;
                else
                    return 0;
            }
        };
	*/
        // Part B
        // Creating a comparator called compString with expected input String
        Comparator<String> compString
                // Setting up a & b as Strings. Then using the comparison from the given code and repacking it into a lambda function
                = (String a, String b) -> {if (a.compareTo(b) > 0){return 1;}else{return 0;}};

        Sort<String> sortStrings = new Sort<String>();

// Initialising an array of Strings with 16 unordered elements.
// Array length must be a power of 2.
        String[] arrayOfStrings = { "Blue", "Yellow", "Almond", "Onyx", "Peach", "Gold",
                "Red", "Melon", "Lava", "Beige", "Aqua", "Lilac", "Capri", "Orange", "Mauve", "Plum" };
        System.out.println("Original array: " + Arrays.toString(arrayOfStrings));
// Sorting the array by calling the sort-method
        sortStrings.sort(arrayOfStrings, compString);
        System.out.println("Sorted array: " + Arrays.toString(arrayOfStrings));

	/*
	 PART A
        // Creating a comparator of type Double and naming in compDouble, following the convention from above
        Comparator<Double> compDouble = new Comparator<Double>() {
        // Now comparing two Doubles, not strings
            public int compare(Double a, Double b) {
                if (a.compareTo(b) > 0)
                    return 1;
                else
                    return 0;
            }
        };
        */

        // PART B
        Comparator<Double> compDouble
                = (Double a, Double b) -> {if (a.compareTo(b) > 0){return 1;}else{return 0;}};


        // Creating a new sort object to handle the Doubles
        Sort<Double> sortDouble = new Sort<Double>();
        // Creating a new array of doubles to test out the sorting
        Double[] arrayOfDoubles = new Double[]{ 36d,24d,32d,64d,10d, 15d, 19d, 20d, 12d,13d, 51d, 8d, 53d, 7d, 61d, 25d};
        System.out.println("Original array: " + Arrays.toString(arrayOfDoubles));
// Sorting the array by calling the sort-method
        sortDouble.sort(arrayOfDoubles, compDouble);
        System.out.println("Sorted array: " + Arrays.toString(arrayOfDoubles));

    }
    public void sort(T[] array, Comparator<T> comp) { // Array length must be a power of 2
        this.array = array;
        this.comp = comp;
        sort(0, array.length);
    }
    private void sort(int low, int n) {
        if (n > 1) {
            int mid = n >> 1;
            // implementing a runnable interface within sort
            Runnable s1 =
                    () -> {sort(low, mid);};
            Runnable s2 =
                    () -> {sort(low + mid, mid);};
            Runnable c =
                    () -> {combine(low, n, 1);};

            // creating the threads needed to run each runnable interface
            Thread t1 = new Thread(s1);
            Thread t2 = new Thread(s2);
            Thread t3 = new Thread(c);
            // Starting the threads and printing out the thread ID to check that new threads are being used for each object.
            t1.start();
            System.out.println(Thread.currentThread().getName());
            t2.start();
            System.out.println(Thread.currentThread().getName());
            t3.start();
            System.out.println(Thread.currentThread().getName());

        }
    }

    private void combine(int low, int n, int st) {
        int m = st << 1;
        if (m < n) {
            combine(low, n, m);
            combine(low + st, n, m);
            for (int i = low + st; i + st < low + n; i += m)
                compareAndSwap(i, i + st);
        } else
            compareAndSwap(low, low + st);
    }
    private void compareAndSwap(int i, int j) {
        if (comp.compare(array[i], array[j]) > 0)
            swap(i, j);
    }
    private void swap(int i, int j) {
        T h = array[i];
        array[i] = array[j];
        array[j] = h;
    }
}
