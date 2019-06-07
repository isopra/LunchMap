package jp.co.isopra.lunchmap.controller;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.isopra.lunchmap.repositories.FootprintRepository;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.service.ImageService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopinfoControllerTest {

	@Mock
	ImageRepository imageRepository;

	@Mock
	FootprintRepository footprintRepository;

	@Mock
	ImageService imageService;

	@Rule
	public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

	private MockMvc mockMvc;

	@InjectMocks
	private ShopinfoController controller;

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
	}

	@Test
	public void test_shopinfo_show_ok() throws Exception {

//		doReturn(999).when(footprintRepository).getFootprintRecords(anyString());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/shopinfo/test"))//.param("place_id", "test"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().hasNoErrors())
			.andExpect(MockMvcResultMatchers.model().attributeExists("footprintRecords"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("footprintDatalist"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("imageRecords"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("imageDatalist"))
			.andExpect(MockMvcResultMatchers.view().name("shopinfo"));
	}

//	@Test
//	public void test_updateFootprint_show_ok() throws Exception {
//
//		String place_id = "test";
//		Long image_id = (long) 1;
//
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/shopinfo/update"))
//			.andDo(MockMvcResultHandlers.print())
//			.andExpect(MockMvcResultMatchers.status().isOk())
//			.andExpect(MockMvcResultMatchers.model().hasNoErrors())
//			.andExpect(MockMvcResultMatchers.view().name(""))
//			.andExpect(MockMvcResultMatchers.model().attribute("place_id", "hello world"));
//	}
//
//	@Test
//	public void test_deleteImage_show_ok() throws Exception {
//
//
////		doReturn().when(imageService).deleteImage(anyString(),anyLong());
////		when(service.deleteImage(place_id, image_id)).thenReturn(999);
//
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/shopinfo/delete/test/1"))
//			.andDo(MockMvcResultHandlers.print())
//			.andExpect(MockMvcResultMatchers.status().isOk())
//			.andExpect(MockMvcResultMatchers.model().hasNoErrors())
//			.andExpect(MockMvcResultMatchers.view().name("shopinfo"));
//	}


}
