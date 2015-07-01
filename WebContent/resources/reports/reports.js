$(document).ready(function() {
	$("a").click(function() {
		var selected = $(this).data('showpopup');
		var $myDiv = $(".drill" + selected);
		if ($myDiv) {
			$myDiv.toggle("slow");
			if ($myDiv.is(':visible')) {
				var urlInput = $(this).data('url') + "/" + selected;
				$.ajax({
					type : "GET",
					url : urlInput,
					dataType : "html",
					async: false,
					success : function(response) {
						$myDiv.html(response);
					}
				});
			}
		}
	});
});