package co.com.avvillas.anagrams.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnagramResponse {

  private Object response;
  private String errorMessage;
}
