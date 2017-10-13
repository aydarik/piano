package ru.gumerbaev.piano.service;

import ru.gumerbaev.piano.model.Items;

import java.io.IOException;

/**
 * The only service for StackExchange communication.
 *
 * @author agumerbaev
 */
public interface StackExchangeService {

	/**
	 * Search for the results.
	 *
	 * @param page  current page
	 * @param query query string
	 * @return {@link Items} POJO
	 */
	Items search(int page, String query) throws IOException;
}
