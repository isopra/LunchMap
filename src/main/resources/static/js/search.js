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

		console.log(data);
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

	//map起動時またはあり/なし両方が押されたとき
	if(data == "Both" || data == null){
		var request = {
			location: center,
			rankBy: google.maps.places.RankBy.DISTANCE,
			type:['restaurant']
		};
		console.log(request);
		service = new google.maps.places.PlacesService(map);
		service.nearbySearch(request, callback);

	}else if(data == "Exist"){
		//ありが押されたとき
		for(var i= 0;i<datalist.length;i++){
			var request = {
				placeId:datalist[i].place_id,
				fields: ['name',  'place_id', 'geometry']
			};
			service = new google.maps.places.PlacesService(map);
			service.getDetails(request, createMarker);
		}
	}else {
		//最近の口コミ、または自分の投稿が押されたとき
		for(i= 0;i<datalist.length;i++){
			var request = {
				placeId:datalist[i],
				fields: ['name',  'place_id', 'geometry']
			};
			service = new google.maps.places.PlacesService(map);
			service.getDetails(request, createMarker);
		}
	}
}

//検索結果の処理
function callback(results, status) {
	if (status == google.maps.places.PlacesServiceStatus.OK) {
		console.log(results);
		for (var i = 0; i < results.length; i++) {
			createMarker(results[i]);
		}
	}
}


//マーカーを表示
function createMarker(place){
	console.log(place);
	var center = map.getCenter();
	if(place == null){
		return;
	}
	//console.log(place.place_id);
	var iconCo ='http://maps.google.com/mapfiles/ms/icons/green-dot.png';
	var vis = true;

	//place_idの判定
	if(datalist != null){
		for(var i = 0; i < datalist.length; i++){
			if(place.place_id == datalist[i].place_id){
				iconCo = 'http://maps.google.com/mapfiles/ms/icons/green-dot.png';
				break;
			}else{
				iconCo ='https://maps.google.com/mapfiles/ms/icons/yellow-dot.png';
			}
		}
	}

	//中心座標との距離
	var res = google.maps.geometry.spherical.computeDistanceBetween(place.geometry.location, center);
	if(res > 500){
		vis = false;
	}
	console.log(vis);
	//各店のマーカーを定義
	marker.push ( new google.maps.Marker({
		position: place.geometry.location,
		title:place.name,
		url:"/shopinfo/" + place.place_id,//情報ページのurl指定
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
	if(ischecked == true){
		document.getElementById("select1").disabled = true;
		document.getElementById("select1").checked=false;
		document.getElementById("select2").disabled = true;
		document.getElementById("select2").checked=false;
	}else {
		document.getElementById("select1").disabled = false;
		document.getElementById("select2").disabled = false;
	}
}

//ありを押したとき
function flg1(ischecked){
	if(ischecked == true){
  		document.getElementById("select1").disabled = false;
  		document.getElementById("select2").disabled = false;
	} else {
  		document.getElementById("select1").disabled = true;
  		document.getElementById("select2").disabled = true;
	}
}
