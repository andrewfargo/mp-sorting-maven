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
   * Sort a subarray using merge sort. A recursive "kernel" that
   * keeps track of the start and end bounds.
   *
   * @param values
   *   The array.
   * @param start
   *   The (inclusive) lower bound.
   * @param end
   *   The (exclusive) upper bound.
   */
  @SuppressWarnings({"unchecked"})
  private void mergeSort(T[] values, int start, int end) {
    if (end - start <= 1) {
      return;
    } // if
    /* Recurse. */
    int mid = start + (end - start) / 2;
    mergeSort(values, start, mid);
    mergeSort(values, mid, end);

    /* Merge the two arrays. */
    T[] merged = (T[]) new Object[end-start];
    int i = start;
    int j = mid;
    int n = 0;
    while (i < mid && j < end) {
      if (order.compare(values[i], values[j]) <= 0) {
	merged[n++] = values[i++];
      } else {
	merged[n++] = values[j++];
      } // if/else
    } // while

    /* Send the remaining elements. */
    while (i < mid) {
      merged[n++] = values[i++];
    } // while
    while (j < end) {
      merged[n++] = values[j++];
    } // while
    i = start;
    for (T val : merged) {
      values[i++] = val;
    } // for
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
  public void sort(T[] values) {
    mergeSort(values, 0, values.length);
  } // sort(T[])
} // class MergeSorter
