package co.com.avvillas.anagrams.api;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentencesOccurrenceResult {

  private boolean existsOccurrences;
  private long occurrencesCount;
  private List<String> sentencesReceived;

}
