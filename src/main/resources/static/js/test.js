/**
 *
 */
	window.onload = function test(){


   			var map;


   			map = new google.maps.Map(document.getElementById('sample'), { // #sampleに地図を埋め込む

			     zoom: 19 // 地図のズームを指定
			   });

   			var service = new google.maps.places.PlacesService(map);
   			service.getDetails({
   			    placeId: placeId,
   			    fields: ['name', 'formatted_address', 'geometry', 'url']
   			}, function(place, status) {
   			    if (status == google.maps.places.PlacesServiceStatus.OK) {
				  var marker = new google.maps.Marker({ // マーカーの追加
   				      position: place.geometry.location, // マーカーを立てる位置を指定
   				      map: map // マーカーを立てる地図を指定
   				   });
   				map = new google.maps.Map(document.getElementById('sample'), { // #sampleに地図を埋め込む
   			     center: place.geometry.location, // 地図の中心を指定
   			     zoom: 19 // 地図のズームを指定
   			   });
   			    	var element = document.getElementById('shopName');
   			    	element.innerHTML = place.name;
   			    	console.log(place.name);
   			        console.log(place.geometry.location);
   			    }
   			});


	}


	}