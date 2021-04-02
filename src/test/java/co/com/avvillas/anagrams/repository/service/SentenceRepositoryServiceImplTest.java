package co.com.avvillas.anagrams.repository.service;

import co.com.avvillas.anagrams.repository.dao.ISentenceDao;
import co.com.avvillas.anagrams.repository.dto.SentenceDTO;
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

    Mockito.when(sentenceRepository.findAll())
        .thenReturn(persistedEntities);

    Optional<List<SentenceDTO>> result = repositoryService
        .findAll();
    Assert.assertTrue(result.isPresent());
    Assert.assertEquals(3, result.get().size());
  }

  @Test
  public void shouldFindAllReturnEmptyOptional() {
    Mockito.when(sentenceRepository.findAll())
        .thenReturn(new ArrayList<>());

    Optional<List<SentenceDTO>> result = repositoryService
        .findAll();
    Assert.assertFalse(result.isPresent());
  }

  @Test
  public void shouldSaveSuccessfully() {
    Mockito.when(sentenceRepository.save(Mockito.any(SentenceEntity.class)))
        .thenReturn(Mockito.any());

    repositoryService.save(new SentenceDTO("sentences "));

    Mockito.verify(sentenceRepository).save(Mockito.any(SentenceEntity.class));
  }

  @Test
  public void shouldDeleteAllSuccessfully(){
    Mockito.doNothing().when(sentenceRepository).deleteAll();
    repositoryService.deleteAll();
    Mockito.verify(sentenceRepository).deleteAll();
  }
}
