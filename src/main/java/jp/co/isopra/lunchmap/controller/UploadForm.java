package jp.co.isopra.lunchmap.controller;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {
	private List<MultipartFile> file;
//	private String place_id;
//	private String place_name;


	public List<MultipartFile> getFile() {
		return file;
	}

	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}

//	public String getPlace_id() {
//		return place_id;
//	}
//
//	public void setPlaceId(String place_id) {
//		this.place_id = place_id;
//	}
//
//	public String getPlace_name() {
//		return place_name;
//	}
//
//	public void setPlaceName(String place_name) {
//		this.place_name = place_name;
//	}
}
