package edu.grinnell.csc207.sorting;

import org.junit.jupiter.api.BeforeAll;

/**
 * Tests of FargoAndrewSorter.
 */
public class TestQuicksorter extends TestSorter {
  /**
   * Set up the sorters.
   */
  @BeforeAll
  static void setup() {
    stringSorter = new FargoAndrewSorter<String>((x,y) -> x.compareTo(y));
    intSorter = new FargoAndrewSorter<Integer>((x,y) -> x.compareTo(y));
  } // setup()

} // class FargoAndrewSorter
