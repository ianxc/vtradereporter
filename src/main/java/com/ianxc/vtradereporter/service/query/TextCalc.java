package com.ianxc.vtradereporter.service.query;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TextCalc {
    boolean areAsciiAnagrams(String str1, String str2) {
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
