package jp.co.isopra.lunchmap.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.isopra.lunchmap.repositories.ShopRepository;



public class MapsControllerTest {

	@Mock
	ShopRepository repository;


	@Rule
	public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

	private MockMvc mockMvc;

	@InjectMocks
	private MapsController controller;


	@Before
	public void before() {

		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
	}

	@Test
	public void test_map()  {

		try {
			this.mockMvc.perform(MockMvcRequestBuilders.get("/menu/mapview"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().hasNoErrors())
			.andExpect(MockMvcResultMatchers.view().name("map"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
