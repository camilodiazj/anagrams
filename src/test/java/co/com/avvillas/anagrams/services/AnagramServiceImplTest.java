package co.com.avvillas.anagrams.services;

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
  public void shouldAreWordsAnagramsReturnTrue(){
    boolean result = anagramService.areWordsAnagrams("Mary", "army");
    Assert.assertFalse(result);
  }

}
