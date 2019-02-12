<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html>
<html>
	<head>
	
	<title>FACEGRAM - MESSAGE</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/custom.css">
	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	
	<style>
		.nav-counter {
			top: 23px;
			left: 10px;
			min-width: 8px;
			height: 25px;
			line-height: 20px;
			margin-top: 11px;
			padding: 0 6px;
			font-weight: normal;
			font-size: small;
			color: white;
			text-align: center;
			text-shadow: 0 1px rgba(0, 0, 0, 0.2);
			background: #e23442;
			border: 1px solid #911f28;
			border-radius: 11px;
			background-image: -webkit-linear-gradient(top, #e8616c, #dd202f);
			background-image: -moz-linear-gradient(top, #e8616c, #dd202f);
			background-image: -o-linear-gradient(top, #e8616c, #dd202f);
			background-image: linear-gradient(to bottom, #e8616c, #dd202f);
			-webkit-box-shadow: inset 0 0 1px 1px rgba(255, 255, 255, 0.1), 0 1px
				rgba(0, 0, 0, 0.12);
			box-shadow: inset 0 0 1px 1px rgba(255, 255, 255, 0.1), 0 1px
				rgba(0, 0, 0, 0.12);
		}
	</style>
	
	</head>
	<body>
		<br>
		<%-- 주고받은 메세지가 없으면 --%>
		<c:if test="${ empty lastmsgInfo }">
			<h2>메세지가 없습니다.</h2>
		</c:if>
		
		<%-- 주고받은 메세지가 있으면 --%>
		
		<c:if test="${ !empty lastmsgInfo }">
			<c:forEach var="lm" items="${ lastmsgInfo }" varStatus="status">
				<br>
				<div>
					<!-- 친구 프로필사진 -->
					<div style="float: left; width: 20%; text-indent: 2em; margin-left: 2%;">
							<img class="media-object img-circle" width="50" height="50"
								src="/resources/profileimages/${ lm.profile_image }">
					</div>
					
					<div style="float: left; width: 50%;">
						<!-- 해당친구와의 대화목록 보기 -->
						<c:if test="${ sessionScope.memberVO.member_id eq lm.friend_id }">
							<a href="javascript:window.open('/message/main?my_id=${ sessionScope.memberVO.member_id }&friend_id=${ lm.my_id }',
																'?','width=800,height=650,left=500,top=100');">
							${ lm.my_id }
							</a><br>
						</c:if>
						
						<!-- 해당친구와의 대화목록 보기 -->
						<c:if test="${ sessionScope.memberVO.member_id ne lm.friend_id }">
							<a href="javascript:window.open('/message/main?my_id=${ sessionScope.memberVO.member_id }&friend_id=${ lm.friend_id }',
																'?','width=800,height=650,left=500,top=100');">
							${ lm.friend_id }
							</a><br>
						</c:if>
						
						<!-- 마지막 메세지 내용-->
						${ lm.msg_text }
					</div>
					
					<!-- 안읽은 쪽지수 -->
					<c:if test="${ countlist[status.index] ne 0 }">
						<div class="nav-counter" style="float: Left">
							${ countlist[status.index] }
						</div>
					</c:if>
				</div>
				<br>
				<hr>
			</c:forEach>
		</c:if>
	</body>
</html>