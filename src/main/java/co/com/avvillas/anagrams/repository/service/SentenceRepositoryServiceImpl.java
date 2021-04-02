package co.com.avvillas.anagrams.repository.service;

import co.com.avvillas.anagrams.repository.dao.ISentenceDao;
import co.com.avvillas.anagrams.repository.dto.SentenceDTO;
import co.com.avvillas.anagrams.repository.entity.SentenceEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SentenceRepositoryServiceImpl implements ISentenceRepositoryService {

  private final ISentenceDao sentenceRepository;

  public SentenceRepositoryServiceImpl(
      ISentenceDao sentenceRepository) {
    this.sentenceRepository = sentenceRepository;
  }

  @Override
  public Optional<List<SentenceDTO>> findAll() {
    List<SentenceEntity> result = (List<SentenceEntity>) sentenceRepository
        .findAll();

    return result.isEmpty() ? Optional.empty() :
        Optional.of(result.stream()
            .map(sentenceEntity -> new SentenceDTO(sentenceEntity.getSentence()))
            .collect(Collectors.toList()));
  }

  @Override
  public void save(SentenceDTO sentence) {
    if (sentence != null && StringUtils.isNotBlank(sentence.getSentence())) {
      sentenceRepository.save(new SentenceEntity(sentence.getSentence()));
    }
  }

  @Override
  public void deleteAll() {
    sentenceRepository.deleteAll();
  }
}
