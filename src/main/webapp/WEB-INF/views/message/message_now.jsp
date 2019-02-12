<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<%
	Date today = new Date();
	SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
	date.format(today);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
		<title>FACEGRAM - MESSAGE LIST</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" href="/resources/css/bootstrap.css">
		<link rel="stylesheet" href="/resources/css/custom.css">
		<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
		<script src="js/bootstrap.js"></script>
	
		<script type="text/javascript">
		
		// 메세지 전송
		function sendMessage( f ){
			var msg_text = f.msg_text.value.trim(); // 메세지 내용
			
			if( msg_text == "" ) {
				alert("메세지를 입력하세요.");
				return;
			}
			
			f.submit();
		}
		
		</script>
	</head>
	
	<body onUnload="closePage();">
	
	<div class="container">
		<div class="container bootstrap snippet">
			<div class="row">
				<div class="col-xs-12">
					<div class="portlet portlet-default">
						<div class="portlet-heading">
							<div class="portlet-title">
								<h4>
									<i class="fa fa-circle text-white">실시간 채팅방</i>
								</h4>
							</div>
							<div class="clearfix"></div>
						</div>

						<div id="chat" class="panel-cpllapse collapse in">
							<div id="chatList" class="portlet-body chat-widget"
								style="overflow-y: auto; width: auto; height:600px">
								<div class="row">
									<div class="col-lg-12">
										<p class="text-center text-muted small"></p>
									</div>
								</div>
								
							<%-- 이전메세지 for문으로 출력 --%>
							<c:forEach var="ml" items="${ msg }">
								<div class="row">
									<div class="col-lg-12">
										<div class="media">
											
											<%-- 내 메세지는 오른쪽 --%>
											<c:if test="${ sessionScope.memberVO.member_id eq ml.my_id }">
												
												<a class="pull-right">
												<%-- 내 프로필사진 --%>
												<img class="media-object img-circle" width="45" height="45" src="/resources/profileimages/${ ml.profile_image }">
												
												<%-- 내 아이디 --%>
												<h4>${ ml.my_id }</h4>
												</a>	
													
												<%-- 보낸시간 --%>
												<div class="media-body">
													<h4 class="media-heading">
														<span class="small pull-right">
															${ ml.msg_regidate }
														</span>
													</h4>
												</div>
													
												<%-- 내 메세지 내용 --%>
												<span class="small pull-right">
													<h5>${ ml.msg_text }</h5>
												</span>
											</c:if>
											
											<%-- 상대방 메세지는 왼쪽 --%>
											<c:if test="${ sessionScope.memberVO.member_id ne ml.my_id }">
												<a class="pull-left">
												<%-- 상대방 프로필사진 --%>
												<img class="media-object img-circle" width="50" height="50" src="/resources/profileimages/${ ml.profile_image }">
												
												<%-- 상대방 아이디 --%>
												<h4>${ ml.my_id }</h4>
												</a>
												
												<%-- 상대방이 보낸 시간 --%>
												<div class="media-body">
													<h4 class="media-heading">
														<span class="small pull-left">
															${ ml.msg_regidate }
														</span>
													</h4>
												</div>
												
												<%-- 상대방 메세지 --%>
												<span class="small pull-left">
													<h5>${ ml.msg_text }</h5>
												</span>
											</c:if>
											
											<br>
										</div>
									</div>
								</div>
							</c:forEach>
							
							<hr>
								
							<%-- 메세지 전송창 + 전송버튼 --%>
							<form action="/message/send" method="POST">
								<div class="portlet-footer">
									<div class="row" style="height: 90px">
										<%-- 메세지 입력창 --%>
										<div class="form-group col-xs-10">
											<textarea style="heigh: 80px" name="msg_text"
												class="form-control" placeholder="메세지를 입력하세요." maxlength="100"></textarea>
										</div>
										
										<%-- 전송 버튼 --%>
										<div class="form-group col-xs-2">
											<input type="button" value="전송" class="btn btn-default pull-right"
																			   onclick="sendMessage(this.form)">
											<div class="clearfix"></div>
										</div>
									</div>
								</div>
								
								<%-- 내 아이디 hidden --%>
								<input type="hidden" name="my_id" value="${ sessionScope.memberVO.member_id }">
								
							</form>
								
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</body>
</html> 



