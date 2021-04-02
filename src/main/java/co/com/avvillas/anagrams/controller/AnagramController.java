package co.com.avvillas.anagrams.controller;

import co.com.avvillas.anagrams.api.AnagramRequest;
import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.api.PersistSentenceRequest;
import co.com.avvillas.anagrams.business.AnagramsBusiness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("anagrams")
public class AnagramController {

  private final AnagramsBusiness anagramsBusiness;

  public AnagramController(
      AnagramsBusiness anagramService) {
    this.anagramsBusiness = anagramService;
  }

  @GetMapping("/validation/words")
  public ResponseEntity<AnagramResponse> validateIfWordsAreAnagrams(@RequestBody
      AnagramRequest request) {
    AnagramResponse response = anagramsBusiness.validateIfWordsAreAnagrams(request);

    return isSuccessfulResponse(response) ? ResponseEntity.ok(response)
        : ResponseEntity.badRequest().body(response);
  }

  @GetMapping("/validation/sentences")
  public ResponseEntity<AnagramResponse> validateIfSentencesShareAnagrams(@RequestBody
      AnagramRequest request) {
    AnagramResponse response = anagramsBusiness.validateIfSentencesShareAnagrams(request);

    return isSuccessfulResponse(response) ? ResponseEntity.ok(response)
        : ResponseEntity.badRequest().body(response);
  }

  @PostMapping("/sentences")
  public ResponseEntity<AnagramResponse> persistSentence(
      @RequestBody PersistSentenceRequest request) {
    AnagramResponse response = anagramsBusiness.saveSentence(request.getSentence());

    return isSuccessfulResponse(response) ? new ResponseEntity<>(response, HttpStatus.CREATED)
        : ResponseEntity.badRequest().body(response);
  }

  @GetMapping("/validation/persisted-sentences")
  public ResponseEntity<AnagramResponse> validateIfPersistedSentencesShareAnagrams() {
    AnagramResponse response = anagramsBusiness.validateIfPersistedSentencesShareAnagrams();

    return isSuccessfulResponse(response) ? ResponseEntity.ok(response)
        : ResponseEntity.badRequest().body(response);
  }

  private boolean isSuccessfulResponse(AnagramResponse anagramResponse) {
    return anagramResponse.getResponse() != null;
  }
}
