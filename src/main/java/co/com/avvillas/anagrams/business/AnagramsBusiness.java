package co.com.avvillas.anagrams.business;

import co.com.avvillas.anagrams.api.AnagramRequest;
import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.api.PersistSentenceResponse;
import co.com.avvillas.anagrams.repository.service.ISentenceRepositoryService;
import co.com.avvillas.anagrams.service.definitions.IAnagramService;
import co.com.avvillas.anagrams.util.RegexUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AnagramsBusiness {

  private final IAnagramService anagramService;
  private final ISentenceRepositoryService sentenceRepositoryService;
  private static final String SRT_SPACE = " ";
  private static final String INVALID_SENTENCE_MESSAGE =
      "Words in sentences must have only letters and Sentence must not be empty.";

  public AnagramsBusiness(
      IAnagramService anagramService,
      ISentenceRepositoryService sentenceService) {
    this.anagramService = anagramService;
    this.sentenceRepositoryService = sentenceService;
  }

  public AnagramResponse validateIfWordsAreAnagrams(AnagramRequest request) {
    String firstWord = request.getFirst();
    String secondWord = request.getSecond();
    boolean areInputOk = isValidWord(firstWord) && isValidWord(secondWord);

    return areInputOk ? AnagramResponse.builder()
        .response(anagramService.areWordsAnagrams(firstWord, secondWord))
        .build() :
        AnagramResponse.builder()
            .errorMessage("Words may have only letters and not be empty.")
            .build();
  }

  public AnagramResponse validateIfSentencesShareAnagrams(AnagramRequest request) {
    String firstSentence = cleanSentence(request.getFirst());
    String secondSentence = cleanSentence(request.getSecond());

    return isSentenceValid(firstSentence) && isSentenceValid(secondSentence) ?
        AnagramResponse.builder()
            .response(
                anagramService.validateIfSentencesShareAnagrams(firstSentence, secondSentence))
            .build() :
        AnagramResponse.builder()
            .errorMessage(INVALID_SENTENCE_MESSAGE)
            .build();
  }

  public AnagramResponse validateIfPersistedSentencesShareAnagrams() {
    List<String> sentences = getPersistedSentences();
    if (sentences.size() == 3) {
      sentenceRepositoryService.deleteAll();

      return
          AnagramResponse.builder()
              .response(anagramService.validateIfSentencesShareAnagrams(
                  sentences.get(0),
                  sentences.get(1),
                  sentences.get(2)))
              .build();
    } else {
      return AnagramResponse.builder()
          .errorMessage(sentences.size() + " sentences to validate, please send more sentences.")
          .build();
    }
  }

  public AnagramResponse saveSentence(String sentence) {
    List<String> persistedSentences = getPersistedSentences();
    sentence = cleanSentence(sentence);

    if (persistedSentences.size() >= 3) {
      return AnagramResponse.builder()
          .errorMessage("Three sentences saved, please verify result.")
          .build();
    } else if (isTheSentenceAlreadyInDb(sentence, persistedSentences)) {
      return AnagramResponse.builder()
          .errorMessage("Sentence is already saved in db.")
          .build();
    } else if (!isSentenceValid(sentence)) {
      return AnagramResponse.builder()
          .errorMessage(INVALID_SENTENCE_MESSAGE)
          .build();
    } else {
      sentenceRepositoryService.save(sentence);
      return AnagramResponse.builder()
          .response(new PersistSentenceResponse(true, getPersistedSentences()))
          .build();
    }

  }

  private String cleanSentence(String sentence) {
    return sentence.replaceAll(RegexUtils.TEXT_WITH_TWO_OR_MORE_BLANK_SPACES, SRT_SPACE);
  }

  private boolean isValidWord(String word) {
    return StringUtils.isNotBlank(word) && RegexUtils
        .wordMatchRegex(word.trim(), RegexUtils.ONLY_LETTERS_REGEX);
  }

  private boolean isSentenceValid(String sentence) {
    return StringUtils.isNotBlank(sentence) && Arrays
        .stream(sentence.split(SRT_SPACE))
        .allMatch(
            word -> RegexUtils.wordMatchRegex(word, RegexUtils.ONLY_LETTERS_AND_NUMBERS_REGEX));
  }

  private boolean isTheSentenceAlreadyInDb(String sentence, List<String> sentenceList) {
    return sentenceList.stream()
        .anyMatch(expression -> expression.equals(sentence));
  }

  private List<String> getPersistedSentences() {
    return sentenceRepositoryService.findAllEnable().orElse(new ArrayList<>());
  }

}

