package co.com.avvillas.anagrams.business;

import co.com.avvillas.anagrams.api.AnagramRequest;
import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.repository.dto.SentenceDTO;
import co.com.avvillas.anagrams.repository.service.ISentenceRepositoryService;
import co.com.avvillas.anagrams.service.definitions.IAnagramService;
import co.com.avvillas.anagrams.util.RegexUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
    String firstSentence = request.getFirst();
    String secondSentence = request.getSecond();

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
    List<SentenceDTO> sentences = getPersistedSentences();
    if (sentences.size() == 3) {
      List<String> sentencesList = sentences.stream()
          .map(SentenceDTO::getSentence)
          .collect(Collectors.toList());
      sentenceRepositoryService.deleteAll();

      return
          AnagramResponse.builder()
              .response(anagramService.validateIfSentencesShareAnagrams(
                  sentencesList.get(0),
                  sentencesList.get(1),
                  sentencesList.get(2)))
              .build();
    } else {
      return AnagramResponse.builder()
          .errorMessage("Not enough sentences to validate, please send more sentences")
          .build();
    }
  }

  public AnagramResponse saveSentence(String sentence) {
    List<SentenceDTO> persistedSentences = getPersistedSentences();

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
      sentenceRepositoryService.save(new SentenceDTO(sentence));
      return AnagramResponse.builder()
          .response("Saved!")
          .build();
    }

  }

  private boolean isValidWord(String word) {
    return StringUtils.isNotBlank(word) && RegexUtils
        .wordMatchRegex(word.trim(), RegexUtils.ONLY_LETTERS_REGEX);
  }

  private boolean isSentenceValid(String sentence) {
    return StringUtils.isNotBlank(sentence) && Arrays
        .stream(sentence.split(SRT_SPACE))
        .allMatch(word -> RegexUtils.wordMatchRegex(word, RegexUtils.ONLY_LETTERS_REGEX));
  }

  private boolean isTheSentenceAlreadyInDb(String sentence, List<SentenceDTO> sentenceList) {
    return sentenceList.stream()
        .anyMatch(sentenceDTO -> sentenceDTO.getSentence().equals(sentence));
  }

  private List<SentenceDTO> getPersistedSentences() {
    return sentenceRepositoryService.findAll().orElse(new ArrayList<>());
  }

}

