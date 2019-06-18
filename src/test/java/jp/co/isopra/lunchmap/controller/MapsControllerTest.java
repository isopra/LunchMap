package jp.co.isopra.lunchmap.controller;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.isopra.lunchmap.controller.MapsController.ConditionSession;
import jp.co.isopra.lunchmap.repositories.ShopRepository;



public class MapsControllerTest {

	@Mock
	ShopRepository repository;

//	@Mock
//	FootPrintRepository footrepo;

	@Mock
	ConditionSession conditionSession;
//
//	@Mock
//	Principal principal;



	@Rule
	public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

	private MockMvc mockMvc;

	@InjectMocks
	private MapsController controller;


	@Before
	public void before() {

		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
	}
//中間発表後に作成予定
//	@Test
//	@WithMockUser(
//			username = "test",
//			password = "test")
//	public void test_map()  {
//
//		try {
//			Authentication auth;
//			Authentication authentication = Mockito.mock(Authentication.class);
//			 Mockito.when(authentication.getPrincipal()).thenReturn("test");
//			this.mockMvc.perform(MockMvcRequestBuilders.get("/menu/mapView"))
//			.andDo(MockMvcResultHandlers.print())
//			.andExpect(MockMvcResultMatchers.status().isOk())
//			.andExpect(MockMvcResultMatchers.model().hasNoErrors())
//			.andExpect(MockMvcResultMatchers.view().name("map"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}


}
