package co.com.avvillas.anagrams.controller;

import co.com.avvillas.anagrams.api.AnagramRequest;
import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.api.PersistSentenceRequest;
import co.com.avvillas.anagrams.api.SentencesOccurrenceResult;
import co.com.avvillas.anagrams.business.AnagramsBusiness;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class AnagramControllerTest {

  @InjectMocks
  private AnagramController anagramController;

  @Mock
  private AnagramsBusiness anagramsBusiness;

  @Test
  public void shouldValidateIfWordsAreAnagramsReturnOkStatus() {
    Mockito.when(anagramsBusiness.validateIfWordsAreAnagrams(Mockito.any(AnagramRequest.class)))
        .thenReturn(AnagramResponse.builder()
            .response(true)
            .build());

    ResponseEntity<AnagramResponse> result = anagramController
        .validateIfWordsAreAnagrams(new AnagramRequest());

    Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
  }

  @Test
  public void shouldValidateIfWordsAreAnagramsReturnBadRequestStatus() {
    Mockito.when(anagramsBusiness.validateIfWordsAreAnagrams(Mockito.any(AnagramRequest.class)))
        .thenReturn(AnagramResponse.builder()
            .errorMessage("fail")
            .build());

    ResponseEntity<AnagramResponse> result = anagramController
        .validateIfWordsAreAnagrams(new AnagramRequest());

    Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
  }

  @Test
  public void shouldValidateIfSentencesShareAnagramsSuccess() {
    Mockito
        .when(anagramsBusiness.validateIfSentencesShareAnagrams(Mockito.any(AnagramRequest.class)))
        .thenReturn(AnagramResponse.builder()
            .response(SentencesOccurrenceResult.builder().build())
            .build());

    ResponseEntity<AnagramResponse> result = anagramController
        .validateIfSentencesShareAnagrams(new AnagramRequest());

    Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
  }

  @Test
  public void shouldValidateIfSentencesShareAnagramsBadRequest() {
    Mockito
        .when(anagramsBusiness.validateIfSentencesShareAnagrams(Mockito.any(AnagramRequest.class)))
        .thenReturn(AnagramResponse.builder()
            .errorMessage("fail")
            .build());

    ResponseEntity<AnagramResponse> result = anagramController
        .validateIfSentencesShareAnagrams(new AnagramRequest());

    Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
  }

  @Test
  public void shouldPersistSentenceSuccess() {
    Mockito.when(anagramsBusiness.saveSentence(Mockito.anyString()))
        .thenReturn(AnagramResponse.builder()
            .response("Saved")
            .build());

    PersistSentenceRequest persistSentenceRequest = new PersistSentenceRequest();
    persistSentenceRequest.setSentence("sentence");
    ResponseEntity<AnagramResponse> result = anagramController
        .persistSentence(persistSentenceRequest);

    Assert.assertEquals(HttpStatus.CREATED.value(), result.getStatusCode().value());
  }

  @Test
  public void shouldPersistSentence() {
    Mockito.when(anagramsBusiness.saveSentence(Mockito.anyString()))
        .thenReturn(AnagramResponse.builder()
            .errorMessage("fail")
            .build());

    PersistSentenceRequest persistSentenceRequest = new PersistSentenceRequest();
    persistSentenceRequest.setSentence("sentence");
    ResponseEntity<AnagramResponse> result = anagramController
        .persistSentence(persistSentenceRequest);

    Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
  }

  @Test
  public void shouldValidateIfPersistedSentencesShareAnagramsSuccess() {
    Mockito.when(anagramsBusiness.validateIfPersistedSentencesShareAnagrams())
        .thenReturn(AnagramResponse.builder()
            .response(new Object())
            .build());

    ResponseEntity<AnagramResponse> result = anagramController
        .validateIfPersistedSentencesShareAnagrams();
    Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
  }

  @Test
  public void shouldValidateIfPersistedSentencesShareAnagramsFail() {
    Mockito.when(anagramsBusiness.validateIfPersistedSentencesShareAnagrams())
        .thenReturn(AnagramResponse.builder()
            .errorMessage("fail")
            .build());

    ResponseEntity<AnagramResponse> result = anagramController
        .validateIfPersistedSentencesShareAnagrams();
    Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
  }
}
