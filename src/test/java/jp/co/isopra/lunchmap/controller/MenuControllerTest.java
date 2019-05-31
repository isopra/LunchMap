package jp.co.isopra.lunchmap.controller;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MenuControllerTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

	private MockMvc mockMvc;

	@InjectMocks
	private MenuController controller;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
	}

	@Test
	public void test_menu_show_ok() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/menu"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().hasNoErrors())
			.andExpect(MockMvcResultMatchers.view().name("menu"));
	}

}
