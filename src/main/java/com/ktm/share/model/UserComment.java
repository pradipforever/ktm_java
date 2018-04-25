package com.ktm.share.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.ktm.utils.MediaType;

@Entity
public class UserComment extends UserEntityInfo {
	@Id
	@Column(name="USER_COMMENT_ID", nullable=false)
	private long id;
	private String userName;
	@NotEmpty
	private String email;
	@NotEmpty
	private String comment;
	
	public UserComment(String entityId, MediaType entityType, String userId, String userName,
			String email, String comment) {
		super(entityId, entityType, userId);
		this.userName = userName;
		this.email = email;
		this.comment = comment;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
