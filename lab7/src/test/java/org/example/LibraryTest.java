package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {


    @Test
    void mySort() {
        int[] input = new int[] {2, 3, 1, 4, 5};
        int[] expected = new int[] {1, 2, 3, 4, 5};
        int[] actual = Library.mySort(input);
        assertArrayEquals(expected, actual);
    }

    @Test
    void detectEvent1() {
        int input1 = 1;
        boolean expect1 = true;
        boolean actual1 = Library.detectOdd(input1);

        assertEquals(expect1, actual1);
    }

    @Test
    void detectEvent2() {
        int input2 = 2;
        boolean expect2 = false;
        boolean actual2 = Library.detectOdd(input2);

        assertEquals(expect2, actual2);
    }
}