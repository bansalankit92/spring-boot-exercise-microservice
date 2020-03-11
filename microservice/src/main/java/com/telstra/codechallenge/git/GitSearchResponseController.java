package com.telstra.codechallenge.git;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/git")
public class GitSearchResponseController {

  public static final int NO_WEEK_DAYS = 7;
  private final GitSearchResponseService gitSearchResponseService;

  @Autowired
  public GitSearchResponseController(
      GitSearchResponseService gitSearchResponseService) {
    this.gitSearchResponseService = gitSearchResponseService;
  }

  @GetMapping("/hottest/{noOfRepos}")
  public ResponseEntity<GitSearchResponse> getHottestRepos(@PathVariable int noOfRepos) {
    return ResponseEntity.ok(gitSearchResponseService.getHottestRepos(noOfRepos, NO_WEEK_DAYS));
  }
}
