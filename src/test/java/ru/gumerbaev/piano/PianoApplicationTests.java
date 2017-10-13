package ru.gumerbaev.piano;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.gumerbaev.piano.model.Items;
import ru.gumerbaev.piano.service.StackExchangeService;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PianoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StackExchangeService service;

	@Value(value = "classpath:static/search.json")
	private Resource json;


//region Prepare
//---------------------------------------------------------------------------------------

	@Before
	public void readJson() throws Exception {
		Items items = jsonMapping();
		when(service.search(anyInt(), anyString())).thenReturn(items);
	}

	private Items jsonMapping() throws Exception {
		InputStream resourceInputStream = json.getInputStream();
		String jsonString = IOUtils.toString(resourceInputStream, StandardCharsets.UTF_8);
		assertNotNull(jsonString);
		assertFalse(jsonString.isEmpty());

		ObjectMapper mapper = new ObjectMapper();
		Items items = mapper.readValue(jsonString, Items.class);
		assertNotNull(items);
		assertTrue(items.getItems().length > 0);

		return items;
	}

//---------------------------------------------------------------------------------------
//endregion


//region GET requests
//---------------------------------------------------------------------------------------

	@Test
	public void searchPage() throws Exception {
		getRequest().andExpect(content().string(CoreMatchers.containsString("searchForm")));
	}

	/**
	 * GET request without arguments. Path is predefined.
	 * @return {@link ResultActions} object
;	 */
	private ResultActions getRequest() throws Exception {
		return mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
	}

//---------------------------------------------------------------------------------------
//endregion


//region POST requests
//---------------------------------------------------------------------------------------

	@Test
	public void firstPage() throws Exception {
		postRequest(1, 0).andExpect(content().string(CoreMatchers.containsString("Answered")))
				.andExpect(content().string(IsNot.not(CoreMatchers.containsString("prevBtn"))));
	}

	@Test
	public void nextPage() throws Exception {
		postRequest(1, 1).andExpect(content().string(CoreMatchers.containsString("Answered")))
				.andExpect(content().string(CoreMatchers.containsString("prevBtn")));
	}

	@Test
	public void prevPage() throws Exception {
		postRequest(2, -1).andExpect(content().string(IsNot.not(CoreMatchers.containsString("prevBtn"))));
	}

	/**
	 * POST request for search. Path and search query are predefined.
	 * @param page current page number
	 * @param dir   navigation direction for results
	 *              (negative: previous; 0: new search; positive: next)
	 * @return {@link ResultActions} object
	 */
	private ResultActions postRequest(int page, int dir) throws Exception {
		return mockMvc.perform(
				post("/").param("page", String.valueOf(page)).param("query", "java").param("dir", String.valueOf(dir)))
				.andDo(print()).andExpect(status().isOk());
	}

//---------------------------------------------------------------------------------------
//endregion
}
