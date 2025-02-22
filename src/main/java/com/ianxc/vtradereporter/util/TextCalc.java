package com.ianxc.vtradereporter.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TextCalc {
    public boolean areAnagrams(String str1, String str2) {
        // check length as an optimization
        if (str1.length() != str2.length()) return false;
        return freqOf(str1).equals(freqOf(str2));
    }

    private Map<Character, Integer> freqOf(String str) {
        final var frequencies = new HashMap<Character, Integer>();
        for (var ch : str.toCharArray()) {
            frequencies.merge(ch, 1, Integer::sum);
        }
        return frequencies;
    }
}
