/**
 *
 */

$(function() {
  $('#form').submit(function() {
	  var count = $(this).val().length;


    // コメントが200文字以上のとき、エラー文を表示
    if(count >= 200 ){
      $('#error-message').text('200文字以内で入力してください');
    }else{
      $('#error-message').text('');

    }
  });

});