package co.com.avvillas.anagrams.controller;

import co.com.avvillas.anagrams.api.AnagramRequest;
import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.api.PersistSentenceRequest;
import co.com.avvillas.anagrams.business.AnagramsBusiness;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("anagrams")
public class AnagramController {

  private final AnagramsBusiness anagramService;

  public AnagramController(
      AnagramsBusiness anagramService) {
    this.anagramService = anagramService;
  }

  @GetMapping("/validation/words")
  public ResponseEntity<AnagramResponse> validateIfWordsAreAnagrams(@RequestBody
      AnagramRequest request) {
    return ResponseEntity.ok(anagramService.validateIfWordsAreAnagrams(request));
  }

  @GetMapping("/validation/sentences")
  public ResponseEntity<AnagramResponse> validateIfSentencesShareAnagrams(@RequestBody
      AnagramRequest request) {
    return ResponseEntity
        .ok(anagramService.validateIfSentencesShareAnagrams(request));
  }

  @PostMapping("/sentences")
  public ResponseEntity<AnagramResponse> persistSentence(
      @RequestBody PersistSentenceRequest request) {
    return ResponseEntity.ok(anagramService.saveSentence(request.getSentence()));
  }

  @GetMapping("/validation/persisted-sentences")
  public ResponseEntity<AnagramResponse> validateIfPersistedSentencesShareAnagrams() {
    return ResponseEntity.ok(anagramService.validateIfPersistedSentencesShareAnagrams());
  }
}
