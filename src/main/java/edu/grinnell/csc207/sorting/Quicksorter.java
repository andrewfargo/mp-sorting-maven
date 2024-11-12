package edu.grinnell.csc207.sorting;

import edu.grinnell.csc207.util.ArrayUtils;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

/**
 * Something that sorts using Quicksort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Andrew N. Fargo
 * @author Samuel A. Rebelsky
 */

public class Quicksorter<T> implements Sorter<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The way in which elements are ordered.
   */
  Comparator<? super T> order;

  /**
   * Our random number generator to calculate the pivot.
   */
  Random rng;
  
  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a sorter using a particular comparator.
   *
   * @param comparator
   *   The order in which elements in the array should be ordered
   *   after sorting.
   */
  public Quicksorter(Comparator<? super T> comparator) {
    this.order = comparator;
    this.rng = new Random();
  } // Quicksorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Partition the subarray according to the pivot, using
   * the dutch national flag algorithm.
   *
   * @param <T>
   *   The type of objects to be sorted
   * @param values
   *   The array.
   * @param pivot
   *   The pivot.
   * @param order
   *   A comparator to judge different T values.
   * @param start
   *   The (inclusive) lower bound of the subarray.
   * @param end
   *   The (exclusive) upper bound of the subarray.
   * @param result
   *   The result of the computation, must be allocated.
   * @return Two integers representing the bounds of
   *   both partitions.
   */
  public static <T> void partition(T[] values, T pivot,
					Comparator<? super T> order,
				    int start, int end, int[] result) {
    int red = start;
    int white = start;
    int blue = end;
    
    while (white < blue) {
      int cmp = order.compare(values[white], pivot);
      if (cmp < 0) {
	// red
	ArrayUtils.swap(values, white, red);
	red++;
	white++;
      } else if (cmp > 0) {
	// blue
	ArrayUtils.swap(values, white, blue - 1);
	blue--;
      } else {
	// white
	white++;
      } // if/else
    } // while
    result[0] = red;
    result[1] = white;
  } // partition(T[], T, Comparator<? super T>, int, int)
  
  /**
   * Sort a subarray in place using a two-pivot Quicksort.
   * A recursive "kernel" to the "shell" of the sort() method.
   * @param values
   *   The larger array
   * @param start
   *   The lower bound (inclusive) of the subarray.
   * @param end
   *   The upper bound (exclusive) of the subarray.
   * @pre
   *   0 <= start <= end <= values.length
   * @post
   *   The subarray has been sorted.
   */
  private void quicksort(T[] values, int start, int end) {
    if (end - start <= 1) {
      return; // We are sorted.
    } // if
    T pivot = values[rng.nextInt(end - start) + start];

    int[] aux = new int[2];
    Quicksorter.partition(values, pivot, order, start, end, aux);
    
    /* Recurse. */
    quicksort(values, start, aux[0]);
    quicksort(values, aux[1], end);
  }

  /**
   * Sort an array in place using Quicksort.
   * Made to mimic the quicksort presented in the CSC207 reading,
   * but explicitly does not reference Knuth's algorithm Q (quicksort)
   * from TAOCP 5.2.2.
   *
   * Knuth presents one of Robert Sedgewick's optimized quicksorts
   * that involves a straight insertion sort for small subarrays,
   * and the use of an auxiliary stack as opposed to recursion.
   * For those reasons, I'll choose to implement the simpler one presented
   * by Sam Rebelsky.
   *
   * @param values
   *   an array to sort.
   *
   * @post
   *   The array has been sorted according to some order (often
   *   one given to the constructor).
   * @post
   *   For all i, 0 &lt; i &lt; values.length,
   *     order.compare(values[i-1], values[i]) &lt;= 0
   */
  @Override
  public void sort(T[] values) {
    quicksort(values, 0, values.length);
  } // sort(T[])
} // class Quicksorter
