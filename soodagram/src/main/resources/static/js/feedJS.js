/**
 *  javascript for feed.jsp
 */

var page = 0;
var numFeed = 4;

var feedTemplate = Handlebars.compile($('#feedTemplate').html());
var replyTemplate = Handlebars.compile($('#replyTemplate').html());

const io = new IntersectionObserver(entries => {
	if(entries.some(entry => entry.intersectionRatio >0)) {
		// 피드 받아와서 출력하기
		attachFeeds();
	}
})

attachFeeds(); // 초기 피드 로드

$('.replySubmit').click(function(e) {
	
	e.preventDefault();
	
	var target = e.target;	
		
	var feedNo = Number(target.getAttribute("id").substr(6));
	var content = document.getElementById("content_" + feedNo).value;
	var form = {
		content: content,
		feedNo: feedNo
	}
	
	var replyList = [];
	
	$.ajax({
		url: "/reply/" + feedNo,
		data : form,
		type: "POST",
		dataType: "json",
		success : function(result) {	
			replyList = result;
			var bindingData = { replyList : replyList };
			var html = replyTemplate(bindingData);
			$('#replies' + result[0].feedNo).append(html);	
			document.getElementById("content_" + result[0].feedNo).value = "";					
		}
	});	
	
});

$('.unfoldReply').click(function(e) {
	
	e.preventDefault();
	
	var target = e.target;
	var feedNo = Number(target.getAttribute("id").substr(10));
	attachReplies(feedNo, 5);
	
});

// 서버로부터 피드 받아오기
function getFeeds(curPage) {
	
	var feedList = [];
	
	$.ajax({
		url: "/feeds/" + curPage,
		data: {page : curPage},
		dataType:"json",
		type: "GET",
		async: false,
		success: function(result) {
			if(result.code === "success"){
				feedList = result.followingFeed;
			}
		},
		error: function(request, status, error) {
			alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
		}
		
	});
	
	return feedList;	
}



// 서버로부터 해당 피드 댓글 받아오는 함수
function getReplies(feedNo, loadNum) {
	
	var curPage = Number(document.getElementById('replyPage_' + feedNo).getAttribute("value"));
	
	var replyList = [];
	data = { feedNo: feedNo,
			 curPage : curPage,
			 loadNum : loadNum};
	
	$.ajax({
		url: "/reply/" + feedNo,
		data: data,    					
		dataType: "json",
		type: "GET",
		async: false,
		success: function(result) {  
			 if(result.code === "success") {    					 
			      replyList = result.replyList;
			 }
		},				
		error: function(request, status, error) {
			alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
		}
	});
	
	return replyList;
}

// 수신 된 피드를 출력하는 함수
function attachFeeds() {	
	// 피드 표시
	var feedList = getFeeds(page);
	var receivedFeed = { feedList : feedList };
	var html = feedTemplate(receivedFeed);
	$("#feedSection").before(html);
	
	
	for(var i = 0; i < feedList.length; i++) {		
		
		var loadNum = 2;
		// 댓글 로드
		attachReplies(feedList[i].feedNo, loadNum);				
	}	
	
	page = (page + 1) * numFeed;
	
}

// 수신된 댓글 출력하는 함수
function attachReplies(feedNo, loadNum) {
	
	var replyList = getReplies(feedNo, loadNum);
		
	if(replyList.length > 0){
		var replyData = {replyList: replyList};
		var html = replyTemplate(replyData);
		$('#replies' + feedNo).prepend(html);
		var updatedPage = Number(document.getElementById('replyPage_' + feedNo).getAttribute("value")) + loadNum;
		document.getElementById('replyPage_' + feedNo).setAttribute("value", updatedPage);
	}
	
}

//좋아요 갯수 수신 및 갯수 갱신
function updateLikeNo(feedNo) {
	
	$.ajax({
		url: "/feeds/like/" + feedNo,	
		dataType: "text",
		type: "GET",
		success: function(result) {  
			 $("#totalLike_" + feedNo).html("<small><strong>좋아요 " + result + "개</strong></small>");
		}
	});
	
}
