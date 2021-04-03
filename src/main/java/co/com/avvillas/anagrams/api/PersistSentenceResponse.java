package co.com.avvillas.anagrams.api;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersistSentenceResponse {

  private boolean saved;
  private List<String> sentencesSaved;
}
