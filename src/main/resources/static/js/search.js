var map;
var marker = [];
var pl = [];
var requestid =[];

function initMap(){
	if(!navigator.geolocation) {
    	alert('Geolocation APIに対応していません');
    	return false;
    }

	navigator.geolocation.getCurrentPosition(function(position){
		var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);//現在地
		var opts = {
			zoom:18,
			center:latlng,
		};

		console.log(data);
		map = new google.maps.Map(document.getElementById("map_sam"), opts);

		//現在地にマーカーを立てる
		var nowPlace = new google.maps.Marker({
			position: latlng,
			title: "現在地",
			map: map
		});

		//開始&ドラッグ操作時に実行
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
		for (i = 0; i <  marker.length; i++) {
        	marker[i].setMap(null);
        }
        //配列削除
        for (i = 0; i <  marker.length; i++) {
        	marker = [];
        }
    }
	//中心座標取得
	var center = map.getCenter();

	//map起動時またはあり/なし両方が押されたとき
	if(data == "Both"){
		var request = {
			location: center,
			radius:'250',
			type:['restaurant']
		};
		console.log(request);
		service = new google.maps.places.PlacesService(map);
		service.nearbySearch(request, callback);
	}else {
		//ありが押されたとき
		for(i= 0;i<datalist.length;i++){
			var request = {
				placeId:datalist[i].place_id,
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
	var iconCo ='https://maps.google.com/mapfiles/ms/icons/yellow-dot.png';
	var vis = true;

	//place_idの判定
	if(datalist != null){
		for(var e = 0; e < datalist.length; e++){
			if(place.place_id == datalist[e].place_id){
				iconCo = 'http://maps.google.com/mapfiles/ms/icons/green-dot.png';
				break;
			}else{
				iconCo ='https://maps.google.com/mapfiles/ms/icons/yellow-dot.png';


			}
		}
	}

	var res = google.maps.geometry.spherical.computeDistanceBetween(place.geometry.location, center);
	console.log(res);
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


function toView(to) {
	location.href = "/menu/" + to;
}