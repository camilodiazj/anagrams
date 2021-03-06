package co.com.avvillas.anagrams.repository.service;

import co.com.avvillas.anagrams.repository.dao.ISentenceDao;
import co.com.avvillas.anagrams.repository.entity.SentenceEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SentenceRepositoryServiceImplTest {

  @InjectMocks
  private SentenceRepositoryServiceImpl repositoryService;

  @Mock
  private ISentenceDao sentenceRepository;

  @Test
  public void shouldFindAllGetAvailableSentences() {
    Iterable<SentenceEntity> persistedEntities = Arrays
        .asList(new SentenceEntity("sentence one"), new SentenceEntity("sentence two"),
            new SentenceEntity("sentence three"));

    Mockito.when(sentenceRepository.findAllByEnable(true))
        .thenReturn(persistedEntities);

    Optional<List<String>> result = repositoryService
        .findAllEnable();
    Assert.assertTrue(result.isPresent());
    Assert.assertEquals(3, result.get().size());
  }

  @Test
  public void shouldFindAllReturnEmptyOptional() {
    Mockito.when(sentenceRepository.findAllByEnable(true))
        .thenReturn(new ArrayList<>());

    Optional<List<String>> result = repositoryService
        .findAllEnable();
    Assert.assertFalse(result.isPresent());
  }

  @Test
  public void shouldSaveSuccessfully() {
    Mockito.when(sentenceRepository.save(Mockito.any(SentenceEntity.class)))
        .thenReturn(Mockito.any());

    repositoryService.save("sentences ");

    Mockito.verify(sentenceRepository).save(Mockito.any(SentenceEntity.class));
  }

  @Test
  public void shouldDeleteAllSuccessfully() {
    Mockito.doNothing().when(sentenceRepository).disableAll();
    repositoryService.deleteAll();
    Mockito.verify(sentenceRepository).disableAll();
  }
}
