package jp.co.isopra.lunchmap.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.co.isopra.lunchmap.entity.Member;
import jp.co.isopra.lunchmap.repositories.MemberRepository;

public class MemberRegistrationServiceTest {

	@Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

	// Autowiredしている変数をMock化
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    // テスト対象のクラス
    @InjectMocks
    private MemberRegistrationService target;

    /**
     * テスト実行前の準備
     */
    @Before
    public void setup() {
    	// Mockの初期化
        MockitoAnnotations.initMocks(this);
    }

    /**
     * registerMemberメソッドの正常系テスト
     */
	@Test
	public void test_registerMember_ok() {
		// テスト対象メソッドのパラメータになるオブジェクトを作成
		Member entity = new Member();
		String pw = "pass";
		entity.setLogin_id("hoge");
		entity.setPassword(pw);
		entity.setNickname("fuga");
		entity.setAdmin_flag(false);

		// Mockの設定
		String encodePw = "aaaaaaaa";
//        when(memberRepository.flush(any())).thenAnswer(new Answer<Member>() {
//            public Member answer(InvocationOnMock invocation) {
//            	// saveメソッドが呼ばれたら引数で受け取った値をそのまま返却するように設定
//            	return (Member)invocation.getArguments()[0];
//            }
//        });
        // encodeメソッドが呼ばれたら固定値を返却するように設定
//        when(passwordEncoder.encode(any())).thenReturn(encodePw);
//
//        // テスト対象のメソッドの実行
//        Member resuitEntity = target.registerMember(entity);
//
//        // Mockのメソッドが指定の引数で実行されたことを確認
//        verify(memberRepository, times(1)).save(resuitEntity);
//        verify(passwordEncoder, times(1)).encode(pw);
//
//        // 値の比較
//        assertThat(resuitEntity.getLogin_id(), is(entity.getLogin_id())); // 値が変更されてないことを確認
//        assertThat(resuitEntity.getPassword(), is(encodePw)); // パスワードの値が書き換わっていることを確認
//        assertThat(resuitEntity.getNickname(), is(entity.getNickname())); // 値が変更されてないことを確認
	}

}
