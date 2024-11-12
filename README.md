# mp-sorting-maven

An exploration of sorting in Java.

### Authors

* Andrew N. Fargo
* Samuel A. Rebelsky (starter code)

### Acknowledgements

Significant amounts of theory have been taken from the following, and have been cited within code:
* Donald Knuth's *The Art of Computer Programming* Volume 2: *Sorting and Searching* 2nd Edition.
* Sam Rebelsky's reading on Insertion Sort and Selection Sort: <https://rebelsky.cs.grinnell.edu/Courses/CSC207/2024Fa/readings/sorting.html>
* Sam Rebelsky's reading on Merge Sort: <https://rebelsky.cs.grinnell.edu/Courses/CSC207/2024Fa/readings/mergesort.html>
* Sam Rebelsky's reading on Quicksort: <https://rebelsky.cs.grinnell.edu/Courses/CSC207/2024Fa/readings/quicksort.html>

* I referenced this StackOverflow article for a branchless method
  of finding the median of three values: <https://stackoverflow.com/questions/1582356/fastest-way-of-finding-the-middle-value-of-a-triple/14676309#14676309>

The project specification, which also laid out general guidance, may be found at this link: <https://rebelsky.cs.grinnell.edu/Courses/CSC207/2024Fa/mps/mp08.html>

This code may be found at <https://github.com/andrewfargo/mp-sorting-maven>. The original code may be found at <https://github.com/Grinnell-CSC207/mp-sorting-maven>.

Description of custom sorting algorithm
---------------------------------------

My sorting algorithm is largely based off of Quicksort, with a few optimizations put forth
by Tony Hoare and Robern Sedgewick, presented in Knuth's *TAOCP* section 5.2.2.

The partitioning algorithm remains identical to quicksort, and for large subarrays this
is used to divide-and-conquer. However, I implement two changes from `Quicksorter.java`:

1. The pivot is calculated as the median of three random elements in the range. (Hoare)
2. Small subarrays use an alternative sort. (Sedgewick)

Theoretically, insertion sort is proven to be the best option, performed on subarrays
of size 9 or smaller. (Knuth) This assumes, to my understanding, that swapping and comparing
are atomic operations. However, in my own testing it seems that selection sort
reduces running time, possibly because of the increased expense of swapping. Furthermore,
rough testing on different critical values led me to choose subarrays of size 20 to use selection
sort.

Though selection sort is an O(n^2) algorithm, testing in comparison with merge sort and quicksort
shows that this approach runs in similar time to O(nlogn), comparable to my implementation of Quicksort and Mergesort, even better about half of the time on my computer.
