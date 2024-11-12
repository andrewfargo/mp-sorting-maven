package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * Something that sorts using merge sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Samuel A. Rebelsky
 */

public class MergeSorter<T> implements Sorter<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The way in which elements are ordered.
   */
  Comparator<? super T> order;

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
  public MergeSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // MergeSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Merge two adjacent subarrays.
   *
   * @param source The greater array.
   * @param sink The auxiliary array.
   * @param start the (inclusive) lower bound
   * @param mid the midpoint of the two (belongs to second)
   * @param end the (exclusive) upper bound
   */
  private void merge(T[] source, T[] sink,
                     int start, int mid, int end) {
    int i = start;
    int j = mid;
    int n = start;
    while (i < mid && j < end) {
      if (order.compare(source[i], source[j]) <= 0) {
        sink[n++] = source[i++];
      } else {
        sink[n++] = source[j++];
      } // if/else
    } // while

    /* Send the remaining elements. */
    while (i < mid) {
      sink[n++] = source[i++];
    } // while
    while (j < end) {
      sink[n++] = source[j++];
    } // while
    for (int k = start; k < end; k++) {
      source[k] = sink[k];
    } // for
  } // merge(T[], T[], int, int, int)

  /**
   * Sort a subarray using merge sort. A recursive "kernel" that
   * keeps track of the start and end bounds.
   *
   * @param values
   *   The array.
   * @param helper
   *   The auxiliary array.
   * @param start
   *   The (inclusive) lower bound.
   * @param end
   *   The (exclusive) upper bound.
   */
  private void mergeSort(T[] values, T[] helper, int start, int end) {
    if (end - start <= 1) {
      return;
    } // if
    /* Recurse. */
    int mid = start + (end - start) / 2;
    mergeSort(values, helper, start, mid);
    mergeSort(values, helper,  mid, end);

    /* Merge the two arrays. */
    merge(values, helper, start, mid, end);
  } // mergeSort

  /**
   * Sort an array in place using merge sort.
   * Made to mimic the merge sort presented in the CSC207 reading.
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
  @SuppressWarnings({"unchecked"})
  public void sort(T[] values) {
    T[] helper = (T[]) new Object[values.length];
    mergeSort(values, helper, 0, values.length);
  } // sort(T[])
} // class MergeSorter
