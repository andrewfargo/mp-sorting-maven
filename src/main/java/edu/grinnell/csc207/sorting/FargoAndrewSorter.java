package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import java.util.Random;

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
   * Temporary result storage.
   */
  int[] result;

  /*
   * Methods copied over from other sorts in this MP.
   */

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
  private int select(T[] values, int start, int end) {
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
  private void sortPartial(T[] values, int l, int r) {
    for (int j = r - 1; j > l; j--) {
      int i = this.select(values, l, j + 1);
      ArrayUtils.swap(values, i, j);
    } // for
  } // sortPartial(T[], int, int)

  /**
   * Partition the subarray according to the pivot, using
   * the dutch national flag algorithm.
   *
   * @param values
   *   The array.
   * @param pivot
   *   The pivot.
   * @param start
   *   The (inclusive) lower bound of the subarray.
   * @param end
   *   The (exclusive) upper bound of the subarray.
   */
  private void partition(T[] values, T pivot,
                         int start, int end) {
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
  } // partition(T[], T, int, int)

  /*
   * End copied methods section.
   */

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
    this.result = new int[2];
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
   * @return
   *   The middle of three random values between values[l] and values[r - 1]
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
    final int threshold = 20;
    if (r - l <= threshold) {
      this.sortPartial(values, l, r);
      return;
    } // if

    // T pivot = values[rng.nextInt(r - l) + l];
    final T pivot = this.getMedian(values, l, r);

    this.partition(values, pivot, l, r);
    /* Push these to the stack, since they may get modified
       on recursive calls. */
    int m1 = result[0];
    int m2 = result[1];

    fsort(values, l, m1);
    fsort(values, m2, r);
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
