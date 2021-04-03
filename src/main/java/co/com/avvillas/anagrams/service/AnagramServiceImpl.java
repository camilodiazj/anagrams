package co.com.avvillas.anagrams.service;

import co.com.avvillas.anagrams.api.SentencesOccurrenceResult;
import co.com.avvillas.anagrams.service.definitions.IAnagramService;
import co.com.avvillas.anagrams.util.RegexUtils;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class AnagramServiceImpl implements IAnagramService {

  private static final String SRT_SPACE = " ";

  @Override
  public boolean areWordsAnagrams(String firstWord, String secondWord) {
    return orderString(firstWord).equals(orderString(secondWord));
  }

  @Override
  public SentencesOccurrenceResult validateIfSentencesShareAnagrams(String firstSentence,
      String secondSentence) {

    Set<String> occurrences = getAnagramOccurrencesFromSentences(firstSentence, secondSentence);

    return SentencesOccurrenceResult.builder()
        .existsOccurrences(occurrences.size() >= 1)
        .occurrencesCount(occurrences.size())
        .build();
  }

  @Override
  public SentencesOccurrenceResult validateIfSentencesShareAnagrams(String firstSentence,
      String secondSentence, String thirdSentence) {
    Set<String> occurrences = getAnagramOccurrencesFromSentences(firstSentence, secondSentence);
    occurrences.addAll(getAnagramOccurrencesFromSentences(firstSentence, thirdSentence));
    occurrences.addAll(getAnagramOccurrencesFromSentences(secondSentence, thirdSentence));

    return SentencesOccurrenceResult.builder()
        .existsOccurrences(occurrences.size() >= 1)
        .occurrencesCount(occurrences.size())
        .build();
  }

  private String orderString(String word) {
    return Arrays.stream(word.split(Strings.EMPTY)).sorted()
        .collect(Collectors.joining());
  }

  private Set<String> getAnagramOccurrencesFromSentences(String firstSentence,
      String secondSentence) {
    Set<String> firstWords = Arrays.stream(firstSentence.split(SRT_SPACE))
        .filter(word -> RegexUtils.wordMatchRegex(word, RegexUtils.ONLY_LETTERS_REGEX))
        .map(this::orderString)
        .collect(Collectors.toSet());

    return Arrays.stream(secondSentence.split(SRT_SPACE))
        .map(this::orderString)
        .filter(firstWords::contains)
        .collect(Collectors.toSet());
  }
}
