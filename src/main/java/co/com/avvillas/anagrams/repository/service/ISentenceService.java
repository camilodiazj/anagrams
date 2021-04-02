package co.com.avvillas.anagrams.repository.service;

import co.com.avvillas.anagrams.repository.dto.SentenceDTO;
import java.util.List;
import java.util.Optional;

public interface ISentenceService {

  Optional<List<SentenceDTO>> findAll();

  void save(SentenceDTO sentence);

  void deleteAll();

}
