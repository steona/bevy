package components
{
	
			import com.google.maps.InfoWindowOptions;
			import com.google.maps.LatLng;
			import com.google.maps.MapMouseEvent;
			import com.google.maps.overlays.Marker;
			import com.google.maps.overlays.MarkerOptions;
			import com.yahoo.maps.api.YahooMap;
			import com.yahoo.maps.api.YahooMapEvent;
			import com.yahoo.maps.api.core.location.Address;
			import com.yahoo.maps.api.core.location.LatLon;
			import com.yahoo.maps.webservices.geocoder.GeocoderResult;
			import com.yahoo.maps.webservices.geocoder.events.GeocoderEvent;
			import com.yahoo.maps.webservices.traffic.TrafficResponse;
			import com.yahoo.maps.webservices.traffic.TrafficResultItem;
			import com.yahoo.maps.webservices.traffic.TrafficSearch;
			import com.yahoo.maps.webservices.traffic.events.TrafficSearchEvent;
			
			import constants.AppConstants;
			
			import flash.display.Bitmap;
			
			import mx.collections.ArrayCollection;
			import mx.controls.Alert;
			import mx.controls.Label;
			import mx.core.Application;
			import mx.messaging.channels.StreamingHTTPChannel;
			
	
	public class Traffic
	{
		
		[Embed(source="assets/caution.jpg")]
        [Bindable]
		public var Caution:Class;
		
		[Embed(source="assets/construction.png")]
        [Bindable]
		public var Construction:Class; 
		
		private var _yahooMap:YahooMap;
		
			private var trafficdata:TrafficSearch;
			private var trafficresponse:TrafficResponse;
			private var trafficresults:TrafficResultItem;
			private var positionmarker:Marker;
			private var trafficlabel:Label;
			private var count:int;
			
			private var fulltrafficdetails:ArrayCollection;
			
			
			public function getTrafficData():void
			{
				var appid:String=AppConstants.yahooAppid;
		    	_yahooMap = new YahooMap();
				_yahooMap.addEventListener(YahooMapEvent.MAP_INITIALIZE, handleMapInitialize);
			
				_yahooMap.init(appid,Application.application.mapContainer.width,Application.application.mapContainer.height);
			}	
			
				
			private function handleMapInitialize(event:YahooMapEvent)
			{				
				var city= new Array();
				city[0] = TripPlannerApp.sourceAddr;
				city[1] = TripPlannerApp.destAddr;
				
				Application.application.trafficId.sourcetrafficdetails.removeAllChildren();
				//Application.application.trafficId.destrafficdetails.removeAllChildren();
				fulltrafficdetails = new ArrayCollection();
				
				count=0;
				for(var i:int=0;i<2;i++)
				{
					
				var address:Address = new Address(city[i]);
			 	address.addEventListener(GeocoderEvent.GEOCODER_SUCCESS, handleGeocodeSuccess);					
				address.geocode();
				
				}
				
			}
			
			private function handleGeocodeSuccess(event:GeocoderEvent):void 
			{		
								
				var address:Address = (event.target as Address);				
				var result:GeocoderResult = address.geocoderResultSet.firstResult;
				var ltlng:LatLon = result.latlon;
			
								
				//_yahooMap.centerLatLon = new LatLon(ltlng.lat,ltlng.lon);				
				
				trafficdata = new TrafficSearch();	
				var ltln:LatLon = new LatLon(ltlng.lat,ltlng.lon);
						
				
				trafficdata.addEventListener(TrafficSearchEvent.SEARCH_SUCCESS,handletrafficload);
				trafficdata.addEventListener(TrafficSearchEvent.SEARCH_FAILURE,handletrafficloadFail);
				trafficdata.getTraffic(5,ltln,600000);
				
						
					
			}
		
			

			private function handletrafficload(event:TrafficSearchEvent):void
			{
				trafficresponse = event.data as TrafficResponse;
					
				count=count+1;
				
						
				var res:Array = trafficresponse.results ;
				
								
				for(var i= 0;i<res.length;i++)
				{
					trafficresults = res[i];
					
					var mytitle:String="";
             		mytitle=trafficresults.title;
             		
             		var mytraffic:String="";
             		mytraffic= "--> "+ trafficresults.title;             		
             		trafficlabel = new Label();
             		trafficlabel.text= mytraffic;
             		trafficlabel.id= "traffic"+i;
             		
             		
             		fulltrafficdetails.addItem(mytraffic);
             		        		             		
             		
             		
             		if(Application.application.trafficId.sourcetrafficdetails.getChildren().length <=res.length)
             		{
             		
             			Application.application.trafficId.sourcetrafficdetails.addChild(trafficlabel);
              		}
                     
             		/*if(Application.application.trafficId.destrafficdetails.getChildren().length <=0 && count!=1)
             		{
             			
             			Application.application.trafficId.destrafficdetails.addChild(trafficlabel);
              		 }*/
              		 
           			 var content:String= trafficresults.description +
           			 					"Severity: "+ trafficresults.severity;
			
           			positionmarker = createMarker(mytitle,content,trafficresults.latlon,trafficresults.type); 			
				
				}
			
						
			}
		
			 private function createMarker(mytitle:String,content:String,position:LatLon,type:String):Marker
        	{
        		var ltln:LatLng= new LatLng(position.lat,position.lon);
				
				if(type=="I")
				{
        			var img:Bitmap = new Caution() as Bitmap;			
        		}
        		else if(type=="C")
        		{
        			var img:Bitmap = new Construction() as Bitmap;
        		}
        		
        		
              	positionmarker = new Marker(ltln , new MarkerOptions({fillStyle: {color:AppConstants.color} , icon:img }));
              	positionmarker.addEventListener(MapMouseEvent.CLICK,function(e:MapMouseEvent):void{          	
                     
                     var marker:Marker= e.target as Marker;
                          	
              		marker.openInfoWindow(new InfoWindowOptions({title:mytitle,contentHTML: content, padding:13}));
                          			});
                          			
                  Application.application.map.addOverlay(positionmarker);
                  
					return positionmarker;
            }
                      
			
			private function handletrafficloadFail(event:TrafficSearchEvent):void
			{
				Alert.show("Fail");
			}  
		 

	}
}