package com.soodagram.soodagram.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.soodagram.soodagram.commons.util.UploadFileUtils;
import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.dto.FeedDTO;
import com.soodagram.soodagram.service.FeedService;

/**
 * 피드 컨트롤러
 * 피드 작성 관련 컨트롤러
 * @author jeminLim
 * @version 1.0
 */

@Controller
@RequestMapping("/feed")
public class FeedController{

	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	private final FeedService feedService;
	
	
	@Autowired
	public FeedController(FeedService feedService) {
		this.feedService = feedService;
	}		
	
	/**
	 * 피드 등록
	 * @param feedVO
	 * @param request
	 * @return redirect profile page
	 * @throws Exception
	 */
	@PostMapping(value="/post")
	public String uploadFeed(@ModelAttribute("feedDTO") FeedDTO feedDTO, HttpServletRequest request) throws Exception {
		// 작성자(로그인) 유저 정보
		HttpSession httpSession = request.getSession();
		Account loginUser = (Account) httpSession.getAttribute("login");				
		feedDTO.setWriter(loginUser);
		
		// 피드 컨텐츠 등록
		feedService.writeFeed(feedDTO.toFeedEntity());
		
		return "redirect:/user/" + loginUser.getAccountId();
	}
	
	/**
	 * 피드 사진 업로드
	 * @param file
	 * @param request
	 * @return response entity
	 * @throws Exception
	 */
	@PostMapping(value="/post/img", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {
		
		logger.info("===============Upload File================");
		logger.info("file name = " + file.toString());
		logger.info("==========================================");
		
		ResponseEntity<String> entity = null;
		try {
			String savedFilePath = UploadFileUtils.uploadFile(file,  request);
			entity = new ResponseEntity<>(savedFilePath, HttpStatus.CREATED);
		} catch ( Exception e ) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/**
	 * 피드 작성중인 사진 삭제
	 * @param fileName
	 * @param request
	 * @return response entity
	 * @throws Exception
	 */
	@DeleteMapping("/post/img")
	@ResponseBody 
	public ResponseEntity<String> deleteFile(@RequestBody String fileName, HttpServletRequest request) throws Exception {
		ResponseEntity<String> entity = null;		
		try {
			UploadFileUtils.deleteFile(fileName);
			entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;		
	}
	
	/**
	 * 썸네일 사진
	 * @param fileName
	 * @param request
	 * @return responseEntity
	 * @throws Exception
	 */
	@GetMapping("/thumbnail")
	@ResponseBody 
	public ResponseEntity<byte[]> display(@RequestParam("fileName") String fileName, HttpServletRequest request) throws Exception {	
		
		HttpHeaders httpHeaders = UploadFileUtils.getHttpHeaders(fileName);
		ResponseEntity<byte[]> entity = null;		
		try (InputStream inputstream = new FileInputStream(fileName)) {
			entity = new ResponseEntity<>(IOUtils.toByteArray(inputstream), httpHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;		
	}	

	/**
	 * 피드 전체 삭제
	 * @param feedNo
	 * @param request
	 * @throws Exception
	 */
	@DeleteMapping("/{feedNo}")
	@ResponseBody
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public ResponseEntity<String> deleteFeed(@PathVariable("feedNo") Long feedNo, HttpServletRequest request) throws Exception {		
	
		try {
			feedService.deleteFeed(feedNo);		
			logger.info("feed - " + feedNo + "deleted");
			return new ResponseEntity<>("delete", HttpStatus.OK);
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}		
	}	
	
	@GetMapping("/feed/{page}")
	@ResponseBody
	public ResponseEntity<List<FeedDTO>> getFeed(Model model, HttpServletRequest request, @PathVariable("page") int page) throws Exception {
		// 현재 로그인 유저 정보		
		HttpSession httpSession = request.getSession();
		Account loginUser = (Account) httpSession.getAttribute("login");			
		
		List<FeedDTO> followingFeed = feedService.getFollowingFeed(loginUser, page);	
		
		return new ResponseEntity<>(followingFeed, HttpStatus.OK);
	}
}
