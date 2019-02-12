<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>FACEGRAM - 게시글상세보기</title>
	</head>
	<style>
	#conetentBox {
		display: -webkit-box;
		-webkit-box-orient: horizontal;
		-webkit-box-direction: normal;
		flex-direction: row;
	}
	
	#comments {
		overflow-y: scroll;
		height: 400px;
		width: 300px;
		border-bottom: 1px solid lightgray;
    	margin: 10px;
	}
	
	#register {
		height: 50px;
	    border-bottom: 1px solid lightgrey;
	    background-color: white;
	    margin: 10px;
	    font-size: 19px;
	}
	
	#registerContent {
		margin: 20px;
	}
	
	#registerContent textarea {
	    background: none;
	    border: none;
	    resize: none;
	    font-family: Noto Sans KR;
	    vertical-align: top;
        width: 250px;
        outline: none;
	}
	
	::-webkit-scrollbar {
		display: none;
	}
	
	#profileImg {
		width: 40px; 
		height: 40px;
		object-fit: cover;
 		border-radius: 50%;
 		vertical-align: middle;
  		position: relative;
 		margin-right: 12px;
	}
	
	#text {
		width: 220px;
		margin: 7px; 
		border: 1px solid lightgray;
		resize: none;
		outline: none;
		vertical-align: middle;
	}
		
	#regiBtn {
		cursor: pointer;
	    border: 1px solid lightgray;
	    background: none;
	    color: lightsalmon;
	    font-size: 14px;
	}
	
	#updel {
		 text-align: center;
		 margin-top: 15px;"
	}
	
	#updel input {
		border: 1px solid lightgray;
	    background: none;
	    width: 60px;
	    height: 30px;
	}
	</style>
	<script>
	
	// 댓글 or 답글작성
	function regi ( f ) {
		var text 		  = f.text.value; 		   // 작성한 댓글 내용
		var feed_idx	  = f.feed_idx.value;	   // 해당 글(부모글) 번호
		var my_id 	      = f.my_id.value;		   // 작성자(나) 아이디
		var regiBtn  	  = f.regiBtn.value;
		
		if ( text == "" ) {
			alert("댓글을 입력하세요.");
			f.text.focus();
			return;
		}
		
		f.submit();
	}
	
	// 답글 버튼 클릭했을 때
	function reply ( id ,comments_refNum ) {
		var temp = document.getElementById("text");
		
		// textarea에 @ id 형식으로 넣어줌
		temp.value = "@" + id + " ";
		
		// '등록' 버튼을 '답글달기' 버튼으로 변경
		document.getElementById("regiBtn").value = "답글달기";
		
		// id가 comments_refNum인 hidden의 value값을 -> 부모댓글번호로 바꾼다. 
		document.getElementById("comments_refNum").value = comments_refNum;
		
	}
	
	// 게시물 삭제
	function del( feed_idx ) {
		if ( confirm("정말 삭제하시겠습니까?") ) {
			opener.location.href = "/feed/delete?feed_idx=" + feed_idx;
			self.close();
		}		
	}
	
	// 게시물 수정
	function update( val, feed_idx ) {
		var textarea = document.getElementById("contentText");
		
		// '수정' 버튼일 때
		if ( val == '수정' ) {
			 textarea.readOnly = false;
			 textarea.focus();
			 
			 document.getElementById("updateBtn").value = '완료';
			 return;
		}
		
		// '완료' 버튼일 때
		if ( confirm("수정하시겠습니까?") ) {
				location.href = 
					"/feed/update?&feed_idx=" + feed_idx + "&text=" + textarea.value.trim();
			}	
	}
	</script>
	
	<body>
		<div id="conetentBox">
		
		<%-- 게시물 이미지 --%>
		<div id="contentImage">
			<img style="width: 400px;" src="/resources/feedimages/${ vo.feed_image }"><br>
			
			<%-- 작성자가 '나' 면 수정, 삭제 버튼 추가 --%>
			<c:if test="${ sessionScope.memberVO.member_id eq vo.user_id }">
				<div id="updel">
					<input type="button" value="삭제" id="delBtn" name="delBtn" onclick="del(${ vo.feed_idx })">
					<input type="button" value="수정" id="updateBtn" name="updateBtn" onclick="update(this.value, ${ vo.feed_idx })">
				</div>
			</c:if>	
		</div>
		
		<article>
			<%-- 프로필사진 + 작성자 ID --%>
			<header id="register">
				<img id="profileImg" src="/resources/profileimages/${ vo.friend_profileImg }"/>   <!-- 프로필사진 -->
				<strong style="color:lightsalmon;">${ vo.user_id }</strong>						 <!-- 아이디 -->
			</header>
			
				<%-- 작성자 ID + 내용 --%>
				<div id="registerContent">
					<strong>${ vo.user_id } </strong> 
					<textarea id="contentText" readonly>${ vo.feed_text }</textarea>
				</div>
				
				<%-- 댓글 + 답글 list --%>
				<div id="comments">
					<ul style="list-style: none; padding-left: 20px;">
						<c:forEach var="com" items="${ commentsListAll }">
							<li>
								<%-- 댓글(0) 이면 그냥 출력 --%>
								<c:if test="${ com.comments_div eq 0 }">
									<div>
										<%-- 댓글 작성자 프로필사진 --%>
										<img style="border-radius: 50%; width: 20px; height: 20px;" src="/resources/profileimages/${ com.profile_image }">
											
										<%-- 댓글 작성자 ID --%>
										<strong style="font-size: 14px;">${ com.user_id }</strong>
											
										<%-- 댓글 내용 + 마우스오버시 작성일시 --%>
										<a style="font-size: 12px;" title="${ com.comments_regidate }">${ com.comments_text }</a>
										
										<%-- 답글 등록 --%>
										<a style="cursor: pointer">
										<img style="width: 15px; height: 15px;" src="/resources/cursor.jpg" id="replyRegiBtn" onclick="javascript:reply('${ com.user_id }', ${ com.comments_idx })">
										</a><br>
									</div>
								</c:if>
								
								<%-- 답글(1) 이면 일단 숨김 --%>
								<c:if test="${ com.comments_div eq 1 }">
									<div style="text-indent: 25px;">
									<%-- 답글 작성자 프로필사진 --%>
									<img style="border-radius: 50%; width: 20px; height: 20px;" src="/resources/profileimages/${ com.profile_image }">
										
									<%-- 답글 작성자 ID --%>
									<strong style="font-size: 13px;">${ com.user_id }</strong>
									
									<%-- 답글 내용 + 마우스오버시 작성일시 --%>
									<a style="font-size: 11px;" title="${ com.comments_regidate }">${ com.comments_text }</a>
									<br>
									</div>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</div>
				
				<section>
					<div style="margin: 10px;">
						<form action="/comment/regi">
							<!-- 게시글 번호 hidden -->
							<input type="hidden" name="feed_idx" value=${ vo.feed_idx }>
							
							<!-- 내 아이디 hidden -->
							<input type="hidden" name="my_id" value='${ sessionScope.memberVO.member_id }'>
							 
							<!-- 메인창인지 모달창인지 구분해줄 값 hidden -->
							<input type="hidden" name="where_url" value="modal">
							
							<!-- 댓글 번호 hidden 초기값 "" / 답글달기버튼으로 바뀌면 value값을 댓글번호로 바꿈 -->
							<input type="hidden" id="comments_refNum" name="comments_refNum" value="0">
							
							<div>
							<textarea id="text" name="text" placeholder="댓글 입력"></textarea>
							<input id="regiBtn" type="button" value="등록" onclick="regi(this.form)">
							</div>
						</form>
					</div>
				</section>
		</article>
	</div>
	</body>
</html>