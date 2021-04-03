package co.com.avvillas.anagrams.business;

import co.com.avvillas.anagrams.api.AnagramRequest;
import co.com.avvillas.anagrams.api.AnagramResponse;
import co.com.avvillas.anagrams.api.PersistSentenceResponse;
import co.com.avvillas.anagrams.api.SentencesOccurrenceResult;
import co.com.avvillas.anagrams.repository.service.ISentenceRepositoryService;
import co.com.avvillas.anagrams.service.definitions.IAnagramService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnagramsBusinessTest {

  private AnagramsBusiness anagramsBusiness;
  @Mock
  private IAnagramService anagramService;
  @Mock
  private ISentenceRepositoryService sentenceRepositoryService;

  @Before
  public void init() {
    anagramsBusiness = new AnagramsBusiness(anagramService, sentenceRepositoryService);
  }

  @Test
  public void shouldValidateIfWordsAreAnagramsReturnsTrue() {
    AnagramRequest anagramRequest = new AnagramRequest();
    anagramRequest.setFirst("army");
    anagramRequest.setSecond("mary");

    Mockito.when(anagramService.areWordsAnagrams(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(true);

    AnagramResponse result = anagramsBusiness
        .validateIfWordsAreAnagrams(anagramRequest);

    Assert.assertTrue((boolean) result.getResponse());
  }

  @Test
  public void shouldValidateIfWordsAreAnagramsReturnsFalse() {
    AnagramRequest anagramRequest = new AnagramRequest();
    anagramRequest.setFirst("Army");
    anagramRequest.setSecond("mary");

    Mockito.when(anagramService.areWordsAnagrams(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(false);

    AnagramResponse result = anagramsBusiness
        .validateIfWordsAreAnagrams(anagramRequest);

    Assert.assertFalse((boolean) result.getResponse());
  }

  @Test
  public void shouldValidateIfWordsAreAnagramsReturnsErrorMessageDueInvalidArgument() {
    AnagramRequest anagramRequest = new AnagramRequest();
    anagramRequest.setFirst("8");
    anagramRequest.setSecond("mary");

    AnagramResponse result = anagramsBusiness
        .validateIfWordsAreAnagrams(anagramRequest);

    Assert.assertNotNull(result.getErrorMessage());
  }

  @Test
  public void shouldValidateIfSentencesShareAnagramsReturnTrue() {
    AnagramRequest anagramRequest = new AnagramRequest();
    anagramRequest.setFirst("angela es conservadora");
    anagramRequest.setSecond("ellos alegan que ella es muy conversadora");

    Mockito.when(
        anagramService.validateIfSentencesShareAnagrams(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(SentencesOccurrenceResult.builder()
            .existsOccurrences(true)
            .build());

    AnagramResponse result = anagramsBusiness.validateIfSentencesShareAnagrams(anagramRequest);
    SentencesOccurrenceResult occurrenceResult = (SentencesOccurrenceResult) result.getResponse();
    Assert.assertTrue(occurrenceResult.isExistsOccurrences());
  }

  @Test
  public void shouldValidateIfSentencesShareAnagramsReturnTrueWithTwoOrMoreWhiteSpaces() {
    AnagramRequest anagramRequest = new AnagramRequest();
    anagramRequest.setFirst("angela        es conservadora");
    anagramRequest.setSecond("ellos alegan que ella es muy conversadora");

    Mockito.when(
        anagramService.validateIfSentencesShareAnagrams(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(SentencesOccurrenceResult.builder()
            .existsOccurrences(true)
            .build());

    AnagramResponse result = anagramsBusiness.validateIfSentencesShareAnagrams(anagramRequest);
    SentencesOccurrenceResult occurrenceResult = (SentencesOccurrenceResult) result.getResponse();
    Assert.assertTrue(occurrenceResult.isExistsOccurrences());
  }

  @Test
  public void shouldValidateIfSentencesShareAnagramsReturnFalse() {
    AnagramRequest anagramRequest = new AnagramRequest();
    anagramRequest.setFirst("Angela eS Conservadora");
    anagramRequest.setSecond("ellos alegan que ella es muy conversadora");

    Mockito.when(
        anagramService.validateIfSentencesShareAnagrams(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(SentencesOccurrenceResult.builder()
            .existsOccurrences(false)
            .build());

    AnagramResponse result = anagramsBusiness.validateIfSentencesShareAnagrams(anagramRequest);
    SentencesOccurrenceResult occurrenceResult = (SentencesOccurrenceResult) result.getResponse();
    Assert.assertFalse(occurrenceResult.isExistsOccurrences());
  }

  @Test
  public void shouldValidateIfSentencesShareAnagramsReturnErrorMessageDueInvalidArgument() {
    AnagramRequest anagramRequest = new AnagramRequest();
    anagramRequest.setFirst("angela es conserva%dora");
    anagramRequest.setSecond("ellos alegan que ella es muy conversadora");

    AnagramResponse result = anagramsBusiness.validateIfSentencesShareAnagrams(anagramRequest);
    Assert.assertNotNull(result.getErrorMessage());
  }

  @Test
  public void shouldValidateIfPersistedSentencesShareAnagramsReturnTrue() {
    List<String> sentences = Arrays.asList("sent1", "sent2", "sent3");

    Mockito.when(sentenceRepositoryService.findAllEnable())
        .thenReturn(Optional.of(sentences));
    Mockito.doNothing().when(sentenceRepositoryService).deleteAll();
    Mockito.when(anagramService
        .validateIfSentencesShareAnagrams(Mockito.anyString(), Mockito.anyString(),
            Mockito.anyString()))
        .thenReturn(SentencesOccurrenceResult.builder()
            .existsOccurrences(true)
            .build());

    AnagramResponse result = anagramsBusiness.validateIfPersistedSentencesShareAnagrams();
    SentencesOccurrenceResult occurrenceResult = (SentencesOccurrenceResult) result.getResponse();
    Assert.assertTrue(occurrenceResult.isExistsOccurrences());
  }

  @Test
  public void shouldValidateIfPersistedSentencesShareAnagramsReturnFalse() {
    List<String> sentences = Arrays.asList("sent1", "sent2", "sent3");

    Mockito.when(sentenceRepositoryService.findAllEnable())
        .thenReturn(Optional.of(sentences));
    Mockito.doNothing().when(sentenceRepositoryService).deleteAll();
    Mockito.when(anagramService
        .validateIfSentencesShareAnagrams(Mockito.anyString(), Mockito.anyString(),
            Mockito.anyString()))
        .thenReturn(SentencesOccurrenceResult.builder()
            .existsOccurrences(false)
            .build());

    AnagramResponse result = anagramsBusiness.validateIfPersistedSentencesShareAnagrams();
    SentencesOccurrenceResult occurrenceResult = (SentencesOccurrenceResult) result.getResponse();
    Assert.assertFalse(occurrenceResult.isExistsOccurrences());
  }

  @Test
  public void shouldValidateIfPersistedSentencesShareAnagramsReturnErrorMessageDueSentencesQuantity() {
    List<String> sentences = Arrays.asList("sent1", "sent2");

    Mockito.when(sentenceRepositoryService.findAllEnable())
        .thenReturn(Optional.of(sentences));

    AnagramResponse result = anagramsBusiness.validateIfPersistedSentencesShareAnagrams();
    Assert.assertNotNull(result.getErrorMessage());
  }

  @Test
  public void saveSentencesShouldSuccess() {
    List<String> sentences = Arrays.asList("the first sentence", "the second sentence");

    Mockito.when(sentenceRepositoryService.findAllEnable())
        .thenReturn(Optional.of(sentences));
    Mockito.doNothing().when(sentenceRepositoryService).save(Mockito.anyString());

    AnagramResponse result = anagramsBusiness.saveSentence("the third sentence");

    PersistSentenceResponse persistSentenceResponse = (PersistSentenceResponse) result
        .getResponse();

    Assert.assertTrue(persistSentenceResponse.isSaved());
  }

  @Test
  public void saveSentencesShouldReturnErrorMessageDueDbSentencesPersisted() {
    List<String> sentences = Arrays
        .asList("the first sentence", "the second sentence", "the third sentence");

    Mockito.when(sentenceRepositoryService.findAllEnable())
        .thenReturn(Optional.of(sentences));

    AnagramResponse result = anagramsBusiness.saveSentence("the third sentence");
    Assert.assertEquals("Three sentences saved, please verify result.", result.getErrorMessage());
  }

  @Test
  public void saveSentencesShouldReturnErrorMessageDueSentencesAlreadySaved() {
    List<String> sentences = Arrays.asList("the first sentence", "the third sentence");

    Mockito.when(sentenceRepositoryService.findAllEnable())
        .thenReturn(Optional.of(sentences));

    AnagramResponse result = anagramsBusiness.saveSentence("the third sentence");
    Assert.assertEquals("Sentence is already saved in db.", result.getErrorMessage());
  }

  @Test
  public void saveSentencesShouldReturnErrorMessageDueInvalidSentence() {
    final String INVALID_SENTENCE_MESSAGE =
        "Words in sentences must have only letters and Sentence must not be empty.";
    List<String> sentences = Arrays.asList("the first sentence", "the third sentence");

    Mockito.when(sentenceRepositoryService.findAllEnable())
        .thenReturn(Optional.of(sentences));

    AnagramResponse result = anagramsBusiness.saveSentence("");
    Assert.assertEquals(INVALID_SENTENCE_MESSAGE, result.getErrorMessage());
  }
}
