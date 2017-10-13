package ru.gumerbaev.piano.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

	private String[] tags;
	private Owner owner;
	@JsonProperty("is_answered")
	private boolean answered;
	@JsonProperty("view_count")
	private int viewCount;
	@JsonProperty("answer_count")
	private int answerCount;
	private int score;
	@JsonProperty("last_activity_date")
	private long lastActivityDate;
	@JsonProperty("creation_date")
	private long creationDate;
	private String link;
	private String title;

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getLastActivityDate() {
		return new Date(lastActivityDate * 1000);
	}

	public void setLastActivityDate(long lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	public Date getCreationDate() {
		return new Date(creationDate * 1000);
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
