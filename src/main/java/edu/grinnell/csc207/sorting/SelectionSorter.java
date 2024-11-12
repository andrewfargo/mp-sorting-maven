package edu.grinnell.csc207.sorting;

import edu.grinnell.csc207.util.ArrayUtils;
import java.util.Comparator;

/**
 * Something that sorts using selection sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Andrew Fargo
 * @author Samuel A. Rebelsky
 */

public class SelectionSorter<T> implements Sorter<T> {
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
  public SelectionSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // SelectionSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Find the last maximum index of an array between two indices.
   * My understanding of low-level cache is that iterating backwards
   * tends to be less efficient, though I've yet to test it.
   *
   * Thus, instead of iterating backwards, I choose to take equal
   * elements as maxes; it's the same number of comparisons but
   * takes more swaps. The upside is that it may be faster
   * on the machine level.
   *
   * @param values
   *   The array
   * @param start
   *   The starting index. (inclusive)
   * @param end
   *   The ending index. (exclusive)
   * @return The largest index for which all values of the array
   *   are less than or equal to the value at that index.
   */
  public int select(T[] values, int start, int end) {
    int max = start;
    for (int i = start + 1; i < end; i++) {
      // This is inefficient access, but I'm betting
      // on the compiler or cache optimizing it.
      if (order.compare(values[max], values[i]) <= 0) {
        max = i;
      } // if
    } // for i
    return max;
  } // select

  /**
   * Perform selection sort on a portion of the array.
   * Not used for typical selection sort, since it is
   * not a divide-and-conquer algorithm, but useful
   * for my personal sort.
   *
   * @param values The array
   * @param l The inclusive lower bound.
   * @param r The exclusive upper bound.
   */
  public void sortPartial(T[] values, int l, int r) {
    for (int j = r - 1; j > l; j--) {
      int i = this.select(values, l, j + 1);
      ArrayUtils.swap(values, i, j);
    } // for
  } // sortPartial(T[], int, int)

  /**
   * Sort an array in place using selection sort.
   * Made to mimic the selection sort presented in the CSC207 reading,
   * and algorithm S from Knuth's TAOCP 5.2.3
   *
   * The decision to use lastMaxBetween should ensure that the sort
   * is stable, since it selects in reverse order.
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
    this.sortPartial(values, 0, values.length);
  } // sort(T[])
} // class SelectionSorter
