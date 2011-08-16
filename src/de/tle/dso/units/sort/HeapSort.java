package de.tle.dso.units.sort;

import java.util.Comparator;

public class HeapSort {

  /**
   * Standard heapsort.
   * @param a an array of Comparable items.
   */
  public static void heapsort(Object[] a, Comparator comparator) {
    if (a.length < 2) return;
    for (int i = a.length / 2; i >= 0; i--) /* buildHeap */ {
      percDown(a, i, a.length, comparator);
    }
    for (int i = a.length - 1; i > 0; i--) {
      swapReferences(a, 0, i);            /* deleteMax */
      percDown(a, 0, i, comparator);
    }
  }

  /**
   * Internal method for heapsort.
   * @param i the index of an item in the heap.
   * @return the index of the left child.
   */
  private static int leftChild(int i) {
    return 2 * i + 1;
  }

  /**
   * Internal method for heapsort that is used in
   * deleteMax and buildHeap.
   * @param a an array of Comparable items.
   * @index i the position from which to percolate down.
   * @int n the logical size of the binary heap.
   */
  private static void percDown(Object[] a, int i, int n, Comparator comparator) {
    int child;
    Object tmp;

    for (tmp = a[ i]; leftChild(i) < n; i = child) {
      child = leftChild(i);
      if (child != n - 1 && comparator.compare(a[ child], a[ child + 1]) < 0) {
        child++;
      }
      if (comparator.compare(tmp, a[ child]) < 0) {
        a[ i] = a[ child];
      } else {
        break;
      }
    }
    a[ i] = tmp;
  }

  /**
   * Method to swap to elements in an array.
   * @param a an array of objects.
   * @param index1 the index of the first object.
   * @param index2 the index of the second object.
   */
  public static void swapReferences(Object[] a, int index1, int index2) {
    Object tmp = a[ index1];
    a[ index1] = a[ index2];
    a[ index2] = tmp;
  }
}
