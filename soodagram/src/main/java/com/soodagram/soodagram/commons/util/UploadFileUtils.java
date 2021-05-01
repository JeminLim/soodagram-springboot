package com.soodagram.soodagram.commons.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	
	public static final String UPLOAD_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator
											+ "dist" + File.separator + "upload" + File.separator + "images";
	
	//파일 업로드
	public static final String uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {
		
		// 1. 요청의 파일 수신
		String originFileName = file.getOriginalFilename();
		byte[] fileData = file.getBytes();
		
		// 2. 파일 중복 방지 UUID 생성
		String uuidFileName = getUUIDFileName(originFileName);
		
		// 3. 업로드 디렉토리 생성
		String datePath = getDatePath(UPLOAD_PATH);
		
		// 4. 파일 복사
		File target = new File(UPLOAD_PATH + datePath, uuidFileName); // 경로, 파일 객체
		FileCopyUtils.copy(fileData, target); // 생성된 타겟에 바이트 스트림 복사
		
		// 5. 이미지 썸네일 생성
		uuidFileName = makeThumbnailImg(UPLOAD_PATH + datePath, uuidFileName);
		
		// 6. 파일 저장 경로 치환
		return replaceSavedFilePath(UPLOAD_PATH + datePath, uuidFileName);
		
	}
	
	public static void deleteFile(String fileName) {
		// 원본 이미지 파일 삭제
		String originalImg = fileName.substring(0, 12) + fileName.substring(14);
		new File(UPLOAD_PATH + originalImg.replace('/', File.separatorChar)).delete();
		
		// 썸네일 이미지 삭제
		new File(UPLOAD_PATH + fileName.replace('/', File.separatorChar)).delete();
	}
	
	public static HttpHeaders getHttpHeaders(String fileName) throws Exception {
		MediaType mediaType = MediaUtils.getMediaType(fileName);
		HttpHeaders httpHeaders = new HttpHeaders();
		
		httpHeaders.setContentType(mediaType);
		return httpHeaders;
		
	}
	
	// UUID  파일명 생성
	private static String getUUIDFileName(String originFileName) {
		return UUID.randomUUID().toString() + "_" + originFileName;
	}
	
	// 날짜 경로 생성 ( 파일의 축적이 많을 수록 탐색하는데 걸리는 시간이 늘어나기 때문에, 속도 문제로 인하여 폴더를 날짜별로 세분화 하여 업로드 관리 )
	public static String getDatePath(String uploadPath) {
		Calendar calendar = Calendar.getInstance();
		// file.separator 는 운영체제마다 / 또는 \ 가 다르기 때문에, 운영체제마다 유연하게 대응하기 위해서 사용
		String yearPath = File.separator + calendar.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1 );
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));
		
		makeDateDir(uploadPath + datePath);
		
		return datePath;
		
	}
	
	// 날짜별 폴더 생성
	private static void makeDateDir(String uploadDatePath) {
		
		File targetDir = new File(uploadDatePath);
		
		// 이미 존재하면 메서드 종료
		if(targetDir.exists())
			return;
		
		if(targetDir.mkdirs()) {
			logger.info("Directories created");
		}
	}
	
	private static String makeThumbnailImg(String uploadPath, String fileName) throws Exception {		
		// 원본 이미지를 메모리에 로딩
		BufferedImage originalImg = ImageIO.read(new File(uploadPath, fileName));		
		// 원본 이미지 축소
		BufferedImage thumbnailImg = Scalr.resize(originalImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, 510 );		
		// 썸네일 파일명
		String thumbnailImgName = "s_" + fileName;
		// 썸네일 업로드 경로
		String fullPath  = uploadPath + File.separator + thumbnailImgName;
		// 썸네일 파일 객체 생성
		File newFile = new File(fullPath);
		// 썸네일 파일 확장자 추출
		String formatName = MediaUtils.getFormatName(fileName);
		// 썸네일 파일 저장
		ImageIO.write(thumbnailImg, formatName, newFile);
		
		return thumbnailImgName;
	}
	
	private static String replaceSavedFilePath(String datePath, String fileName) {
		String savedFilePath = datePath + File.separator + fileName;
		return savedFilePath.replace(File.separatorChar, '/');
	}
	
}
