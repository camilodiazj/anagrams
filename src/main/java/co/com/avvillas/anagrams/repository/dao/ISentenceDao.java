package co.com.avvillas.anagrams.repository.dao;

import co.com.avvillas.anagrams.repository.entity.SentenceEntity;
import org.springframework.data.repository.CrudRepository;

public interface ISentenceDao extends CrudRepository<SentenceEntity, Long> {

}
