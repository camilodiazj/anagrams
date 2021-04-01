package co.com.avvillas.anagrams.services.anagrams.definitions;

import co.com.avvillas.anagrams.api.SentencesOccurrenceResult;

public interface IAnagramService {

  /*
  * This method validate if firstWord contains the same letters as secondWord
  * Is case sensitive
   */
  boolean areWordsAnagrams(String firstWord, String secondWord);

  SentencesOccurrenceResult validateIfSentencesShareAnagrams(String firstSentence, String secondSentence);

  SentencesOccurrenceResult validateIfSentencesShareAnagrams(String firstSentence, String secondSentence,
      String thirdSentence);
}
