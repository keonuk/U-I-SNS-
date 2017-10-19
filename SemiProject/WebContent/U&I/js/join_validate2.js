
	$(document).ready(function() {
		
		$("#check_join").click(function(){
			
			
			if($.trim($("#join_id").val()) == ""){
				alert("ID를 입력하세요.");
				$("#join_id").focus();
				return false;
			}			
			
			if($.trim($("#join_pass").val()) == ""){
				alert("비밀번호를 입력하세요.");
				$("#join_pass").focus();
				return false;
			}
			if($.trim($("#join_name").val()) == ""){
				alert("이름을 입력하세요.");
				$("#join_name").focus();
				return false;
			}
			if($.trim($("#join_birth").val()) == ""){
				alert("생년월일을 입력하세요.");
				$("#join_birth").focus();
				return false;
			}
			if($.isNumeric($("#join_birth").val()) == ""){
				alert("생년월일은 숫자를 입력하세요.");
				$("#join_birth").val('');
				$("#join_birth").focus();
				return false;
			}
			if($.trim($("#join_phNum").val()) == ""){
				alert("핸드폰 번호를 입력하세요.");
				$("#join_phNum").focus();
				return false;
			}
			if($.isNumeric($("#join_phNum").val()) == ""){
				alert("핸드폰 번호는 숫자를 입력하세요.");
				$("#join_phNum").val('');
				$("#join_phNum").focus();
				return false;
			}
			if($.trim($("#join_email").val()) == ""){
				alert("이메일을 입력하세요.");
				$("#join_email").focus();
				return false;
			}
			
			joinform.submit();
		});
	});
