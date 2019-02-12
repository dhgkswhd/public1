<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>FACEGRAM - 친구요청</title>
	</head>
	<style>
		#applyBox {
			width: 700px;
		    position: relative;
		    top: 100px;
		    margin: 0 15% 100% 25%;
		    text-align: center;
		    border: 1px solid lightgray;
		}
		
		#applyBox th, #applyBox td {
			padding: 15px;
		}
		
		#applyBox input {
			border: 1px solid lightgray;
		    background: none;
		    width: 80px;
		    height: 25px;
		}
	</style>
	
	<script>
		// 친구수락버튼 눌렀을 때
		function _accept ( receiver_id, sender_id ) {
			if ( confirm("수락하시겠습니까?") ) {
				location.href = "/friendapply/accept?receiver_id=" + receiver_id +
							    			  	     "&sender_id=" + sender_id;
			}
		}
		
		// 친구거절버튼 눌렀을 때
		function refuse ( receiver_id, sender_id ) {
			if ( confirm("거절하시겠습니까?") ) {
				location.href = "/friendapply/refuse?receiver_id=" + receiver_id +
							    		  		     "&sender_id=" + sender_id;
			}
		}
	</script>
	
	<body>
		<!-- 페이지 동적 포함 (액션태그) -->
		<jsp:include page="../feed/feed_index.jsp"/>
		
		<div id="applyBox">
			<table style="width:700px; height:100px;">
				<tr>
					<th colspan="2">친구요청</th>
				</tr>
				
				<%-- 친구요청 건이 없으면 --%>
				<c:if test="${ empty applyList }">
					<td colspan="2">친구요청 건이 없습니다.</td>
				</c:if>
				
				<%-- 친구요청 건이 있으면 for문 돌림 --%>
				<c:forEach var="al" items="${ applyList }">
					<tr>
						<%-- 친구 신청한 회원 id (내아이디 아님) --%>
						<td>${ al.sender_id }</td>
						<td style="text-align: right; padding-right: 50px;">
							<%-- 파라미터로 '내 아이디(receiver_id)' + '친구신청한사람 아이디(sender_id)' --%>
							<input type="button" value="수락" onclick="_accept( '${ al.receiver_id }',
																			  '${ al.sender_id }' )">
							<input type="button" value="거절" onclick="refuse( '${ al.receiver_id }',
																			  '${ al.sender_id }' )">
						</td>					
					</tr>
				</c:forEach>
			</table>
		</div>			
	</body>
</html>