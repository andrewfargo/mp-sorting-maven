package edu.grinnell.csc207.sorting;

import edu.grinnell.csc207.util.ArrayUtils;
import java.util.Comparator;
import java.util.Random;

/**
 * Something that sorts using Quicksort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
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
   * Sort a subarray using the Dutch National Flag algorithm.
   *
   * @param values
   *   The larger array
   * @param start
   *   The (inclusive) lower bound
   * @param end
   *   The (exclusive) upper bound
   * @param p1
   *   The first pivot
   * @param p2
   *   The second pivot
   */
  private void dnf(T[] values, int start, int end, T p1, T p2) {
    int red = start;
    int white = start;
    int blue = end;

    while (white < blue) {
      boolean ltFirst = order.compare(values[white], p1) < 0;
      boolean ltSecond = order.compare(values[white], p2) < 0;
      if (ltFirst && ltSecond) {
	// red
	ArrayUtils.swap(values, white, red);
	red++;
	white++;
      } else if (ltFirst || ltSecond) {
	// white
	white++;
      } else {
	// blue
	ArrayUtils.swap(values, white, blue - 1);
	blue--;
      } // if/else
    } // while
  } // dnf(T[], int, int, T, T)

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
    /* Calculate both pivots. */
    int p1 = rng.nextInt(end - start) + start;
    int p2 = rng.nextInt(end - start) + start;
    /** Order them. */
    int temp = p1 < p2 ? p1 : p2;
    p1 = p1 < p2 ? p1 : p2;
    p2 = temp;
    
    /* Exchange members. */
    dnf(values, start, end, values[p1], values[p2]);
    /* Recurse. */
    quicksort(values, start, p1);
    quicksort(values, p1, p2);
    quicksort(values, p2, end);
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
   *   For all i, 0 &lt; i &lt; vals.length,
   *     order.compare(vals[i-1], vals[i]) &lt;= 0
   */
  @Override
  public void sort(T[] values) {
    quicksort(values, 0, values.length);
  } // sort(T[])
} // class Quicksorter
