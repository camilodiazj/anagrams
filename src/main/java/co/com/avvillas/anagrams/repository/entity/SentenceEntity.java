package co.com.avvillas.anagrams.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sentences",schema = "anagrams")
public class SentenceEntity {

  @Id
  private long id;
  private String sentence;
}
