package co.com.avvillas.anagrams.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

  public static final String ONLY_LETTERS_REGEX = "[a-zA-Z]+";

  public static boolean wordMatchRegex(String word, String regex) {

    Pattern patterns = Pattern.compile(regex);
    Matcher matcher = patterns.matcher(word);
    return matcher.matches();
  }
}
