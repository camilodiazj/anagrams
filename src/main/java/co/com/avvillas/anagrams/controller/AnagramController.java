package co.com.avvillas.anagrams.controller;

import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.business.AnagramsBusiness;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("anagrams")
public class AnagramController {

  private final AnagramsBusiness anagramService;

  public AnagramController(
      AnagramsBusiness anagramService) {
    this.anagramService = anagramService;
  }

  @GetMapping("/words/validate")
  public ResponseEntity<AnagramResponse> validateIfWordsAreAnagrams(
      @RequestParam(value = "first-word") String firstWord,
      @RequestParam(value = "second-word") String secondWord) {

    return ResponseEntity.ok(anagramService.validateIfWordsAreAnagrams(firstWord, secondWord));
  }

  @GetMapping("/sentences/validate")
  public ResponseEntity<AnagramResponse> validateIfSentencesShareAnagrams(
      @RequestParam(value = "first-sentence") String firstSentence,
      @RequestParam(value = "second-sentence") String secondSentence) {
    return ResponseEntity
        .ok(anagramService.validateIfSentencesShareAnagrams(firstSentence, secondSentence));
  }
}
