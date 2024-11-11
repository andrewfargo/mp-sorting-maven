package edu.grinnell.csc207.sorting;

import edu.grinnell.csc207.util.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 * Tests of Sorter objects. Please do not use this class directly.
 * Rather, you should subclass it and initialize stringSorter and
 * intSorter in a static @BeforeAll method.
 *
 * @author Your Name
 * @uathor Samuel A. Rebelsky
 */
public class TestSorter {

  // +---------+-----------------------------------------------------
  // | Globals |
  // +---------+

  /**
   * The sorter we use to sort arrays of strings.
   */
  static Sorter<String> stringSorter = null;

  /**
   * The sorter we use to sort arrays of integers.
   */
  static Sorter<Integer> intSorter = null;

  // +-----------+---------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Given a sorted array and a permutation of the array, sort the
   * permutation and assert that it equals the original.
   *
   * @param <T>
   *   The type of values in the array.
   * @param sorted
   *   The sorted array.
   * @param perm
   *   The permuted sorted array.
   * @param sorter
   *   The thing to use to sort.
   */
  public <T> void assertSorts(T[] sorted, T[] perm, Sorter<? super T> sorter) {
    T[] tmp = perm.clone();
    sorter.sort(perm);
    assertArrayEquals(sorted, perm,
      () -> String.format("sort(%s) yields %s rather than %s",
          Arrays.toString(tmp), 
          Arrays.toString(perm), 
          Arrays.toString(sorted)));
  } // assertSorts

  // +-------+-------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * A fake test. I've forgotten why I've included this here. Probably
   * just to make sure that some test succeeds.
   */
  @Test
  public void fakeTest() {
    assertTrue(true);
  } // fakeTest()

  /**
   * Ensure that an array that is already in order gets sorted correctly.
   */
  @Test
  public void orderedStringTest() {
    if (null == stringSorter) {
      return;
    } // if
    String[] original = { "alpha", "bravo", "charlie", "delta", "foxtrot" };
    String[] expected = original.clone();
    assertSorts(expected, original, stringSorter);
  } // orderedStringTest

  /**
   * Ensure that an array that is ordered backwards gets sorted correctly.
   */
  @Test
  public void reverseOrderedStringTest() {
    if (null == stringSorter) {
      return;
    } // if
    String[] original = { "foxtrot", "delta", "charlie", "bravo", "alpha" };
    String[] expected = { "alpha", "bravo", "charlie", "delta", "foxtrot" };
    assertSorts(expected, original, stringSorter);
  } // orderedStringTest

  /**
   * Ensure that a randomly permuted version of a moderate-sized
   * array sorts correctly.
   */
  @Test 
  public void permutedIntegersTest() { 
    int SIZE = 100; 
    if (null == intSorter) { 
      return; 
    } // if
    Integer[] original = new Integer[SIZE];
    for (int i = 0; i < SIZE; i++) {
      original[i] = i;
    } // for
    Integer[] expected = original.clone();
    ArrayUtils.permute(original);
    assertSorts(expected, original, intSorter);
  } // permutedIntegers

  /**
   * Random ints test. Ensures that a random array of integers--with
   * repeats--is sorted correctly.
   */
  @Test
  public void randomIntegersTest() {
    int SIZE = 100;
    int TRIALS = 3;
    if (intSorter == null) {
      return;
    } // if
    Random rng = new Random();
    Integer[] original = new Integer[SIZE];
    for (int trial = 0; trial < TRIALS; trial++) {
      // Set the integers
      Arrays.parallelSetAll(original, i -> rng.nextInt());
      // Sort using a known working method
      Integer[] expected = original.clone();
      Arrays.sort(expected);
      // Test
      assertSorts(expected, original, intSorter);
    } // for trial
  } // randomIntegersTest()

  /**
   * Test the sorts on extreme integers.
   */
  @Test
  public void extremeIntegersTest() {
    int SIZE = 100;
    if (intSorter == null) {
      return;
    } // if
    Integer[] expected = new Integer[SIZE];
    for (int i = 0; i < SIZE / 2; i++) {
      expected[i] = Integer.MIN_VALUE;
    } // for
    for (int j = SIZE / 2; j < SIZE; j++) {
      expected[j] = Integer.MAX_VALUE;
    } // for
    Integer[] original = expected.clone();
    ArrayUtils.permute(original);
    assertSorts(expected, original, intSorter);
  } // extremeIntegersTest

  /**
   * Test the sorts on strings with repeats in every position.
   */
  @Test
  public void repeatStringTest() {
    if (stringSorter == null) {
      return;
    } // if
    String[] source = {"alpha", "bravo", "charlie", "delta", "echo", "foxtrot"};
    String[] expected = new String[source.length + 1];
    for (int j = 0; j < source.length; j++) {
      for (int l = 0; l < j; l++) {
	expected[l] = source[l];
      } // for l
      expected[j] = source[j];
      for (int r = j + 1; r < expected.length; r++) {
	expected[r] = source[r - 1];
      } // for

      String[] original = expected.clone();
      ArrayUtils.permute(original);
      assertSorts(expected, original, stringSorter);
    } // for j
  } // repeatStringTest()

  /**
   * Test that sorts of integers that are already in order work.
   */
  @Test
  public void forwardIntTest() {
    int SIZE = 100;
    if (intSorter == null) {
      return;
    } // if
    Integer[] original = new Integer[SIZE];
    Arrays.parallelSetAll(original, i -> i);
    Integer[] expected = original.clone();
    assertSorts(expected, original, intSorter);
  } // forwardIntTest

  /**
   * Test that sorts of integers that are in reverse order work.
   */
  @Test
  public void reverseIntTest() {
    int SIZE = 100;
    if (intSorter == null) {
      return;
    } // if
    Integer[] original = new Integer[SIZE];
    Arrays.parallelSetAll(original, i -> (SIZE - i - 1));
    Integer[] expected = new Integer[SIZE];
    Arrays.parallelSetAll(expected, i -> i);
    assertSorts(expected, original, intSorter);
  } // reverseIntTest
} // class TestSorter
