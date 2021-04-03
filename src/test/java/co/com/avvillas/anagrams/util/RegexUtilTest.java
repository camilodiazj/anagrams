package co.com.avvillas.anagrams.util;

import org.junit.Assert;
import org.junit.Test;

public class RegexUtilTest {

  @Test
  public void shouldWordMatchRegexReturnTrue(){
    boolean result = RegexUtils.wordMatchRegex("camilo", RegexUtils.ONLY_LETTERS_AND_NUMBERS_REGEX);
    Assert.assertTrue(result);
  }

  @Test
  public void shouldWordMatchRegexReturnFalse(){
    boolean result = RegexUtils.wordMatchRegex("cam7ilo", RegexUtils.ONLY_LETTERS_AND_NUMBERS_REGEX);
    Assert.assertFalse(result);
  }
}
