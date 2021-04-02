package co.com.avvillas.anagrams.repository.dao;

import co.com.avvillas.anagrams.repository.entity.SentenceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ISentenceDao extends CrudRepository<SentenceEntity, Long> {
  Iterable<SentenceEntity> findAllByEnable(boolean enable);

  @Modifying
  @Query("update SentenceEntity s set s.enable = false")
  void disableAll();
}
