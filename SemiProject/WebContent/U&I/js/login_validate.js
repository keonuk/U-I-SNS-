
$(document).ready(function() {
	
   $("#login_button").click(function(e) {
      if($.trim($("#login_id").val()) == ""){
         alert("ID�� �Է��ϼ���.2");
         $("#login_id").focus();
         //e.preventDefault();
         return false;
      }
      if($.trim($("#login_pass").val()) == ""){
         alert("��й�ȣ�� �Է��ϼ���.");
         $("#login_pass").focus();
         //e.preventDefault();
         return false;
      }
      
      loginform.submit();
   })
})