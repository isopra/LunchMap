package jp.co.isopra.lunchmap.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.isopra.lunchmap.entity.Member;
import jp.co.isopra.lunchmap.service.MemberRegistrationService;

public class MemberRegistrationControllerTest {

	@Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    private MockMvc mockMvc;

	// Autowiredしている変数をMock化
    @Mock
    private MemberRegistrationService service;

    // テスト対象のクラス
    @InjectMocks
    private MemberRegistrationController controller;

    /**
     * テスト実行前の準備
     */
    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    /**
     * /member/register のコントローラの正常系テスト
     * @throws Exception
     */
	@Test
	public void test_registerMember_ok() throws Exception {

		// Mockの設定
        when(service.registerMember(any())).thenAnswer(new Answer<Member>() {
            public Member answer(InvocationOnMock invocation) {
            	// registerMemberメソッドが呼ばれたら引数で受け取った値をそのまま返却するように設定
            	return (Member)invocation.getArguments()[0];
            }
        });

        // テストの実行と結果確認
        this.mockMvc.perform(MockMvcRequestBuilders.post("/member/register")
        		.param("login_id", "hoge").param("password", "fuga").param("nickname", "piyo"))
            .andDo(MockMvcResultHandlers.print()) // コンソールに詳細を出力
            .andExpect(MockMvcResultMatchers.status().isOk()) // HTTPステータスの確認
            .andExpect(MockMvcResultMatchers.model().hasNoErrors()) // エラー情報がなにもないことを確認
            .andExpect(MockMvcResultMatchers.view().name("login")) // 遷移先名の確認
            .andExpect(MockMvcResultMatchers.model().attribute("message", "登録しました")); // Modelにセットされた値の確認
	}

}
