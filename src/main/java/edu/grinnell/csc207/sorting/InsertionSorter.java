package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * Something that sorts using insertion sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Samuel A. Rebelsky
 */
public class InsertionSorter<T> implements Sorter<T> {
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
  public InsertionSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // InsertionSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Inserts the value at index into it's proper position.
   * That is, compares that value with values previous until
   * it is greater than or equal to it, and places it there.
   *
   * @param values
   *   The array.
   * @param index
   *   The index of the element to be sorted.
   */
  private void insert(T[] values, int index) {
      T element = values[index];
      int i = index - 1;
      while (i >= 0) {
	if (order.compare(element, values[i]) >= 0) {
	  break;
	} // if
	values[i+1] = values[i];
	i--;
      } // while
      values[i+1] = element;
  } // insert()

  /**
   * Sort an array in place using insertion sort.
   * Implemented to mimic the process found in the CSC207 reading
   * and algorithm S in Knuth's TAOCP 5.2.1. See README for citations.
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
    for (int j = 0; j < values.length; j++) {
      insert(values, j);
    } // for j
  } // sort(T[])
} // class InsertionSorter
