package co.com.avvillas.anagrams.service.definitions;

import co.com.avvillas.anagrams.api.SentencesOccurrenceResult;

public interface IAnagramService {

  boolean areWordsAnagrams(String firstWord, String secondWord);

  SentencesOccurrenceResult validateIfSentencesShareAnagrams(String firstSentence,
      String secondSentence);

  SentencesOccurrenceResult validateIfSentencesShareAnagrams(String firstSentence,
      String secondSentence,
      String thirdSentence);
}
