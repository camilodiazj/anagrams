package co.com.avvillas.anagrams.services.anagrams;

import co.com.avvillas.anagrams.api.SentencesOccurrenceResult;
import co.com.avvillas.anagrams.services.anagrams.definitions.IAnagramService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class IAnagramServiceImpl implements IAnagramService {

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
    long coincidences = Arrays.stream(secondSentence.split(SRT_SPACE))
        .map(this::orderString)
        .filter(firstWords::contains)
        .count();
    return SentencesOccurrenceResult.builder()
        .existsOccurrences(coincidences > 1)
        .occurrencesCount(coincidences)
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
