package edu.grinnell.csc207.util;

import java.util.Comparator;
import java.util.Random;

/**
 * Utilities for working with arrays.
 *
 * @author Samuel A. Rebelsky
 */
public class ArrayUtils {
  // +---------------+-----------------------------------------------
  // | Static Fields |
  // +---------------+

  /**
   * A handy, dandy random-number generator.
   */
  static Random rand = new Random();

  // +----------------+----------------------------------------------
  // | Static Methods |
  // +----------------+

  /**
   * Swap two elements in an array.
   *
   * @param <T>
   *   The type of elements stored in the array.
   * @param values
   *   The array.
   * @param p
   *   The position of one element.
   * @param q
   *   The position of the other element.
   * @pre 0 *lt;= p,q &lt; values.length
   */
  public static <T> void swap(T[] values, int p, int q) {
    if (p == q) {
      return;
    } // if
    T tmp = values[p];
    values[p] = values[q];
    values[q] = tmp;
  } // swap(T[], int, int)

  /**
   * Randomly permute elements in an array.
   *
   * @param <T>
   *   The type of elements stored in the array.
   * @param values
   *   The array.
   */
  public static <T> void permute(T[] values) {
    for (int i = 0; i < values.length; i++) {
      swap(values, i, rand.nextInt(values.length));
    } // for
  } // permute(T[])

  /**
   * Find the first maximum index of an array between two indices.
   *
   * @param <T>
   *   The type of elements stored in the array.
   * @param values
   *   The array
   * @param start
   *   The starting index. (inclusive)
   * @param end
   *   The ending index. (exclusive)
   * @return The smallest index for which all values of the array
   *   are less than or equal to the value at that index.
   */
  public static <T> int maxBetween(T[] values, Comparator<? super T> order,
				   int start, int end) {
    int max = end - 1;
    for (int i = start + 1; i < end; i++) {
      // This is inefficient access, but I'm betting
      // on the compiler or cache helping.
      if (order.compare(values[max], values[i]) < 0) {
	max = i;
      } // if
    } // for i
    return max;
  } // maxBetween
  
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
   * @param <T>
   *   The type of elements stored in the array.
   * @param values
   *   The array
   * @param start
   *   The starting index. (inclusive)
   * @param end
   *   The ending index. (exclusive)
   * @return The largest index for which all values of the array
   *   are less than or equal to the value at that index.
   */
  public static <T> int lastMaxBetween(T[] values, Comparator<? super T> order,
				       int start, int end) {
    int max = start;
    for (int i = start + 1; i < end; i++) {
      // This is inefficient access, but I'm betting
      // on the compiler or cache optimizing it.
      if (order.compare(values[max], values[i]) <= 0) {
	max = i;
      } // if
    } // for i
    return max;
  } // maxBetween

  /*
   * Convert a subset of an array to a string. Intended mostly for debug 
   * printing in which we need to keep track of parts of an array.
   *
   * @param <T>
   *   The type of elements in the array.
   * @param values
   *   The array.
   * @param lb
   *   The lower bound of the section of interest.
   * @param ub
   *   The upper bound of the section of interest.
   */
  public static <T> String toString(T[] values, int lb, int ub) {
    if (lb >= ub) {
      return "[]";
    } // if
    StringBuilder result = new StringBuilder("[");
    result.append(lb);
    result.append(":");
    result.append(values[lb].toString());
    for (int i = lb+1; i < ub; i++) {
      result.append(", ");
      result.append(i);
      result.append(":");
      result.append(values[i].toString());
    } // for
    result.append("]");
    return result.toString();
  } // toString(T[], int, int)
} // ArrayUtils
