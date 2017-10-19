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
					data-target="#LoginModal"><strong>�α���</strong></a> &nbsp;&nbsp;&nbsp;
					 <a class="btn btn-primary btn-xl" data-toggle="modal"
					data-target="#JoinModal"><strong>ȸ������</strong></a>
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
								<label class="control-label col-sm-2" for="id">���̵� </label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="login_id"
										name="login_id" placeholder="Enter ID">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="pass">��й�ȣ</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" id="login_pass"
										name="login_pass" placeholder="Enter password">
								</div>
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-default" id="login_button"
									data-dismiss="modal"><strong>�α���</strong></button>

								<button type="button" class="btn btn-default"
									data-dismiss="modal"><strong>�ݱ�</strong></button>
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
								<label for="inputid">���̵�</label> <input
									class="form-control input-lg" type="text" id="join_id"
									name="join_id">
							</div>
							<div class="form-group">
								<label for="inputpass">��й�ȣ</label> <input
									class="form-control input-sm" type="password" id="join_pass"
									name="join_pass">
							</div>
							<div class="form-group">
								<label for="inputname">�̸�</label> <input class="form-control"
									type="text" id="join_name" name="join_name">
							</div>
							<div class="form-group">
								<label for="inputbirth">���� ����</label> <input
									class="form-control input-sm" type="text" id="join_birth"
									name="join_birth" placeholder="ex)921104 6�ڸ�">
							</div>
							<div class="form-group">
								<label for="inputsex">����</label><br> <input type="radio"
									id="gender" name="gender" value="male" checked />
									<font style="color:#000000"><strong>����</strong></font>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio"
									id="gender" name="gender" value="female" />
									<font style="color:#000000"><strong>����</strong></font>
							</div>
							<div class="form-group">
								<label for="inputphone">�ڵ��� ��ȣ</label> <input
									class="form-control input-sm" type="text" id="join_phNum"
									name="join_phNum" placeholder="ex)'-'���� �Է�">
							</div>
							<div class="form-group">
								<label for="inputemail">�̸���</label> <input
									class="form-control input-sm" type="text" id="join_email"
									name="join_email" placeholder="ex)mvp@naver.com">
							</div>
							<div class="form-group">
							<label for="inputphoto">������ ���� ÷��</label><br>
								<h6><strong>5MB </strong> ������ �̹��� ���ϸ� ����� �� �ֽ��ϴ�. (�ǳʶٱ� ����)</h6>
								<input type="file" class="upload" id="uploadInputBox" name="Filedata"
									style="color:#000000"> 
							</div>
						</form>
						<!-- ȸ�� �Է� ���� �� -->

						<!-- ȸ������, �ݱ� ��ư -->
						<div class="modal-footer">
							<button class="btn btn-default" data-dismiss="modal"
								id="check_join" name="check_join">ȸ������</button>
							<button class="btn btn-default" data-dismiss="modal">�ݱ�</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
</body>
</html>