	$(document).ready(function(){
		$("a").click(function() {
			var selected = $(this).data('showpopup');
			$(".variant" + selected).toggle("slow");
		});
		/*$("input:radio").click(function() {
			var selected = $(this).data('showpopup');
			$(".variant" + selected).toggle("slow");
		});*/
		$( "input[type='radio']" ).change(function() {
			var selected = $(this).data('showpopup');
			$(".variant" + selected).toggle("slow");
		});
	});
	
	