//package jp.co.isopra.lunchmap.service;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//import java.io.File;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.TemporaryFolder;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//import org.mockito.quality.Strictness;
//import org.mockito.stubbing.Answer;
//
//import jp.co.isopra.lunchmap.entity.Image;
//import jp.co.isopra.lunchmap.entity.Member;
//import jp.co.isopra.lunchmap.repositories.ImageRepository;
//import jp.co.isopra.lunchmap.repositories.MemberRepository;
//import jp.co.isopra.lunchmap.repositories.ShopRepository;
//
//public class ImageServiceTest {
//
//	@Rule
//    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);
//
//	@Rule
//	public TemporaryFolder tempFolder = new TemporaryFolder();
//
//	// Autowiredしている変数をMock化
//    @Mock
//    private ImageRepository imageRepository;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @Mock
//    private ShopRepository shopRepository;
//
//    @Mock
//    private File file;
//
//    // テスト対象のクラス
//    @InjectMocks
//    private ImageService target;
//
//    /**
//     * テスト実行前の準備
//     */
//    @Before
//    public void setup() {
//    	// Mockの初期化
//        MockitoAnnotations.initMocks(this);
//    }
//
//    /**
//     * registerMemberメソッドの正常系テスト
//     */
//	@Test
//	public void test_imageDelete_ok() {
//
//		tempFolder.newFile("test_1.jpg");
//
//		String path = tempFolder.getRoot().getAbsolutePath() + "test_1.jpg";
//
//
//		file = new File(path);
//
//		// テスト対象メソッドのパラメータになるオブジェクトを作成
//		Image entity = new Image();
//		entity.setLogin_id("hoge");
//		entity.setImage_id(999);
//		entity.setPlace_id("foo");
//
//
//		// Mockの設定
//        when(memberRepository.save(any())).thenAnswer(new Answer<Member>() {
//            public Member answer(InvocationOnMock invocation) {
//            	// saveメソッドが呼ばれたら引数で受け取った値をそのまま返却するように設定
//            	return (Member)invocation.getArguments()[0];
//            }
//        });
//        // encodeメソッドが呼ばれたら固定値を返却するように設定
//        when(passwordEncoder.encode(any())).thenReturn(encodePw);
//
//        // テスト対象のメソッドの実行
//        Member resuitEntity = target.deleteImage(entity.getPlace_id(),entity.getImage_id());
//
//        // Mockのメソッドが指定の引数で実行されたことを確認
//        verify(memberRepository, times(1)).save(resuitEntity);
//        verify(passwordEncoder, times(1)).encode(pw);
//
//        // 値の比較
//        assertThat(resuitEntity.getLogin_id(), is(entity.getLogin_id())); // 値が変更されてないことを確認
//        assertThat(resuitEntity.getPassword(), is(encodePw)); // パスワードの値が書き換わっていることを確認
//        assertThat(resuitEntity.getNickname(), is(entity.getNickname())); // 値が変更されてないことを確認
//	}
//
//}
