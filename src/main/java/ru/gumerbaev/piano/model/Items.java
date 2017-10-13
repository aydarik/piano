package ru.gumerbaev.piano.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Items {

	private Question[] items;
	@JsonProperty("has_more")
	private boolean hasMore;

	public Question[] getItems() {
		return items;
	}

	public void setItems(Question[] items) {
		this.items = items;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
}
