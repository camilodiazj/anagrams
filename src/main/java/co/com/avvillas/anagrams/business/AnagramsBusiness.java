package co.com.avvillas.anagrams.business;

import co.com.avvillas.anagrams.api.AnagramRequest;
import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.repository.dto.SentenceDTO;
import co.com.avvillas.anagrams.repository.service.ISentenceService;
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
  private final ISentenceService sentenceService;
  private static final String SRT_SPACE = " ";
  private static final String INVALID_SENTENCE_MESSAGE =
      "Words in sentences must have only letters and Sentence must not be empty.";

  public AnagramsBusiness(
      IAnagramService anagramService,
      ISentenceService sentenceService) {
    this.anagramService = anagramService;
    this.sentenceService = sentenceService;
  }

  public AnagramResponse validateIfWordsAreAnagrams(AnagramRequest request) {
    String firstWord = request.getFirst();
    String secondWord = request.getSecond();
    boolean areInputOk = isValidWord(firstWord) && isValidWord(secondWord);

    return areInputOk ? new AnagramResponse(anagramService.areWordsAnagrams(firstWord, secondWord))
        : new AnagramResponse("Words may have only letters and not be empty.");
  }

  public AnagramResponse validateIfSentencesShareAnagrams(AnagramRequest request) {
    String firstSentence = request.getFirst();
    String secondSentence = request.getSecond();

    return isSentenceValid(firstSentence) && isSentenceValid(secondSentence) ?
        new AnagramResponse(
            anagramService.validateIfSentencesShareAnagrams(firstSentence, secondSentence))
        : new AnagramResponse(INVALID_SENTENCE_MESSAGE);
  }

  public AnagramResponse validateIfPersistedSentencesShareAnagrams() {
    List<SentenceDTO> sentences = getPersistedSentences();
    if (sentences.size() == 3) {
      List<String> sentencesList = sentences.stream()
          .map(SentenceDTO::getSentence)
          .collect(Collectors.toList());
      sentenceService.deleteAll();

      return new AnagramResponse(anagramService
          .validateIfSentencesShareAnagrams(
              sentencesList.get(0),
              sentencesList.get(1),
              sentencesList.get(2)));
    } else {
      return new AnagramResponse("Not enough sentences to validate, please send more sentences");
    }
  }

  public AnagramResponse saveSentence(String sentence) {
    List<SentenceDTO> persistedSentences = getPersistedSentences();

    if (persistedSentences.size() >= 3) {
      return new AnagramResponse("Three sentences saved, please verify result.");
    } else if (sentenceExistsInDb(sentence, persistedSentences)) {
      return new AnagramResponse("Sentence is already saved in db.");
    } else if (!isSentenceValid(sentence)) {
      return new AnagramResponse(INVALID_SENTENCE_MESSAGE);
    } else {
      sentenceService.save(new SentenceDTO(sentence));
      return new AnagramResponse("Saved!");
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

  private boolean sentenceExistsInDb(String sentence, List<SentenceDTO> sentenceList) {
    return sentenceList.stream()
        .anyMatch(sentenceDTO -> sentenceDTO.getSentence().equals(sentence));
  }

  private List<SentenceDTO> getPersistedSentences() {
    return sentenceService.findAll().orElse(new ArrayList<>());
  }

}

