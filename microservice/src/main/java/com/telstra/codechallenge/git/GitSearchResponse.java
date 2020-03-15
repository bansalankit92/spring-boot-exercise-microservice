package com.telstra.codechallenge.git;


import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GitSearchResponse {

  private List<GitRepoDetail> items = new ArrayList<>();

}
