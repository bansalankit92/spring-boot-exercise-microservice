package com.telstra.codechallenge.git;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GitRepoDetail {

  @JsonProperty("name")
  private String name;
  @JsonProperty("html_url")
  private String htmlUrl;
  @JsonProperty("watchers_count")
  private String watchersCount;
  @JsonProperty("language")
  private String language;
  @JsonProperty("description")
  private String desciption;

}
