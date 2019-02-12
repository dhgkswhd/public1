<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>FACEGRAM</title>
		
		<!-- 웹폰트 -->
		<link href="http://fonts.googleapis.com/earlyaccess/notosanskr.css" rel="stylesheet" type="text/css">
		
		<style>
		body {
		    margin: 0 auto;
		    font-family: Noto Sans KR;
		    color : dimgrey;
		    z-index:1;
		}
		
		#main {
			border-bottom: 1px solid palegoldenrod;
			width: 100%;
			margin: 5px 0px auto;
			text-align: center;
		}
		
		#main .facegram {
			text-decoration: none;
		    text-align: center;
		    font-size: 30px;
		    color: palegoldenrod;
		    margin: 0 auto;
		}
		
		#login {
		    background-color: #fff;
		    border: 1px solid #e6e6e6;
		    border-radius: 1px;
		    margin: 100px 30% 0px;
		    padding: 10px;
		    text-align: center;
		}
		
		#login dd {
			margin: 0 auto;
		}
		
		#login input {
		    height: 20px;
		    border:none;
		    background-color: #e2e2e2;
		    font-family: Noto Sans KR;
		}
		</style>
		
		<!--  AJAX 파일  -->
		<script src="/fg/js/httpRequest.js"></script>
		
		<script>
		// 로그인 AJAX
		function login ( f ) {
			var  id = f.member_id.value;
			var pwd = f.member_pwd.value;
			
			if ( id == '' && pwd == '' ) {
				alert("아이디와 패스워드를 입력해주세요");
				return;
			}
			
			var url	   = "/member/login"; 
			var param  = "member_id=" + id + "&member_pwd=" + pwd;
			
			sendRequest( url, param, check_member, "POST" );
		}
		
		function check_member() {
			console.log( xhr.readyState + " / " + xhr.status );
			
			if ( xhr.readyState == 4 && xhr.status == 200 ) {
				var msg = xhr.responseText;
				
				if ( msg == 'yes' ) {
					// feed 메인페이지로
					location.href = "/feed/mainview";
					alert("로그인 되었습니다");
				}
				else {
					alert("회원정보가 없거나 일치하지 않습니다");
				}
			}
		}
		</script>
		
	</head>
	
	<body>
		<div id="main">
			<a class="facegram" href="/main">FACEGRAM</a>
		</div>
		
		<div id="login">
			<form action="/member/login" method="POST"> 
				<h3>FACEGRAM LOGIN</h3>
				<dl>
					<dd>아이디</dd>
					<dd>
						<input type="text" name="member_id">
					</dd>
					
					<dd>비밀번호</dd>
					<dd>
						<input type="password" name="member_pwd">
					</dd>
					<br>
					<dd>
						<input type="button" value="로그인" onclick="login(this.form)">
					</dd>
					<br>
					<dd>
						<input type="button" value="회원가입" onclick="location.href='member/joinview'">
						<input type="button" value="아이디/비밀번호찾기" onclick="location.href='member/findpwview'">
					</dd>
				</dl>
			</form>
		</div>
	<body>
	
	</body>
</html>