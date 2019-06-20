package jp.co.isopra.lunchmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.isopra.lunchmap.entity.AccountDetails;
import jp.co.isopra.lunchmap.repositories.ImageRepository;
import jp.co.isopra.lunchmap.repositories.ShopRepository;


@Controller
public class ImageController {

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ShopRepository ShopRepository;

//	// ファイル名指定
//    private String getExtension(String filename) {
//        int dot = filename.lastIndexOf(".");
//        if (dot > 0) {
//          return filename.substring(dot).toLowerCase();
//        }
//        return "";
//      }
//
//      private String getUploadFileName(String fileName) {
//
//          return fileName + "_" +
//                  DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
//                      .format(LocalDateTime.now())
//                  + getExtension(fileName);
//      }
//
//    // フォルダの作成
//	private void createDirectory() {
//       Path path = Paths.get("images/");
//        if (!Files.exists(path)) {
//          try {
//            Files.createDirectory(path);
//          } catch (Exception e) {
//            //エラー処理は省略
//          }
//        }
//	 }
//
//	/* 保存場所の指定 */
//    private void savefile(MultipartFile file) {
//        String filename = getUploadFileName(file.getOriginalFilename());
//        Path uploadfile = Paths.get("images/" + filename);
//
//        try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
//	      byte[] bytes = file.getBytes();
//	      os.write(bytes);
//        } catch (IIOException e) {
//          // エラー処理は省略
//        } catch (IOException e1) {
//        	// TODO 自動生成された catch ブロック
//			e1.printStackTrace();
//		}
//    }
//
//	// ファイルを保存
//	private void savefiles(List<MultipartFile> multipartFiles)
//	{
//	      createDirectory();
//	      for (MultipartFile file : multipartFiles)
//	      {
//	          savefile(file);
//	      }
//	 }

	// 画像登録画面
	@RequestMapping(value= "create/image", method = RequestMethod.GET)
    ModelAndView uploadview(
    		ModelAndView mav,
    		@RequestParam String place_id,
    		@RequestParam String place_name)
	{

//	      mav.addObject("form", new UploadForm());
		mav.setViewName("image");
	    mav.addObject("place_id", place_id);
	    mav.addObject("place_name", place_name);
	    return mav;
	}

	// 登録処理 ToDo:繰り返し
	@RequestMapping(value="create/image/register", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	String upload(
			ModelAndView mav,
			@RequestParam (value = "upfile") MultipartFile[] upfile,
//			@RequestParam List<MultipartFile> upfile,
//			@RequestParam String place_id,
//			@RequestParam String place_name,
			@AuthenticationPrincipal AccountDetails accountDetails
			)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		

//		System.out.println(place_id);
//		System.out.println(place_name);
//		System.out.println(upfile.getFile());
		for (MultipartFile file : upfile)
		{
			System.out.println(file.getOriginalFilename());
		}

//		Image entity = new Image();
//		Date nowDate = new Date();
//		Shop entityShop;
//
//		//Shop TableにPlace_idがない場合は登録
//		if(this.ShopRepository.existsById(place_id)) {
//			entityShop = this.ShopRepository.findById(place_id).get();
//		} else {
//			entityShop = new Shop();
//
//			entityShop.setPlace_id(place_id);
//			entityShop.setPlace_name(place_name);
//
//			ShopRepository.save(entityShop);
//		}
//
//		// image Tableにinsert
//		entity.setMember(accountDetails.getMember());
//		entity.setLogin_id(accountDetails.getMember().getLogin_id());
//		entity.setPlace_id(place_id);
//		entity.setCreated_time(nowDate);
//		entity.setShop(entityShop);
//		imageRepository.save(entity);
//
//		savefiles(form.getFile());
//
		return "image";
	}

	// 戻るボタン
	@RequestMapping(value = "menu/image/return", method = RequestMethod.POST)
		public String previous(
		@RequestParam String place_id,
		@AuthenticationPrincipal AccountDetails accountDetails,
		ModelAndView mav)
	{
		return "redirect:/shopinfo/" + place_id;

	}
}

