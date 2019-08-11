package com.mytechideas.appmoto;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int [] arrayOfSortedNumbers={1, 5, 7, 8, 9, 11, 18, 19, 20, 25,10};

        assertEquals(0, findTarget(arrayOfSortedNumbers,1));
        assertEquals(1, findTarget(arrayOfSortedNumbers,5));
        assertEquals(2, findTarget(arrayOfSortedNumbers,7));
        assertEquals(3, findTarget(arrayOfSortedNumbers,8));
        assertEquals(4, findTarget(arrayOfSortedNumbers,9));
        assertEquals(5, findTarget(arrayOfSortedNumbers,11));
        assertEquals(6, findTarget(arrayOfSortedNumbers,18));
        assertEquals(7, findTarget(arrayOfSortedNumbers,19));
        assertEquals(8, findTarget(arrayOfSortedNumbers,20));
        assertEquals(9, findTarget(arrayOfSortedNumbers,25));
        assertEquals(-1, findTarget(arrayOfSortedNumbers,23));
        assertEquals(-1, findTarget(arrayOfSortedNumbers,45));
        assertEquals(-1, findTarget(arrayOfSortedNumbers,109));
        assertEquals(10, findTarget(arrayOfSortedNumbers,10));
    }

    public static int findTarget(int[] arr, int target) {
        if (arr.length == 0) return -1;


        int start = 0, end = arr.length - 1;

        while (start<=end) {

            int mid = (start+end)/2;

            if(arr[mid]==target)
                return mid;
            else if(arr[mid]<target){
                start=mid+1;
            }
            else {
                end=mid-1;
            }

        }


        return -1;
    }
}