<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<html>
<head>
<meta charset="utf-8">
	<title>YOU & I - HOME</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="generator" content="Codeply">
<link rel="stylesheet" href="./U&I/css/bootstrap.min.css" />
<link rel="stylesheet" href="./U&I/css/animate.min.css" />
<link rel="stylesheet" href="./U&I/css/ionicons.min.css" />
<link rel="stylesheet" href="./U&I/css/styles.css" />

<!--scripts loaded here -->
<script src="./U&I/js/jquery-3.2.1.js"></script>
<script src="./U&I/js/jquery.min.js"></script>
<script src="./U&I/js/bootstrap.min.js"></script>
<script src="./U&I/js/jquery.easing.min.js"></script>
<script src="./U&I/js/wow.js"></script>
<script src="./U&I/js/scripts.js"></script>
<script src="./U&I/js/login_validate.js"></script>
<script src="./U&I/js/join_validate2.js"></script>
<style>
	label {color:#000000}
	div > h6 {color:#000000}
</style>
</head>


<body>
	<header id="first">
		<div class="header-content">
			<div class="inner">
				<p class="cursive" style="color:#333300; font-weight:bold;
				font-size:130px; padding-top:100px">YOU & I</p>
				<hr>
				<a class="btn btn-primary btn-xl" data-toggle="modal"
					data-target="#LoginModal"><strong>로그인</strong></a> &nbsp;&nbsp;&nbsp;
					 <a class="btn btn-primary btn-xl" data-toggle="modal"
					data-target="#JoinModal"><strong>회원가입</strong></a>
			</div>
		</div>

		<!-- Modal -->

		<div class="modal fade" id="LoginModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-body">
						<form name="loginform" class="form-horizontal"
							action="loginProcess.sr" method="post">
							<div class="form-group">
								<label class="control-label col-sm-2" for="id">아이디 </label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="login_id"
										name="login_id" placeholder="Enter ID">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="pass">비밀번호</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" id="login_pass"
										name="login_pass" placeholder="Enter password">
								</div>
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-default" id="login_button"
									data-dismiss="modal"><strong>로그인</strong></button>

								<button type="button" class="btn btn-default"
									data-dismiss="modal"><strong>닫기</strong></button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="JoinModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-body">
						<form name="joinform" style="text-align: left" method="post"
							action="joinProcess.sr" enctype="multipart/form-data">

							<div class="form-group" style="text-align: left">
								<label for="inputid">아이디</label> <input
									class="form-control input-lg" type="text" id="join_id"
									name="join_id">
							</div>
							<div class="form-group">
								<label for="inputpass">비밀번호</label> <input
									class="form-control input-sm" type="password" id="join_pass"
									name="join_pass">
							</div>
							<div class="form-group">
								<label for="inputname">이름</label> <input class="form-control"
									type="text" id="join_name" name="join_name">
							</div>
							<div class="form-group">
								<label for="inputbirth">생년 월일</label> <input
									class="form-control input-sm" type="text" id="join_birth"
									name="join_birth" placeholder="ex)921104 6자리">
							</div>
							<div class="form-group">
								<label for="inputsex">성별</label><br> <input type="radio"
									id="gender" name="gender" value="male" checked />
									<font style="color:#000000"><strong>남성</strong></font>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio"
									id="gender" name="gender" value="female" />
									<font style="color:#000000"><strong>여성</strong></font>
							</div>
							<div class="form-group">
								<label for="inputphone">핸드폰 번호</label> <input
									class="form-control input-sm" type="text" id="join_phNum"
									name="join_phNum" placeholder="ex)'-'없이 입력">
							</div>
							<div class="form-group">
								<label for="inputemail">이메일</label> <input
									class="form-control input-sm" type="text" id="join_email"
									name="join_email" placeholder="ex)mvp@naver.com">
							</div>
							<div class="form-group">
							<label for="inputphoto">프로필 사진 첨부</label><br>
								<h6><strong>5MB </strong> 이하의 이미지 파일만 등록할 수 있습니다. (건너뛰기 가능)</h6>
								<input type="file" class="upload" id="uploadInputBox" name="Filedata"
									style="color:#000000"> 
							</div>
						</form>
						<!-- 회원 입력 정보 끝 -->

						<!-- 회원가입, 닫기 버튼 -->
						<div class="modal-footer">
							<button class="btn btn-default" data-dismiss="modal"
								id="check_join" name="check_join">회원가입</button>
							<button class="btn btn-default" data-dismiss="modal">닫기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
</body>
</html>