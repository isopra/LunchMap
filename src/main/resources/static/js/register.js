$(function() {
	$(document).ready(function() {
		$('input').blur(function() {
			if($(this).val().length === 0) {
				$(this).parent().find('label').removeClass("focus");
			}else {
				returns;
			}
		}).focus(function() {
			$(this).parent().find('label').addClass('focus')
		});
	});
});