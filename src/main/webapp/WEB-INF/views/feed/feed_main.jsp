<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>FACEGRAM</title>
		<style>
		#feed {
			position: relative;
		    top: 100px;
		    padding: 0;
		    margin: 0 33% 100% 33%;
		    padding-bottom: 100%;
		}
		
		#regi table {
			 width: 550px;
			 text-align: center;
			 border: 1px solid lightgray;
			 border-collapse: collapse;
		}
		
		#regi #feedRegiBtn {
		    background: none;
		    border: 1px solid lightgray;
		    width: 100px;
		    height: 40px;
    		margin: 10px;
    		cursor: pointer;
		}
		
		#regi textarea {
		    resize: none;
		    border: none;
		    margin: 10px;
		}
		
		#contents table {
		    width: 550px;
		    border: 1px solid lightgray;
		}
		
		#contents tbody {
			display: block;
	    	overflow: auto;
		    height: 170px;
		}
		
		#contents #profileId {
			font-weight: 500;
			font-size: 22px;
		}
		
		#contents #profileImg {
		    margin: 5px 15px 5px 5px;
		    border-radius: 50%;
		    width: 50px;
		    height: 50px;
		    vertical-align: middle;
		}
		
		#text {
			width: 85%;
			margin: 7px; 
			border: none;
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
		    width: 55px;
    		height: 35px;
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
			 var msg = window.open("/feed/showcontent?feed_idx=" + feed_idx +
					 						  	 "&register_id=" + register_id, "detail" ,
					 						   		    "width=" + popW +
												      ",height=" + popH +
												   	     ",top=" + posT +
												   	    ",left=" + posL +
												   	    ",resizable=no,scrollbars=no");
		}
		
		// 댓글 작성
		function commentsRegi ( f ) {
			var text		  = f.text.value; 		   // 작성한 댓글 내용
			var feed_idx	  = f.feed_idx.value;	   // 해당 글(부모글) 번호
			var my_id 	      = f.my_id.value;		   // 작성자(나) 아이디
			
			if ( text == "" ) {
				alert("댓글을 입력하세요.");
				f.text.focus();
				return;
			}
			
			f.submit();
		}
		
		/** 댓글 작성 (CALL BACK) (구현예정)
		function regi_callback() {
			console.log( xhr.readyState + " / " + xhr.status );
		
			if ( xhr.readyState == 4 && xhr.status == 200 ) {
				var msg = xhr.responseText;
				
			}
		} */
		
		// 피드 업로드 (AJAX)
		function regi ( f ) {
			var myImg = f.myImg.value.trim();
			var text  = f.feedtext.value.trim();
			
			if ( myImg == "" || myImg == null ) {
				alert("사진 필수첨부");
				return;
			}
			
			f.submit();
		}
		
		// 피드 업로드전에 사진 미리보기
		function imagePreview ( val ) {
			
			document.all.myImg.click();			// 파일선택 버튼을 클릭시킴
			val = document.all.myImg.value; 	// 클릭된 사진을 '찾아보기' 버튼으로 대체시킴
			
			// 변경할 사진이 선택되면
			document.getElementById("myImg").onchange = function() {
				// 변경할 이미지명만 따옴
				var updateImgName = document.getElementById("myImg").files[0].name;
				
				//alert( "파일명 : " + updateImgName);
				
				var updateImg = document.getElementById("myImg")
				    updateImg.src = URL.createObjectURL(event.target.files[0]);
				
				//alert("파일주소 : " + updateImg.src);
				
				document.getElementById("image").style.width = "500px"; // 업로드할 사진의 가로크기 지정
				document.getElementById("image").src = updateImg.src;   // 업로드할 사진으로 교체 미리보기
			};
		}
		
		// 댓글 작성자 아이디 클릭시 해당 회원 feed로 이동
		function goUserFeed( my_id, user_id ) {
			
			// id가 '나' 면 내 프로필 페이지로
			if ( my_id == user_id ) {
				location.href = "/feed/mypageview";
				return;
			}
			
			// 아니면 해당 회원 프로필 페이지로
			location.href = "/feed/friend?friend_id=" + user_id;
		}
		</script>
		
	</head>
	<body>
		<jsp:include page="feed_index.jsp"/>
		
		<%-- 피드 글쓰기 --%>
		<div id="feed">
			<div id="regi">
				<form action="/feed/regi" enctype="multipart/form-data" method="POST" >
					<table>
						<tr>
							<td>
								<!-- 피드에 등록할 사진 -->
								<img style="cursor:pointer;" id="image" src="/resources/uploadImage.png" onclick="javascript:imagePreview(this.value)">
								<input style="display:none;" id="myImg" name="myImg" type="file">
							</td>
						</tr>
						
						<tr>
							<td><textarea name="feedtext" rows="10" cols="65"></textarea></td>
						</tr>
						
						<tr>
							<td><input id="feedRegiBtn" type="button" value="등록" onclick="regi(this.form)"></td>
						</tr>	
						
						<%-- 글 등록자 id(나) hidden --%>
						<input type="hidden" name="register" value="${ sessionScope.memberVO.member_id }">
					</table>
				</form>
			</div>
			
			<%-- 피드 친구 게시물들 --%>
			<div id="contents">
				<c:forEach var="feed" items="${ friendsFeedList }" varStatus="status">
					<article>
						<table>
							<thead style="display: block;">
								<tr style="height: 55px;">
									<%-- 프로필 이미지 + 아이디 --%>
									<td id="profileId">
										<img id="profileImg" src="/resources/profileimages/${ feed.friend_profileImg }">
										<a href="/feed/friend?friend_id=${ feed.user_id }">
											${ feed.user_id }
										</a>
									</td>
								</tr>
								
								<tr>
									<td>
										<%-- 피드 사진 / 이미지클릭시 모달창 띄움  --%>
										<a href="javascript:showContent(${ feed.feed_idx }, '${ feed.user_id }')">
											<img src="/resources/feedimages/${ feed.feed_image }" width="550px">
										</a>
									</td>
								</tr>
								
								<tr>
									<td>
									<div style="min-height: 100px;">	
										<%-- 아이디 --%>
										<strong style="color:lightsalmon; font-size: 19px;">
											<a href="/feed/friend?friend_id=${ feed.user_id }">
											${ feed.user_id }
											</a>
										</strong>
										 
										<%-- 내용 --%>
										<span style="font-size: 16px;"> ${ feed.feed_text }</span>
									</div>
									</td>
								</tr>
								
								<tr style="height: 40px;">
									<%-- 등록일시 --%>
									<td style="text-align: right; font-size: 14px;">${ feed.feed_regidate }</td>
								</tr>
							</thead>
							
							<tbody>
								<%-- 댓글 list --%>
								<c:forEach var="com" items="${ feed.com_list }" varStatus="status">
								<tr style="font-size: 14px;">
									<td>
									<%-- 댓글(0) 만 출력 --%>
									<c:if test="${ com.comments_div eq 0 }">	
										<%-- 댓글 작성자 프로필사진 --%>
										<img style="border-radius: 50%; width: 20px; height: 20px;" src="/resources/profileimages/${ com.profile_image }">
										
										<%-- 댓글 작성자 ID --%>
										<strong style="font-size: 13px;">
											<a href="javascript:goUserFeed('${ sessionScope.memberVO.member_id }', '${ com.user_id }')">
												${ com.user_id }
											</a>
										</strong>
										
										<%-- 댓글 내용 + 마우스오버시 작성일시 show --%>
										<a style="font-size: 12px;" title="${ com.comments_regidate }">${ com.comments_text }</a><br>
									</c:if>
									</td>
								</tr>	
								</c:forEach>
							</tbody>
							
							<tbody>	
							<tr>	
								<td style="width:550px;">
									<form action="/comment/regi">
										<%-- 게시글 번호 hidden --%>
										<input type="hidden" name="feed_idx" value=${ feed.feed_idx }>
										
										<%-- 내 아이디 hidden --%>
										<input type="hidden" name="my_id" value='${ sessionScope.memberVO.member_id }'>
										 
										<%-- 메인창인지 모달창인지 구분해줄 값 hidden --%>
										<input type="hidden" name="where_url" value="main">
										
										<!-- 댓글 번호 hidden 초기값 "" -->
										<input type="hidden" id="comments_refNum" name="comments_refNum" value="0">
										 
										<div>
										<textarea id="text" name="text" placeholder="댓글 입력"></textarea>
										<input id="regiBtn" type="button" value="등록" onclick="commentsRegi(this.form)">
										</div>
									</form>
								</td>
							</tr>
							</tbody>
							
							<br><br>
						</table>
					</article>
				</c:forEach>
			</div>
		</div>
	</body>
</html>