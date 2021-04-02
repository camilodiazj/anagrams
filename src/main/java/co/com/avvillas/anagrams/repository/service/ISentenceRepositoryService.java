package co.com.avvillas.anagrams.repository.service;

import co.com.avvillas.anagrams.repository.dto.SentenceDTO;
import java.util.List;
import java.util.Optional;

public interface ISentenceRepositoryService {

  Optional<List<SentenceDTO>> findAllEnable();

  void save(SentenceDTO sentence);

  void deleteAll();

}
