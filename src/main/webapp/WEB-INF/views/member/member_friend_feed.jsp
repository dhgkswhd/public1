<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>FACEGRAM - FRIEND FEED</title>
	</head>
	
	<style>
		#profile {
			width: 730px;
		    position: relative;
		    top: 88px;
		    margin: 0 15% 100% 25%;
		}
		
		#text {
		    text-align: center;
		    left: 50px;
		}
		
		#text table {
			border: 1px solid lightgray;
			padding: 10px;
			width: 100%;
			color: gray;
		}
		
		#text textarea {
			border: none;
			background: none;
			resize: none;
			overflow: hidden;
			text-align: center;
		}
		
		#text img {
			width: 110px;
    		height: 110px;
		    border-radius: 50%;
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
		</style>
		
		<!-- AJAX -->
		<script src="/js/httpRequest.js"></script>
		
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
		
		// 친구요청
		function apply ( my_id, user_id, status ) {
			
			// 친구요청 또는 친구요청취소 클릭 시
			if ( status == "친구요청" ) {
				if ( confirm("친구 요청 하시겠습니까?") ) {}
			} else {
				if ( confirm("친구 요청을 취소 하시겠습니까?") ) {}
			}
			
			var url   = "/friendapply/apply";
			var param = "my_id=" + my_id + 
						"&user_id=" + user_id + 
						"&status=" + status;	
			
			sendRequest( url, param, apply_callback, "POST" );
		}
		
		// 콜백함수
		function apply_callback() {
			console.log( xhr.readyState + " / " + xhr.status );
		
			if ( xhr.readyState == 4 && xhr.status == 200 ) {
				var msg = xhr.responseText;
				
				if ( msg == '친구요청취소' ) {
					alert("친구요청이 완료되었습니다.");
					document.getElementById("name").value = msg;	
				} else {
					alert("친구요청을 취소하였습니다.");
					document.getElementById("name").value = msg;
				} 
				 
			}
		}
		</script>
	
	<body>
	
	<!-- include View -->
	<jsp:include page="../feed/feed_index.jsp"/>
	
	<div id="profile">
		<div id="text" >
			<table>
				<tr>
					<th style="width: 30%; height: 200px;">
						<!-- 프로필사진 -->
						<img src="/resources/profileimages/${ friendProfile.profile_image }">
					</th>
					
					<th>
						<!-- 프로필 아이디 -->
						<strong>${ friendProfile.member_id }</strong>
					</th>
					
					<th style="width: 50%;">
						<!-- 프로필 내용 -->
						<textarea rows="4" name="profile_text" readonly>${ friendProfile.profile_text }</textarea>
					</th>
					
					<th rowspan="2">
						<td colspan="3" rowspan="3">
							<c:choose>
								<%-- 이미 친구면 --%>
								<c:when test="${ applyStatus eq -1 }">
									<p style="color: coral;">FRIEND</p>
								</c:when>
								
								<%-- 친구요청 한 적이 있으면 --%>
								<%-- 파라미터 : '내아이디' + '친구아이디' + 'value값'--%>
								<c:when test="${ applyStatus eq 1 }">
									<input style="border: 1px solid lightgray; height: 30px; id="name" name="apply" type="button" value="친구요청취소" 
														      onclick="apply( '${ sessionScope.memberVO.member_id }',
																			  '${ friendProfile.member_id }',
																			   this.value )">
								</c:when>
								
								<%-- 친구요청 한 적이 없으면 --%>
								<%-- 파라미터 : '내아이디' + '친구아이디' + 'value값'--%>
								<c:otherwise>
									<input style="border: 1px solid lightgray; height: 30px;" id="name" name="apply" type="button" value="친구요청" 
														      onclick="apply( '${ sessionScope.memberVO.member_id }',
																			  '${ friendProfile.member_id }',
																			   this.value )">								
								</c:otherwise>
							</c:choose>
							
							<%-- 채팅버튼 누르면 채팅창 열림 --%>
							<input style="border: 1px solid lightgray; height: 30px;" type="button" value="채팅하기" 
														      onclick="javascript:window.open(
														      '/message/main?my_id=${ sessionScope.memberVO.member_id }&friend_id=${ friendProfile.member_id }',
														      '?','width=500,height=500,left=400,top=50');">
						</td>
					</th>
				
				<tr style="height: 40px;">
					<td colspan="2"><strong>게시물</strong></td>
					<td colspan="2"><strong>친구</strong></td>
				</tr>
				
				<tr>
					<td colspan="2">${ feedCount }</td>
					<td colspan="2">${ friendsCount }</td>
				</tr>
			</table>
		</div>
		
		<%-- 피드 게시물 --%>
		<div id="feed">
		<article>
			<div>
				<c:set var="i" value="0" />
				<c:set var="j" value="3" />

				<c:forEach var="ff" items="${ friendFeed }">
					<c:if test="${ i % j  == 0 }">
					<div class="box">
					</c:if>			
						<%-- 게시물 1개 div --%>
						<div>
							<a href="javascript:showContent(${ ff.feed_idx }, '${ ff.user_id }');">
							<p>
								<img class="image" src="/resources/feedimages/${ ff.feed_image }"><br>
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