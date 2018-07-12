package com.ktm.twitter.repository;

import com.ktm.twitter.model.TwitterPo;
import com.ktm.twitter.model.TwitterUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterRepository extends JpaRepository<TwitterPo, Long> {

  List<TwitterPo> findByTwitterUser(TwitterUser twitterUser);

}
