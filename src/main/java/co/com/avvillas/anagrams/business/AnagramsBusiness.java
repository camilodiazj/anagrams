package co.com.avvillas.anagrams.business;

import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.service.definitions.IAnagramService;
import co.com.avvillas.anagrams.util.RegexUtils;
import java.util.Arrays;
import org.springframework.stereotype.Service;

@Service
public class AnagramsBusiness {

  private final IAnagramService anagramService;

  public AnagramsBusiness(
      IAnagramService anagramService) {
    this.anagramService = anagramService;
  }

  public AnagramResponse validateIfWordsAreAnagrams(String firstWord, String secondWord) {

    boolean areInputOk =
        RegexUtils.wordMatchRegex(firstWord.trim(), RegexUtils.ONLY_LETTERS_REGEX) && RegexUtils
            .wordMatchRegex(secondWord.trim(), RegexUtils.ONLY_LETTERS_REGEX);

    return areInputOk ? AnagramResponse.builder()
        .response(anagramService.areWordsAnagrams(firstWord, secondWord))
        .build() :
        AnagramResponse.builder().response("Words may have only letters and not be empty.").build();
  }

  public AnagramResponse validateIfSentencesShareAnagrams(String firstSentence,
      String secondSentence) {
    boolean isFirstSentenceOk = Arrays
        .stream(firstSentence.split(" ")) //TODO: Revisar si es necesario (ignorar numeros individuales)
        .allMatch(word -> RegexUtils.wordMatchRegex(word, RegexUtils.ONLY_LETTERS_REGEX));
    boolean isSecondSentenceOk = Arrays.stream(secondSentence.split(" "))
        .allMatch(word -> RegexUtils.wordMatchRegex(word, RegexUtils.ONLY_LETTERS_REGEX));

    return isFirstSentenceOk && isSecondSentenceOk ? AnagramResponse.builder()
        .response(anagramService.validateIfSentencesShareAnagrams(firstSentence, secondSentence))
        .build() :
        AnagramResponse.builder()
            .response("Words in sentences must have only letters and not be empty.")
            .build();
  }
}

