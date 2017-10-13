package ru.gumerbaev.piano.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gumerbaev.piano.model.Items;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/**
 * Implementation of {@link StackExchangeService}.
 *
 * @author agumerbaev
 */
@Service
public class StackExchangeServiceImpl implements StackExchangeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StackExchangeServiceImpl.class);

	@Value("${piano.searchLink}")
	private String searchLink;

	@Override
	public Items search(int page, String query) throws IOException {
		String link = String.format(searchLink, page, query);
		LOGGER.debug("API link: " + link);

		String data = loadData(link);
		LOGGER.info("Data successfully loaded");

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(data, Items.class);
	}

	private String loadData(String link) throws IOException {
		URL url = new URL(link);
		URLConnection con = url.openConnection();

		InputStream stream = con.getInputStream();
		if ("gzip".equals(con.getContentEncoding())) {
			LOGGER.debug("URL in GZIP encoding");
			stream = new GZIPInputStream(stream);
		}

		return IOUtils.toString(stream, StandardCharsets.UTF_8);
	}
}
