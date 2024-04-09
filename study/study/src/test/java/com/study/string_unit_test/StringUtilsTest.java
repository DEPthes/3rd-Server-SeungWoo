package com.study.string_unit_test;

import com.study.string_unit.StringUtils;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    @Test
    @DisplayName("문자열 합치기 테스트")
    public void 문자열합치기() {
        // Given
        String str1 = "Hello";
        String str2 = "World";

        // When
        String result = StringUtils.concatenate(str1, str2);

        // Then
        assertEquals("HelloWorld", result);
    }

    @Test
    @DisplayName("펠린드롬 문자 테스트")
    public void 문자열뒤집기() {
        // Given
        String palindrome = "대학생학대";

        // When
        boolean result = StringUtils.isPalindrome(palindrome);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("중복 단어 제거 테스트")
    public void 중복단어제거() {
        // Given
        String input = "helloo";

        // When
        String result = StringUtils.removeDuplicates(input);

        // Then
        assertEquals("helo", result);
    }
}
