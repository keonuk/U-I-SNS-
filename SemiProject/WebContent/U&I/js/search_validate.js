$(document).ready(function() {

	$("#searchBtn").click(function(e) {
		if ($.trim($("#searchText").val()) == "") {
			alert("����ڸ� �Է��ϼ���.");
			$("#searchText").focus();
			// e.preventDefault();
			return false;
		}
	})
})