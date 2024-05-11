package com.ktyolmz.library.utils;

import com.ktyolmz.library.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class Utils{

    public <T> List<T> getNonMatchingElements(List<T> list1, List<T> list2) {
        Set<T> set1 = new HashSet<>(list1);
        Set<T> set2 = new HashSet<>(list2);

        List<T> nonMatchingElements = new ArrayList<>(set1);
        nonMatchingElements.removeAll(set2);

        return nonMatchingElements;
    }
}
