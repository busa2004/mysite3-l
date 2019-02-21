<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
#dialog-delete-form p {
 padding : 10px;
 font-weight: bold;
 font-size:1.0em;
}

#dialog-delete-form input[type="password"]{
	padding:5px;
	outline:none;
	width:180px;
	
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
//jquery plug-in
(function($){
	$.fn.hello = function(){
		console.log($(this).attr("id")+"-----> hello");
	}
})(jQuery);

var no = 0;
var page = 0;
var isEnd = false;
var messageBox = function(title,message,tag){
	
	$("#dialog-message").attr("title",title);
	$("#dialog-message p").text(message);
	$("#dialog-message").dialog({
		modal:true,
		buttons:{
			"확인" : function(){
				$(this).dialog("close");
			}	
		},
		close:function(){
			console.log("close 시 뒤처리...");
			tag.focus();
		}
	});
	
	
	
};

var render = function(vo,mode){
	//템플릿 라이브러리 필요
	//ex)ejs, underscore, mustache
	var htmls = "<li data-no='"+vo.no+"'>"+
					"<strong>"+vo.name+"</strong>"+
						"<p>"+vo.message.replace(/\n/g,"<br>")+"</p>"+
					"<strong></strong>"+
					"<a href='' data-no='"+vo.no+"'>삭제</a>"+
				"</li>";	
	
	if(mode == true){
		$("#list-guestbook").prepend(htmls);
	}else{
		$("#list-guestbook").append(htmls);
	}
};
var fetchList = function(){
	if(isEnd == true){
		return;
	}
	++page;
	$.ajax({
		url:"/mysite3/guestbook/ajax/list?p="+page,
		type:"get",
		dataType:"json",
		data:"",
		success: function(response){

			if(response.result == "fail"){
				console.warn(response.data);
				return;
			}
			
			console.log(response.data);
			
			//페이지 끝을 검출
			if(response.data.length< 5){
				isEnd = true;
				$("#btn-next").prop("disabled",true);
			}
			
			//rendering
			$.each(response.data, function(index,vo){
				render(vo,false);
			});
		
		
		
		},
		error: function(xhr,status,e){
			console.error(status + ":" + e);
		}
		
	});	
};
$(function(){
	var dialogDelete=$("#dialog-delete-form").dialog({
		autoOpen:false,
		modal:true,
		buttons:{
			"삭제":function(){
				
				
				
				console.log("ajax 삭제 작업");
				
				
				
				
				$.ajax({
					url:"/mysite3/guestbook/ajax/delete?no="+no+"&password="+$("#password-delete").val(),
					type:"get",
					dataType:"json",
					data:"",
					success: function(response){

						if(response.result == "fail"){
							console.warn(response.data);
							return;
						}
						
						console.log(response.data);
						console.log(no,$("#password-delete").val())
						
						//rendering
						
						if(response.data){
							$("#list-guestbook li a[data-no="+no+"]").parent( 'li' ).remove();
							dialogDelete.dialog("close");
							return;
						}
						
						
						$("#box").css("display","block");
						$("#password-delete").focus();
					
					},
					error: function(xhr,status,e){
						console.error(status + ":" + e);
					}
					
				})
				//비밀번호가 틀렸습니다. 표시 나오게 해야함
			},
			"취소":function(){
				dialogDelete.dialog("close");
				
				
			}
		},
		close:function(){
			console.log("close 시 뒤처리...");
			$("input[type=password]").val("");
			
			$("#box").css("display","none")
		}
	});
	//live event
	$(document).on("click","#list-guestbook li a",function(event){
		event.preventDefault();
		no = $(this).data("no");
		dialogDelete.dialog("open");
	});
	
	
	$('#btn-next').click(function(){
		//최초리스트 가져오기
		$(this).hello();
		fetchList();
	});
	
	//메세지 등록 폼
	$("#add-form").submit(function(event){
		// submit의 기본동작(post)
		// 막아야 한다.
		event.preventDefault();
		// validate form data
		var name =$("#input-name").val();
		var password =$("#input-password").val();
		var message =$("#tx-content").val();
		
		if(name == ""){
			messageBox("글 남기기","이름은 필수 항목입니다",$("#input-name"));
			return;
		}
		
		if(password == ""){
			messageBox("글 남기기","비밀번호는 필수 항목입니다",$("#input-password"));
			return;
		}
		
		if(message == ""){
			messageBox("글 남기기","내용은 필수 항목입니다",$("#tx-content"));
			return;
		}
		
		$.ajax({
			url:"/mysite3/guestbook/ajax/insert?name="+name+"&password="+password+"&message="+message,
			type:"get",
			dataType:"json",
			data:"",
			success: function(response){

				if(response.result == "fail"){
					console.warn(response.data);
					return;
				}
				
				console.log(response.data);
				
				
				//rendering
				
					render(response.data,true);
				$("#input-name").val("")
				$("#input-password").val("")
				$("#tx-content").val("")
			
			},
			error: function(xhr,status,e){
				console.error(status + ":" + e);
			}
			
		});	
		
		
		
	});
	
	//스크롤 이벤트
	$(window).scroll(function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();
		
		if(scrollTop + windowHeight + 10 > documentHeight){
			fetchList();
		}
	});
	
	
	
	
	fetchList();
	fetchList();
	
	
	
	
});


</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" name="name" placeholder="이름">
					<input type="password" id="input-password" name="password" placeholder="비밀번호">
					<textarea id="tx-content" name="message" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<button id="btn-next">다음</button>
				<ul id="list-guestbook">

					
					
									
				</ul>
				
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" id="box" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
				
  				<p style="padding:30px;"></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>