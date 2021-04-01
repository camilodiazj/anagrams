package co.com.avvillas.anagrams.service;

import co.com.avvillas.anagrams.api.SentencesOccurrenceResult;
import co.com.avvillas.anagrams.service.definitions.IAnagramService;
import java.util.Arrays;
import java.util.List;
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
    List<String> firstWords = Arrays.stream(firstSentence.split(SRT_SPACE))
        .map(this::orderString)
        .collect(Collectors.toList());
    Set<String> occurrences = Arrays.stream(secondSentence.split(SRT_SPACE))
        .map(this::orderString)
        .filter(firstWords::contains)
        .collect(Collectors.toSet());

    return SentencesOccurrenceResult.builder()
        .existsOccurrences(occurrences.size() > 1)
        .occurrencesCount(occurrences.size())
        .build();
  }

  @Override
  public SentencesOccurrenceResult validateIfSentencesShareAnagrams(String firstSentence,
      String secondSentence, String thirdSentence) {
    long counter = validateIfSentencesShareAnagrams(firstSentence, secondSentence)
        .getOccurrencesCount();
    counter += validateIfSentencesShareAnagrams(firstSentence, thirdSentence).getOccurrencesCount();
    counter += validateIfSentencesShareAnagrams(firstSentence, thirdSentence).getOccurrencesCount();



    return SentencesOccurrenceResult.builder()
        .existsOccurrences(counter > 1)
        .occurrencesCount(counter)
        .build();
  }

  private String orderString(String word) {
    return Arrays.stream(word.split(Strings.EMPTY)).sorted()
        .collect(Collectors.joining());
  }
}
