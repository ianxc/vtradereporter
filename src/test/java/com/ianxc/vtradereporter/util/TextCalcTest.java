package com.ianxc.vtradereporter.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class TextCalcTest {
    private final TextCalc textCalc = new TextCalc();

    @ParameterizedTest
    @CsvSource({
            "abc, bac, true",
            "abc, bAc, false",
            "EMU_BANK, KEMU_NAB, true",
            "ab, abc, false",
    })
    void when_anagram_then_returnTrueOtherwiseFalse(String s1, String s2, boolean expected) {
        final var result = textCalc.areAnagrams(s1, s2);

        assertThat(result).isEqualTo(expected);
    }

}
