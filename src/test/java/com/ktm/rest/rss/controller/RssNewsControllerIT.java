package com.ktm.rest.rss.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.ktm.rest.rss.model.RssNews;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RssNewsControllerIT {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void fetchInternationalRssFeed_verifyDetails() {
    ResponseEntity<RssNews[]> responseEntity =
        restTemplate.getForEntity("/news/international", RssNews[].class);
    assertRssNewsResponse(responseEntity);
  }

  @Test
  public void fetchNationalRssFeed_verifyDetails() {
    ResponseEntity<RssNews[]> responseEntity =
        restTemplate.getForEntity("/news/national", RssNews[].class);
    assertRssNewsResponse(responseEntity);
  }

  public void assertRssNewsResponse(ResponseEntity<RssNews[]> responseEntity) {
    List<RssNews> rssNewsList = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assert (!rssNewsList.isEmpty());
    assert (StringUtils.isNotEmpty(rssNewsList.stream().findFirst().get().getDescription()));
    assert (StringUtils.isNotEmpty(rssNewsList.stream().findFirst().get().getUri()));
  }
}