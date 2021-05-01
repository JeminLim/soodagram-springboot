/**
 *  navigation bar javascript
 */

var cursorOnList = false;

$(document).ready(function(){
	$('.dropdown-toggle').dropdown();
});


var searchUserTemplate = Handlebars.compile($('#resultUserTemplate').html());
var searchHashtagTemplate = Handlebars.compile($('#resultHashtagTemplate').html());

$("#searchBar").keyup(function(){
	search(this);
	
	if(this.value.length === 0){
		$('#searchResult').empty();
		$('#searchResult').hide();
	}
	
});

$("#searchBar").focus(function(){
	
	if(this.value.length > 0) {
		search(this);
	} 
	
});



$('html').click(function(event){
	
	if(!$(event.target).hasClass("searchInput") && !$(event.target).hasClass("searchResult")) {
		$('#searchResult').hide();
	}
		
});

function linkToHashtagFeed(obj) {	
	
	var hashtagName = $(obj).children('.hashtagNameVal').val();
	var url="/search/";		
	const encodedUrl = url + encodeURIComponent(hashtagName);
	location.href= encodedUrl;
}

function search(searchBar) {
	
	if(searchBar.value.length > 0) {
		
		var inputValue = searchBar.value;
		
		$.ajax({
			url: "/search",
			data : { keyword : inputValue },
			type : "post",
			dataType: "json",
			success: function(result) {
				
				$('#searchResult').empty();
				
				if(result.resultUser !== undefined && result.resultUser.length > 0) {						
					var uBindingData = { searchedUser : result.resultUser };
					var uHtml = searchUserTemplate(uBindingData);
					$('#searchResult').append(uHtml);
					$('#searchResult').show();
				}
				
				if(result.resultHashtag !== undefined && result.resultHashtag.length > 0) {
					var hBindingData = { searchedTag : result.resultHashtag };
					var hHtml = searchHashtagTemplate(hBindingData);
					$('#searchResult').append(hHtml);
					$('#searchResult').show();
				}
				
			}
		});		
	}
}

