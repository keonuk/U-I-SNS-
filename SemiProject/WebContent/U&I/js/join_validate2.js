
	$(document).ready(function() {
		
		$("#check_join").click(function(){
			
			
			if($.trim($("#join_id").val()) == ""){
				alert("ID�� �Է��ϼ���.");
				$("#join_id").focus();
				return false;
			}			
			
			if($.trim($("#join_pass").val()) == ""){
				alert("��й�ȣ�� �Է��ϼ���.");
				$("#join_pass").focus();
				return false;
			}
			if($.trim($("#join_name").val()) == ""){
				alert("�̸��� �Է��ϼ���.");
				$("#join_name").focus();
				return false;
			}
			if($.trim($("#join_birth").val()) == ""){
				alert("��������� �Է��ϼ���.");
				$("#join_birth").focus();
				return false;
			}
			if($.isNumeric($("#join_birth").val()) == ""){
				alert("��������� ���ڸ� �Է��ϼ���.");
				$("#join_birth").val('');
				$("#join_birth").focus();
				return false;
			}
			if($.trim($("#join_phNum").val()) == ""){
				alert("�ڵ��� ��ȣ�� �Է��ϼ���.");
				$("#join_phNum").focus();
				return false;
			}
			if($.isNumeric($("#join_phNum").val()) == ""){
				alert("�ڵ��� ��ȣ�� ���ڸ� �Է��ϼ���.");
				$("#join_phNum").val('');
				$("#join_phNum").focus();
				return false;
			}
			if($.trim($("#join_email").val()) == ""){
				alert("�̸����� �Է��ϼ���.");
				$("#join_email").focus();
				return false;
			}
			
			joinform.submit();
		});
	});
