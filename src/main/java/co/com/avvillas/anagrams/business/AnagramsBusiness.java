package co.com.avvillas.anagrams.business;

import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.repository.dto.SentenceDTO;
import co.com.avvillas.anagrams.repository.service.ISentenceService;
import co.com.avvillas.anagrams.service.definitions.IAnagramService;
import co.com.avvillas.anagrams.util.RegexUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

  public AnagramResponse validateIfWordsAreAnagrams(String firstWord, String secondWord) {

    boolean areInputOk =
        RegexUtils.wordMatchRegex(firstWord.trim(), RegexUtils.ONLY_LETTERS_REGEX) && RegexUtils
            .wordMatchRegex(secondWord.trim(), RegexUtils.ONLY_LETTERS_REGEX);

    return areInputOk ? new AnagramResponse(anagramService.areWordsAnagrams(firstWord, secondWord))
        : new AnagramResponse("Words may have only letters and not be empty.");
  }

  public AnagramResponse validateIfSentencesShareAnagrams(String firstSentence,
      String secondSentence) {
    //TODO: Revisar si es necesario (ignorar números individuales)
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

  public AnagramResponse saveSentence(String sentence) {
    List<SentenceDTO> sentences = sentenceService.findAll().orElse(null);

    if (sentences == null || sentences.size() < 3) {
      sentenceService.save(new SentenceDTO(sentence));
      return new AnagramResponse("Sentence saved");
    } else {
      return new AnagramResponse("Max number of sentences saved, please verify result.");
    }
  }

}

