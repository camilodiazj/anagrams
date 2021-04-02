package co.com.avvillas.anagrams.services;

import co.com.avvillas.anagrams.api.SentencesOccurrenceResult;
import co.com.avvillas.anagrams.service.AnagramServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnagramServiceImplTest {

  @InjectMocks
  private AnagramServiceImpl anagramService;

  @Test
  public void shouldAreWordsAnagramsReturnFalse() {
    boolean result = anagramService.areWordsAnagrams("Mary", "army");
    Assert.assertFalse(result);
  }

  @Test
  public void shouldValidateIfSentencesShareAnagrams() {
    SentencesOccurrenceResult result = anagramService
        .validateIfSentencesShareAnagrams("angela es angela conservadora",
            "ellos alegan que ella es es muy conversadora");
    Assert.assertEquals(3, result.getOccurrencesCount());
  }

  @Test
  public void shouldValidateIfThreeSentencesShareAnagrams() {
    SentencesOccurrenceResult result = anagramService.validateIfSentencesShareAnagrams(
        "lucia viaja a roma",
        "el jugo de fresa se licua ironicamente",
        "en una frase del renacimiento encontramos amor");

    Assert.assertEquals(4, result.getOccurrencesCount());
  }

  @Test
  public void shouldValidateIfThreeSentencesShareAnagramsSecondOrder() {
    SentencesOccurrenceResult result = anagramService.validateIfSentencesShareAnagrams(
        "el jugo de fresa se licua ironicamente",
        "lucia viaja a roma",
        "en una frase del renacimiento encontramos amor");

    Assert.assertEquals(4, result.getOccurrencesCount());
  }

  @Test
  public void shouldValidateIfThreeSentencesShareAnagramsThirdOrder() {
    SentencesOccurrenceResult result = anagramService.validateIfSentencesShareAnagrams(
        "en una frase del renacimiento encontramos amor",
        "el jugo de fresa se licua ironicamente",
        "lucia viaja a roma");

    Assert.assertEquals(4, result.getOccurrencesCount());
  }

}
