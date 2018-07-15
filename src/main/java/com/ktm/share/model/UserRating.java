package com.ktm.share.model;

import com.dictionary.MediaType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserRating extends UserEntityInfo {
  @Id
  @Column(name = "USER_RATING_ID", nullable = false)
  private long id;
  @NotEmpty
  @Max(10)
  private int userRatingScore;

  public UserRating() {
    super();
  }

  public UserRating(String entityId, MediaType entityType, String userId, int userRatingScore) {
    super(entityId, entityType, userId);
    this.setUserRatingScore(userRatingScore);
  }

}
