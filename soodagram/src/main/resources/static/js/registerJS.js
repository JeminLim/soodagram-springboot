/**
 * Control register.jsp file
 */

// 각 입력들 toggle 전역변수
var checkEmail = false;
var checkId = false;
var checkName = false;
var checkPw = false;

// Email 입력 체크 - ajax 통신으로 중복 여부 확인
$(document).ready(function(){
	$("#userEmail").on("change", function() {
	
		if($(this).val().length == 0){
			checkEmail = false;
			return;
		}
		
		var url = "/user/regist/check/email";
		
		$.ajax({
			url: url,
			dataType: 'text',
			data: $("#userEmail").serialize(),
			type: "POST",
			async: false,
			success : function(result) {
				if(result > 0) {
					alert("중복된 Email 입니다");
					checkEmail = false;
					btnActivate();			
				} else if(result == 0){	
					checkEmail = true;		
					btnActivate();
				} else {
					alert("Error");
				}		
			  }
		});
	});
});

// id 입력 체크 - ajax 통신으로 중복 여부 확인
$(document).ready(function(){
	$("#userId").on("change", function() {
		
		if($(this).val().length == 0){
			checkId = false;
			return;
		}	
		
		var url = "/user/regist/check/id";
		
		$.ajax({
			url: url,
			dataType: 'text',
			data: $("#userId").serialize(),
			type: "POST",
			async: false,
			success : function(result) {
				if(result > 0) {
					alert("중복된 Id 입니다");
					checkId = false;
					btnActivate();			
				} else if(result == 0){	
					checkId = true;		
					btnActivate();
				} else {
					alert("Error");
				}			
			  }
		});
		
	});

});

// Name 입력 체크
$("#userName").change(function(){
	inputCheckName();
	btnActivate();
});

// Password 입력 체크
$("#userPw").change(function(){
	inputCheckPw();
	btnActivate();
});

function inputCheckName() {
	
	var input = $("#userName").val();
	
	if(input.length > 0)
		checkName = true;
	else
		checkName = false;
	
	btnActivate();
}

function inputCheckPw() {
	
	var input = $("#userPw").val();
	
	if(input.length > 0)
		checkPw = true;
	else
		checkPw = false;
	
	btnActivate();
}


function btnActivate() {	
	if(checkEmail && checkId && checkPw && checkName)
		$("#submitBtn").prop("disabled", false);
	else
		$("#submitBtn").prop("disabled", true);
}
