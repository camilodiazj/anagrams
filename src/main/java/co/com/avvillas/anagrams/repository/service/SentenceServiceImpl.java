package co.com.avvillas.anagrams.repository.service;

import co.com.avvillas.anagrams.repository.dao.ISentenceDao;
import co.com.avvillas.anagrams.repository.dto.SentenceDTO;
import co.com.avvillas.anagrams.repository.entity.SentenceEntity;
import com.mysql.cj.util.StringUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SentenceServiceImpl implements ISentenceService {

  private final ISentenceDao sentenceRepository;

  public SentenceServiceImpl(
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
    if (sentence != null && !StringUtils.isEmptyOrWhitespaceOnly(sentence.getSentence())) {
      sentenceRepository.save(new SentenceEntity(sentence.getSentence()));
    }
  }
}
