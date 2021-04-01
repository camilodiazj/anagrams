package co.com.avvillas.anagrams.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnagramResponse {

  @JsonProperty("Response")
  private Object response;
}
