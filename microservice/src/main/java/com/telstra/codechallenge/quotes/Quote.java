package com.telstra.codechallenge.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Quote {

  private String type;
  private Value value;

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Data
  public class Value {

    private Long id;
    private String quote;
  }
}
