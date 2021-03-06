package co.com.avvillas.anagrams.repository.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "sentences",schema = "anagrams")
public class SentenceEntity {

  public SentenceEntity(String sentence) {
    this.sentence = sentence;
    this.enable = true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Basic
  private String sentence;
  @Basic
  private boolean enable;
}
