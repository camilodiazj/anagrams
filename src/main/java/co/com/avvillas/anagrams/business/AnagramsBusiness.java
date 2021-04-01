package co.com.avvillas.anagrams.business;

import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.api.AnagramRequest;
import co.com.avvillas.anagrams.repository.dto.SentenceDTO;
import co.com.avvillas.anagrams.repository.service.ISentenceService;
import co.com.avvillas.anagrams.service.definitions.IAnagramService;
import co.com.avvillas.anagrams.util.RegexUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AnagramsBusiness {

  private final IAnagramService anagramService;
  private final ISentenceService sentenceService;

  public AnagramsBusiness(
      IAnagramService anagramService,
      ISentenceService sentenceService) {
    this.anagramService = anagramService;
    this.sentenceService = sentenceService;
  }

  public AnagramResponse validateIfWordsAreAnagrams(AnagramRequest request) {
    String firstWord = request.getFirst();
    String secondWord = request.getSecond();

    boolean areInputOk =
        RegexUtils.wordMatchRegex(firstWord.trim(), RegexUtils.ONLY_LETTERS_REGEX) && RegexUtils
            .wordMatchRegex(secondWord.trim(), RegexUtils.ONLY_LETTERS_REGEX);

    return areInputOk ? new AnagramResponse(anagramService.areWordsAnagrams(firstWord, secondWord))
        : new AnagramResponse("Words may have only letters and not be empty.");
  }

  public AnagramResponse validateIfSentencesShareAnagrams(AnagramRequest request) {
    String firstSentence = request.getFirst();
    String secondSentence = request.getSecond();

    boolean isFirstSentenceOk = Arrays
        .stream(firstSentence.split(" "))
        .allMatch(word -> RegexUtils.wordMatchRegex(word, RegexUtils.ONLY_LETTERS_REGEX));
    boolean isSecondSentenceOk = Arrays.stream(secondSentence.split(" "))
        .allMatch(word -> RegexUtils.wordMatchRegex(word, RegexUtils.ONLY_LETTERS_REGEX));

    return isFirstSentenceOk && isSecondSentenceOk ?
        new AnagramResponse(
            anagramService.validateIfSentencesShareAnagrams(firstSentence, secondSentence))
        : new AnagramResponse("Words in sentences must have only letters and not be empty.");
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
    }
    return new AnagramResponse("Not enough sentences to validate");
  }

  public AnagramResponse saveSentence(String sentence) {
    List<SentenceDTO> persistedSentences = getPersistedSentences();

    if (persistedSentences.size() >= 3) {
      return new AnagramResponse("Max number of sentences saved, please verify result.");
    } else if (sentenceExistsInDb(sentence, persistedSentences)) {
      return new AnagramResponse("Sentence is already saved in db.");
    } else {
      sentenceService.save(new SentenceDTO(sentence));
      return new AnagramResponse("Saved!");
    }

  }

  private boolean sentenceExistsInDb(String sentence, List<SentenceDTO> sentenceList) {
    return sentenceList.stream()
        .anyMatch(sentenceDTO -> sentenceDTO.getSentence().equals(sentence));
  }

  private List<SentenceDTO> getPersistedSentences() {
    return sentenceService.findAll().orElse(new ArrayList<>());
  }

}

