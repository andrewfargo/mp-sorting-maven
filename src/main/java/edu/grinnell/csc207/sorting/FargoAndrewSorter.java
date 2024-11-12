package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.Arrays;

import edu.grinnell.csc207.util.ArrayUtils;

/**
 * Something that sorts using FargoAndrewSort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Andrew N. Fargo
 */
public class FargoAndrewSorter<T> implements Sorter<T> {
  /**
   * How to compare elements.
   */
  Comparator<? super T> order;

  /**
   * Our random number generator.
   */
  Random rng;

  /**
   * A finer-detail selection sort.
   */
  SelectionSorter ssort;
  
  /**
   * Create the sorter using a comparator.
   *
   * @param comparator
   *   A comparator object that determines the ordering after
   *   sorting.
   */
  public FargoAndrewSorter(Comparator<? super T> comparator) {
    this.order = comparator;
    this.rng = new Random();
    this.ssort = new SelectionSorter(order);
  } // FargoAndrewSorter(Comparator)

  /**
   * Return the larger of two elements.
   * @param first The first element.
   * @param second The second element.
   * @return The larger element.
   */
  private T max(T first, T second) {
    return order.compare(first, second) > 0 ? first : second;
  } // max(T, T)

  /**
   * Return the smaller of two elements.
   * @param first The first element.
   * @param second The second element.
   * @return The smaller element.
   */
  private T min(T first, T second) {
    return order.compare(first, second) < 0 ? first : second;
  } // min(T, T)
  
  /**
   * Finds the middle of three random values without branches.
   * @param values
   *   The array.
   * @param l
   *   The inclusive lower bound of the subarray.
   * @param r
   *   The exclusive upper bound of the subarray.
   */
  @SuppressWarnings({"unchecked"})
  private T getMedian(T[] values, int l, int r) {
    T[] samp = (T[]) rng.ints(3, l, r).mapToObj(i -> values[i]).toArray();
    /* https://stackoverflow.com/questions/1582356
       /fastest-way-of-finding-the-middle-value-of-a-triple/14676309#14676309 */
    return max(min(samp[0], samp[1]),
	       min(max(samp[0], samp[1]), samp[2]));
  } // getMedian(T[], int, int)
  
  /**
   * The recursive "kernel" to the FargoAndrewSorter.
   * @param values
   *   The array.
   * @param l
   *   The inclusive lower bound.
   * @param r
   *   The exclusive upper bound.
   */
  private void fsort(T[] values, int l, int r) {
    final int M = 20;
    if (r - l <= M) {
      ssort.sortPartial(values, l, r);
      return;
    } // if
    
    // T pivot = values[rng.nextInt(r - l) + l];
    final T pivot = this.getMedian(values, l, r);

    int aux[] = new int[2];
    Quicksorter.partition(values, pivot, order, l, r, aux);
    
    fsort(values, l, aux[0]);
    fsort(values, aux[1], r);
  } // fsort(T[])
  
  /**
   * FargoAndrewSort- My entry into the sorting competition.
   * Implements a lot of the theory by Robert Sedgewick presented
   * by Donald E. Knuth in "The Art of Computer Programming"
   * section 5.2.2.
   *
   * We first follow Hoare's suggestion to select the median
   * of three random values, rather than the left-most or single
   * random value. In testing this improves time very slightly,
   * but relies on a hacky way to calculate the median.
   *
   * Second, instead of perfoming a final insertion sort pass
   * like Sedgewick suggests, we perform a selection sort on small
   * subarrays individually. In testing on my computer, this greatly
   * saves time. I suspect comparison of the underlying objects is fast.
   *
   * For more details, see the README.
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
    fsort(values, 0, values.length);
  } // sort(T[])
} // FargoAndrewSorter
