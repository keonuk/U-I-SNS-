$(document).ready(function() {

	$("#searchBtn").click(function(e) {
		if ($.trim($("#searchText").val()) == "") {
			alert("사용자를 입력하세요.");
			$("#searchText").focus();
			// e.preventDefault();
			return false;
		}
	})
})