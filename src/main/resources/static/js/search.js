var map;
var marker = [];
//地図を表示
function initMap(){
	if(!navigator.geolocation) {
    	alert('Geolocation APIに対応していません');
    	return false;
    }
	navigator.geolocation.getCurrentPosition(function(position){
		var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);//現在地
		var opts = {
			zoom:19,
			center:latlng
		};
		map = new google.maps.Map(document.getElementById("map_sam"), opts);
		//現在地にマーカーを立てる
		var nowPlace = new google.maps.Marker({
			position: latlng,
			title: "現在地",
			map: map
		});
		//開始&ドラッグ等操作完了時実行
		google.maps.event.addListener(map, 'idle', function(){
			dispLatLng();
		});
	},function(){
		alert('位置情報取得に失敗しました');
	});
}
//ドラッグ操作の設定
function dispLatLng(){
	if(marker.length > 0){
		//マーカー削除
		for (var i = 0; i <  marker.length; i++) {
        	marker[i].setMap(null);
        }
        //配列削除
        for (var i = 0; i <  marker.length; i++) {
        	marker = [];
        }
    }
	//中心座標取得
	var center = map.getCenter();
	var cenlat = center.lat();
	var cenlng = center.lng();
//	画面の境界を取得
	var bounds = map.getBounds();
//	地図の右上の座標
	var map_ne = bounds.getNorthEast();
	var nelat = map_ne.lat();
	var nelng = map_ne.lng();
//	地図の左下の座標
	var map_sw = bounds.getSouthWest();
	var swlat = map_sw.lat();
	var swlng = map_sw.lng();
/*ajaxで画面限界を送信、datalistを取得----------------------*/
	border(nelat,nelng,swlat,swlng);
}
//検索結果の処理
function callback(results, status) {
	if (status == google.maps.places.PlacesServiceStatus.OK) {
		for (var i = 0; i < results.length; i++) {
			createMarker(results[i]);
		}
	}
}
//マーカーを表示
function createMarker(place){
	var center = map.getCenter();
	var cenlat = center.lat();
	var cenlng = center.lng();
	if(place == null){
		return;
	}
	//console.log(place.place_id);
	var iconCo ='https://maps.google.com/mapfiles/ms/icons/yellow-dot.png';
	var vis = true;
	//place_idの判定
	for(var i = 0; i < datalist.length; i++){
        if(place.place_id == datalist[i].place_id){
        	iconCo = 'http://maps.google.com/mapfiles/ms/icons/green-dot.png';
			break;
		}else{
			iconCo ='https://maps.google.com/mapfiles/ms/icons/yellow-dot.png';
		}
	}
	//中心座標との距離
	var lat = place.geometry.location.lat();
	var lng = place.geometry.location.lng();
	var res1 = distance(cenlat, cenlng, lat, lng)
//	console.log("res1="+ res1);
	if(res1 > 500){
		vis = false;
	}
	var lat = place.geometry.location.lat();
	var lng = place.geometry.location.lng();
//	console.log(place.geometry.location.lat());
//	console.log(vis);
	//各店のマーカーを定義
	marker.push ( new google.maps.Marker({
		position: place.geometry.location,
		title:place.name,
		url:"/sessionAdd/" + place.place_id+"/"+place.name+"/"+lat+"/"+lng,//shopinfoへ移動
		map: map,
		visible:vis,
		icon:iconCo
	}));
	//クリック時urlに移動
	for(var i=0; i < marker.length; i++){
		google.maps.event.addListener(marker[i],'click',function(){
			window.location.href = this.url;
		});
	}
}
//ページ遷移用メソッド
function toView(to) {
	location.href = "/menu/" + to;
}
//モーダルの表示
function Modal() {
	const modalArea = document.getElementById('modalArea');
	modalArea.classList.add('is-show')
}
//モーダルを閉じる
function Modalclose(){
	const modalArea = document.getElementById('modalArea');
	modalArea.classList.remove('is-show')
}
//あり/なし両方を押したとき
function flg0(ischecked){
	const imp = document.getElementById("imp");
	const imp1 = document.getElementById("select1");
	const imp2 = document.getElementById("select2")
	if(ischecked == true){
		imp.classList.add('imp');
		imp1.disabled = true;
		imp1.checked=false;
		imp2.disabled = true;
		imp2.checked=false;
	}else {
		imp1.disabled = false;
		imp2.disabled = false;
	}
}
//ありを押したとき
function flg1(ischecked){
	const imp = document.getElementById("imp");
	const imp1 = document.getElementById("select1");
	const imp2 = document.getElementById("select2");
	if(ischecked == true){
		imp.classList.remove('imp');
  		imp1.disabled = false;
  		imp2.disabled = false;
	}else {
  		imp1.disabled = true;
  		imp2.disabled = true;
	}
}
//座標間の距離計算
function distance(lat1, lng1, lat2, lng2) {
	  lat1 *= Math.PI / 180;
	  lng1 *= Math.PI / 180;
	  lat2 *= Math.PI / 180;
	  lng2 *= Math.PI / 180;
	  return 6378137 * Math.acos(Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1) + Math.sin(lat1) * Math.sin(lat2));
}
//検索実行
function search(){
	/*ajaxでpostで送信するとき必要---------------------------------*/
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
      xhr.setRequestHeader(header, token);
    });
    /*------------------------------------------------------------*/
    var jsonString = $('#the-form').serialize();
	var formData =  JSON.stringify(jsonString);
	condition(formData);
	$.ajax({
	   url: "/menu/search",  //POST送信を行うファイル名を指定
	   type: "POST",
	   data: formData,  //POST送信するデータを指定
	   datatype: 'json',
	   contentType: 'application/json',
	   scriptCharset: 'utf-8',
	   success: function(data) {
		   datalist = data;
		   initMap();
		   Modalclose();
	   },
	   error: function(data) {
		   alert('error');
	   }
   });
}
//境界をサーバーに送信
function border(nelat,nelng,swlat,swlng){
	var test_arr =[nelat,nelng,swlat,swlng];
	/*ajaxでpostで送信するとき必要---------------------------------*/
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
      xhr.setRequestHeader(header, token);
    });
    /*------------------------------------------------------------*/
    var center = map.getCenter();
	var cenlat = center.lat();
	var cenlng = center.lng();
    $.ajax({
    	url: "/menu/border",  //POST送信を行うファイル名を指定
        type: "POST",
        data: JSON.stringify(test_arr),  //POST送信するデータを指定
        datatype: 'json',
        contentType: 'application/json',
        scriptCharset: 'utf-8',
        traditional: true,
        success: function(data) {
     	   datalist = data;
     	   console.log(datalist);
     		//map起動時またはあり/なし両方が押されたとき
     		if(con == "Both" || con == null){
     			var request = {
     				location: center,
     				rankBy: google.maps.places.RankBy.DISTANCE,
     				type:['restaurant']
     			};
     			service = new google.maps.places.PlacesService(map);
     			service.nearbySearch(request, callback);
     		}else{
     			//ありが押されたとき
     			for(var i= 0;i<datalist.length;i++){
     				var latlng = new google.maps.LatLng(datalist[i].latitude,datalist[i].longitude ) ;
     				var res1 = distance(cenlat, cenlng, datalist[i].latitude, datalist[i].longitude);
     				var vis = true;
     				if(res1 > 500){
     					vis = false;
     				}
     				marker.push ( new google.maps.Marker({
     					position:latlng,
     					title:datalist[i].place_name,
     					url:"/sessionAdd/" + datalist[i].place_id + "/" + datalist[i].place_name+"/"+datalist[i].latitude+"/"+datalist[i].longitude,//情報ページのurl指定
     					map: map,
     					visible:vis,
     					icon:'http://maps.google.com/mapfiles/ms/icons/green-dot.png'
     				}));
     				google.maps.event.addListener(marker[i],'click',function(){
     					window.location.href = this.url;
     				})
     			}
     		}
        },
        error: function(data) {
     	   alert('error' );
        }
    });
}
function condition(cond){
	con = "Both";
	if(cond == "\"locate=Exist\""){
		con = "Exist";
	}else if(cond == "\"locate=Exist&near=near\""){
		con = "near";
	}else if(cond == "\"locate=Exist&mylog=mylog\""){
		con = "mylog";
	}else if(cond == "\"locate=Exist&near=near&mylog=mylog\""){
		con = "both condition";
	}
}
