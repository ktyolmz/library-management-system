package com.ktyolmz.library.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestUtils {

    Utils utils = mock(Utils.class);

    @Test
    void testGetNonMatchingElements() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(3, 4, 5, 6, 7);
        List<Integer> expectedNonMatching = Arrays.asList(1, 2);

        Set<Integer> set1 = new HashSet<>(list1);
        Set<Integer> set2 = new HashSet<>(list2);
        List<Integer> nonMatchingElements = new ArrayList<>(set1);
        nonMatchingElements.removeAll(set2);

        when(utils.getNonMatchingElements(list1, list2)).thenReturn(nonMatchingElements);

        assertEquals(expectedNonMatching, utils.getNonMatchingElements(list1, list2));
    }

    @Test
    void testGetNonMatchingElements_emptyLists() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> expectedNonMatching = new ArrayList<>();

        Set<Integer> set1 = new HashSet<>(list1);
        Set<Integer> set2 = new HashSet<>(list2);
        List<Integer> nonMatchingElements = new ArrayList<>(set1);
        nonMatchingElements.removeAll(set2);

        when(utils.getNonMatchingElements(list1, list2)).thenReturn(nonMatchingElements);

        assertEquals(expectedNonMatching, utils.getNonMatchingElements(list1, list2));
    }

    @Test
    void testGetNonMatchingElements_identicalLists() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(1, 2, 3);
        List<Integer> expectedNonMatching = new ArrayList<>();

        Set<Integer> set1 = new HashSet<>(list1);
        Set<Integer> set2 = new HashSet<>(list2);
        List<Integer> nonMatchingElements = new ArrayList<>(set1);
        nonMatchingElements.removeAll(set2);

        when(utils.getNonMatchingElements(list1, list2)).thenReturn(nonMatchingElements);

        assertEquals(expectedNonMatching, utils.getNonMatchingElements(list1, list2));
    }
}
