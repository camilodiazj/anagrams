package co.com.avvillas.anagrams.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

  public static final String ONLY_LETTERS_AND_NUMBERS_REGEX = "[a-zA-Z0-9]+";
  public static final String ONLY_LETTERS_REGEX = "[a-zA-Z]+";
  public static final String TEXT_WITH_TWO_OR_MORE_BLANK_SPACES = "\\s{2,}";

  public static boolean wordMatchRegex(String word, String regex) {

    Pattern patterns = Pattern.compile(regex);
    Matcher matcher = patterns.matcher(word);
    return matcher.matches();
  }
}
