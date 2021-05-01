/**
 * Control account.jsp file
 */

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
	