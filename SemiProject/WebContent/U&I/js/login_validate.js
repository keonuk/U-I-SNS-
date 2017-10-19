
$(document).ready(function() {
	
   $("#login_button").click(function(e) {
      if($.trim($("#login_id").val()) == ""){
         alert("ID를 입력하세요.2");
         $("#login_id").focus();
         //e.preventDefault();
         return false;
      }
      if($.trim($("#login_pass").val()) == ""){
         alert("비밀번호를 입력하세요.");
         $("#login_pass").focus();
         //e.preventDefault();
         return false;
      }
      
      loginform.submit();
   })
})