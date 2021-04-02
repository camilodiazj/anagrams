package co.com.avvillas.anagrams.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnagramResponse {

  private Object response;
  private String errorMessage;
}
