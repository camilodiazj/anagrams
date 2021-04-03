package co.com.avvillas.anagrams.repository.service;

import java.util.List;
import java.util.Optional;

public interface ISentenceRepositoryService {

  Optional<List<String>> findAllEnable();

  void save(String sentence);

  void deleteAll();

}
