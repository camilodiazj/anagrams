package co.com.avvillas.anagrams.repository.service;

import co.com.avvillas.anagrams.repository.dao.ISentenceDao;
import co.com.avvillas.anagrams.repository.entity.SentenceEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SentenceRepositoryServiceImpl implements ISentenceRepositoryService {

  private final ISentenceDao sentenceRepository;

  public SentenceRepositoryServiceImpl(
      ISentenceDao sentenceRepository) {
    this.sentenceRepository = sentenceRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<List<String>> findAllEnable() {
    List<SentenceEntity> result = (List<SentenceEntity>) sentenceRepository
        .findAllByEnable(true);

    return result.isEmpty() ? Optional.empty() :
        Optional.of(result.stream()
            .map(SentenceEntity::getSentence)
            .collect(Collectors.toList()));
  }

  @Override
  @Transactional
  public void save(String sentence) {
    if (StringUtils.isNotBlank(sentence)) {
      sentenceRepository.save(new SentenceEntity(sentence));
    }
  }

  @Override
  @Transactional
  public void deleteAll() {
    sentenceRepository.disableAll();
  }
}
