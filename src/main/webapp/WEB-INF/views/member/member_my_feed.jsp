<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>FACEGRAM - MY FEED</title>
		<style>
		#profile {
		    width: 816px;
		    position: relative;
		    top: 88px;
		    margin: 0 15% 100% 25%;
		}
		
		#text {
			text-align: center;
		    left: 50px;
		}
		
		#text #textBtn {
			border: 1px solid lightgray;
		    vertical-align: top;
		    width: 68px;
		    height: 35px;
		    background: none;
		}
		
		#text table {
			border: 1px solid lightgray;
			margin: auto;
    		width: 80%;
			padding: 10px;
		    color: gray;
		}
		
		#text textarea {
			border: none;
			background: none;
			resize: none;
			overflow: hidden;
			text-align: center;
		}
		
		#feed table {
			border: 1px solid lightgray;
			width: 100%;
			border-collapse: collapse;
		}
		
		#feed th, #feed td {
			border: 1px solid lightgray;
		}
		
		#feed img { 
			width: 232px; 
			height: 232px;
    		margin:20px;
    		-webkit-box-flex:1;
		}
		
		.box {
			display:-webkit-box;
   		 	-webkit-box-orient: horizontal;
   		 	-moz-box-orient: horizontal;
    	 	-webkit-box-direction: normal;
         	-ms-flex-direction: row;
          	flex-direction: row;
          	-webkit-box-align:center;
		}
		
		#profileText {
			outline: none;
		}
		
		#profileImg {
			border-radius: 50%;
			width: 110px;
    		height: 110px;
			cursor: pointer;
		}
		</style>
		
		<script>
		// 사진 누르면 모달창 띄우기
		function showContent ( feed_idx, register_id ) {
			
			 var screenW = screen.availWidth;  // 스크린 가로사이즈
			 var screenH = screen.availHeight; // 스크린 세로사이즈
			 var popW 	 = 830; // 모달창 width
			 var popH 	 = 650; // 모달창 height
			 var posL	 = ( screenW-popW ) / 2;   // 모달창left
			 var posT	 = ( screenH-popH ) / 2;   // 모달창top  
			
			 // 모달창 띄우고 해당 게시글번호를 파라미터로 보냄
			 var msg = window.open( "/feed/showcontent?feed_idx=" + feed_idx +
						 						  "&register_id=" + register_id, "detail" ,
						 								 "width=" + popW +
													   ",height=" + popH +
													   	  ",top=" + posT +
													   	 ",left=" + posL +
													   	 ",resizable=no,scrollbars=no" );
		}
		
		// 프로필 텍스트 수정
		function updateText ( val, my_id ) {
			var textarea = document.getElementById("profileText"); // textarea 내용
			
			// 버튼 value가 '수정'일때
			if ( val == '소개수정' ) {
				// 텍스트영역의 readonly 속성 해제 + 포커스
				textarea.readOnly = false;
				textarea.focus();
				
				// 수정버튼을 '완료' 버튼으로 바꿈
				document.getElementById("textBtn").value = '완료';
				return;
			}
			
			// 버튼 value가 '완료'일때
			if ( confirm("수정하시겠습니까?") ) {
				location.href = 
					"/profile/textupdate?&my_id=" + my_id + "&text=" + textarea.value.trim();
			}
		}
		
		// 프로필 사진 변경 (사진 클릭시 수행될 function)
		function updateImg ( val ) {
			alert("수정할 이미지를 선택해주세요^-^");
			
			document.all.profile_update.click();	 // 파일선택 버튼을 클릭시킴
			val = document.all.profile_update.value; // 클릭된 사진을 '찾아보기' 버튼으로 대체시킴
			
			// 변경할 사진이 선택되면
			document.getElementById("profile_update").onchange = function() {
				// 변경할 이미지명만 따옴
				var updateImgName = document.getElementById("profile_update").files[0].name;
				
				if( confirm( updateImgName + " 으로 변경 하시겠습니까?") ) {
					document.updateform.submit();
				} 
			};
			
			
		}
		</script>
		
	</head>
	<body>
	
	<!-- include View -->
	<jsp:include page="../feed/feed_index.jsp"/>
	
	<%-- 프로필 --%>
	<div id="profile">
		<div id="text">
			<table>
				<tr>
					<th style="width: 30%; height: 200px;">
						<!-- 프로필사진 -->
						<form name="updateform" method="post" action="/profile/photoupdate" enctype="multipart/form-data">
							<img id="profileImg" src="/resources/profileimages/${ myProfile.profile_image }" onclick="updateImg(this.value)">
							<input style="display:none;" id="profile_update" name="profile_update" type="file">
							
							<!-- hidden 내 아이디 -->
							<input type="hidden" name="my_id" value="${ sessionScope.memberVO.member_id }">
						</form>
					</th>
					
					<th>
						<!-- 프로필 아이디 -->
						<strong>${ sessionScope.memberVO.member_id }</strong>
					</th>
					
					<th style="width: 50%;">
						<!-- 프로필 내용 -->
						<textarea id="profileText" cols="35" rows="5" name="profile_text" readonly>${ myProfile.profile_text }</textarea>
						<input id="textBtn" name="textBtn" type="button" value="소개수정" onclick="updateText( this.value, '${ sessionScope.memberVO.member_id }')">
					</th>
				</tr>
					
				<tr style="height: 40px;">
					<td colspan="2"><strong>게시물</strong></td>
					<td><strong>친구</strong></td>
				</tr>
				
				<tr>
					<td colspan="2">${ feedCount }</td> <%-- 친구 수 --%>
					<td>${ friendsCount }</td>			<%-- 게시물 수 --%>
				</tr>
			</table>
		</div>
		
		<%-- 피드 게시물 --%>
		<div id="feed">
		<article>
			<div>
				<c:set var="i" value="0" />
				<c:set var="j" value="3" />

				<c:forEach var="mf" items="${ myFeedList }">
					<c:if test="${ i % j == 0 }">
					<div class="box">
					</c:if>	
						<%-- 게시물 1개 div --%>		
						<div>
							<a href="javascript:showContent(${ mf.feed_idx }, '${ mf.user_id }');">
							<p>
								<img class="image" src="/resources/feedimages/${ mf.feed_image }"><br>
							</p>
							</a>	
						</div>
					<c:if test="${ i % j == j - 1 }">
					</div>
					</c:if>
					
					<c:set var="i" value="${ i + 1 }" />
				</c:forEach>
			</article>
		</div>
	</div>
	</body>
</html>