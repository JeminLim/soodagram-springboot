/**
 * Control account.jsp file
 */

 // 버튼 클릭시 삭제
 $(document).on("click", ".delBtn", function(e) {
    	e.preventDefault();
    	var that = $(this);
    	deleteFileWrtPage(that);	
 }); 

$(document).ready(function() {
   $("#writeForm").submit(function (e) {
	   e.preventDefault();
	   var that = $(this);
	   filesSubmit(that);
   });
});	   



 // 프로필 이미지 클릭
 $(document).ready(function() {
 	$("#profileImg").click(function(e) {
 		e.preventDefault();	
 		$("#profileImgUpload").click();
 	});
 });
		 

// 프로필 등록시 ajax 통신
$(document).ready(function() {
 	$("#profileImgUpload").on("change", function(e) {
 		e.preventDefault();
 		var imgSrc = $("#profileImgUpload")[0].files[0];	
 		var formData = new FormData();
 		formData.append("file", imgSrc);
 		$.ajax({
 			url: "/profile/img",
 			data: formData,
 			dataType: "text",
 			processData: false,
 			contentType: false,
 			type: "POST",
 			success: function(result) {				
 				$("#profileImg").attr("src", "/resources/dist/upload/media/" + result);
 			}
 		});
 	});		
 });
		
 $(".followBtnAcc").click(function(event){
	var target = event.target;
	var targetEmail = target.getAttribute("id").substr(10);
	
	$.ajax({
		url: "/relation/follow",
		data: {targetEmail: targetEmail},
		type: "post",
		success: function(result) {
			if(result === 1) {
				document.getElementById('followBtn_' + targetEmail).innerText = "팔로잉";
				document.getElementById('followBtn_' + targetEmail).className = "followBtnAcc btn btn-default";
			} else {
				document.getElementById('followBtn_' + targetEmail).innerText = "팔로우";
				document.getElementById('followBtn_' + targetEmail).className = "followBtnAcc btn btn-primary";
			}
			
		}    			
	});
});
	    
 $(".feedDelBtn").click(function(event) {
	 var target = event.target;
	 var feedNo = target.getAttribute("id").substr(7);
	 			 
	 $.ajax({
		 url: "/feed/" + feedNo,
		 data: {feedNo : feedNo},
		 type: "DELETE",
		 success: function(result) {
			 location.reload();
		 }				 
	 });
	 
 });
		 
		
// 피드 작성 modal 컨트롤
$("#postBtn").click(function(e){
	e.preventDefault();
	$("#feedModal").modal("show");
});

// 팔로우 modal 컨트롤
$("#followBtn").click(function(e){
  	e.preventDefault();
  	$("#followModal").modal("show");
});

 // 팔로워 modal 컨트롤
 $("#followerBtn").click(function(e){
  	e.preventDefault();
 	 $("#followerModal").modal("show");
});

$(".closeBtn").click(function(e){
  	e.preventDefault();
 	 $("#feedModal").modal("hide");
 	 $("#followModal").modal("hide");
 	 $("#followerModal").modal("hide");
});

$(".closeIcon").click(function(e){
 	 e.preventDefault();
 	 $("#feedModal").modal("hide");
 	 $("#followModal").modal("hide");
 	 $("#followerModal").modal("hide");
});
	
	
// Handlerbars 파일 템플릿 컴파일
var fileTemplate = Handlebars.compile($("#imgTemplate").html());

// 파일 선택 완료시
$(document).ready(function() {
	$("#imgUpload").change(function(e){
		e.preventDefault();
		var files = $("#imgUpload").get(0).files;
		
		for (var i = 0; i < files.length; i++) {
			var file = files[i];
			var formData = new FormData();
			formData.append("file", file);
			uploadFile(formData);		
		}
		
	});
});

// 파일 업로드 Ajax 통신
function uploadFile(formData) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url: "/feed/post/img",
		data: formData,		
		dataType: "text",
		//processData: 데이터를 일반적인 query string으로 변환 처리 여부
		// true일 경우 application/x-www-form-urlencoded 타입
		processData: false,
		// contentType: processData의 기본타입과 동일
		contentType: false,
		type: "POST",
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			printFiles(data);
		},
		error: function(request, status, error) {	
			cnosole.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
		}
	});
}

// 첨부파일 출력
function printFiles(data) {
	// 파일 정보
	var fileInfo = getFileInfo(data);
	// 템플릿 파일 정보 바인딩 및 html 생성
	var html = fileTemplate(fileInfo);
	// html을 DOM에 주입
	$(".fileDiv").append(html);
}

// 게시글 입력/ 수정 submit 처리시에 첨부파일 정보도 함께 처리
function filesSubmit(that) {		
	var str = "";
	$(".delBtn").each(function(index){
		str += "<input type='hidden' name='files[" + index + "]' value='" + $(this).attr("href") + "'>"
	});
	that.append(str);
	that.get(0).submit();	
}


// 파일 삭제(입력페이지) : 첨부파일만 삭제처리
function deleteFileWrtPage(that) {
	var url = "/feed/post/img";
	deleteFile(url, that);	
}

// 파일 삭제 AJAX 통신
function deleteFile(url, that) {
	$.ajax({
		url: url,
		type: "DELETE",
		data: {fileName: that.attr("href")},
		dataType: "text",
		success: function(result) {
			if(result ==="DELETED"){
				alert("삭제되었습니다");
				that.parents("li").remove();
			}
		}
	});	
}

// 파일 정보
function getFileInfo(fullName){	
	var fileName; // 파일명
	var imgSrc; // 썸네일 파일 요청 URL
	var getLink; // 원본파일 요청 URL
	var fileLink; // 날짜 제외 파일명
	
	imgSrc = "/feed/thumbnail?fileName=" + fullName;
	fileLink = fullName.substr(14);
	var datePath = fullName.substr(0,12);
	getLink = "/feed/thumbnail?fileName=" + datePath + fileLink;
	
	fileName = fileLink.substr(fileLink.indexOf("_") + 1);
	
	return { fileName : fileName,
			 imgSrc : imgSrc,
			 getLnk : getLink,
			 fullName : fullName}
}



