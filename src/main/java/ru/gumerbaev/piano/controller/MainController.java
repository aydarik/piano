package ru.gumerbaev.piano.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gumerbaev.piano.model.Items;
import ru.gumerbaev.piano.service.StackExchangeService;

import java.io.IOException;

/**
 * Controller for user's requests processing.
 *
 * @author agumerbaev
 */
@Controller
public class MainController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	private StackExchangeService service;

	/**
	 * Injection of {@link StackExchangeService} bean.
	 * @param service will be autowired
	 */
	@Autowired
	public void setService(StackExchangeService service) {
		this.service = service;
	}

	/**
	 * Get request for main page.
	 * @param model model for UI rendering
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String result(ModelMap model) {
		model.put("page", 0);

		return "index";
	}

	/**
	 * Post request for getting data.
	 *
	 * @param model model for UI rendering
	 * @param page  current page number
	 * @param query search query
	 * @param dir   navigation direction for results
	 *              (negative: previous; 0: new search; positive: next)
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String search(ModelMap model, @RequestParam int page, @RequestParam String query, @RequestParam int dir) {
		if (dir > 0) {
			page++;
		} else if (dir < 0) {
			page--;
		} else {
			page = 1;
		}

		try {
			Items items = service.search(page, query);
			model.put("items", items);
			model.put("page", page);
		} catch (IOException e) {
			model.put("page", 0);
			model.put("error", e.getLocalizedMessage());
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			model.put("query", query);
		}

		return "index";
	}
}