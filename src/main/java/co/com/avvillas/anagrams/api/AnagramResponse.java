package co.com.avvillas.anagrams.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnagramResponse {

  @JsonProperty("Response")
  private Object response;
}
