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
		import com.yahoo.maps.webservices.local.LocalSearch;
		import com.yahoo.maps.webservices.local.LocalSearchItem;
		import com.yahoo.maps.webservices.local.LocalSearchResults;
		import com.yahoo.maps.webservices.local.events.LocalSearchEvent;
		
		import constants.AppConstants;
		
		import mx.core.Application;
		import mx.core.BitmapAsset;
	
		
	public class PointOfInterests 
	{
	 var poi_label:String;	
	 var lat:Number;
     var lon:Number;
     var startMarker:Marker;
     var markers:Array=[];
     
     var leng:int;
     var len:int;
     var searchResults:LocalSearchResults;
     var results:Array;
   // static  var poi_markers:Array= new Array();
     var im:ImagePointsofInterest= new ImagePointsofInterest();

       
     public function PointOfInterests(){}
		     
     public function getlat():Number
     {
     return this.lat;
     }
		
		
	public function setlat(lat:Number):void
		{
		this.lat=lat;
	   }
		
	
	public function getlon():Number
     {
     return this.lon;
     }
		
	public function setlon(lon:Number):void
		{
		this.lon=lon;
		}
		
  
		 
		
	
	public function assign_poi(label:String):void
	{
		this.poi_label=label;	
		}



       private var _localSearch:LocalSearch;	
	   private var _yahooMap:YahooMap;
		
		
		public function showpoints(label:String):void
		{
		
			assign_poi(label);
			var appid:String=AppConstants.yahooAppid;
		    _yahooMap = new YahooMap();
			_yahooMap.addEventListener(YahooMapEvent.MAP_INITIALIZE, handleMapInitialize);
			
		_yahooMap.init(appid,Application.application.mapContainer.width,Application.application.mapContainer.height);
			
		}
			
private function handleMapInitialize(event:YahooMapEvent):void  
{		
			
	   _yahooMap.centerLatLon = new LatLon(37.779160,-122.420049); // setting the center latlon over san francisco,ca
		var city=TripPlannerApp.destAddr;
		var address:Address = new Address(city);
			 	address.addEventListener(GeocoderEvent.GEOCODER_SUCCESS, handleGeocodeSuccess);					
				address.geocode();
		
}		


/**
* Local Search along the direction route 
*/

private function handleGeocodeSuccess(event:GeocoderEvent):void 
			{		
								
				var address:Address = (event.target as Address);				
				var result:GeocoderResult= address.geocoderResultSet.firstResult;
				var ltlng:LatLon = result.latlon;
				var lt:Number;
				var lo:Number;

			
			for(var i:uint = 0; i < TripPlannerApp.latlon_route.length; i++) {
				lt=components.TripPlannerApp.latlon_route[i].lat;	
				lo=components.TripPlannerApp.latlon_route[i].lon;
				
				_yahooMap.centerLatLon =new LatLon(lt,lo);
				_localSearch = new LocalSearch();
						
				_localSearch.addEventListener(LocalSearchEvent.SEARCH_SUCCESS,handleSearchSuccess);
			
				_localSearch.searchLocal(poi_label,5,_yahooMap.centerLatLon,15, 1, 5);

				}
			
			
			
	/* 		_yahooMap.centerLatLon = new LatLon(ltlng.lat,ltlng.lon);				
		  	_localSearch = new LocalSearch();
			_localSearch.addEventListener(LocalSearchEvent.SEARCH_SUCCESS,handleSearchSuccess);
		
		TripPlannerApp.destAddr  //"96926236"
		_localSearch.searchLocal(poi_label,5,_yahooMap.centerLatLon,25, 1, 25);
		 */
		
		}
		
		 private function handleSearchSuccess(event:LocalSearchEvent):void 
        {
            searchResults= event.data as LocalSearchResults;
            results=searchResults.results;
            len=results.length;
            leng=len;
			
            for(var i:int=0; i<len; i++) 
            {
                var item:LocalSearchItem= results[i];
              
               setlat(item.lat);
               setlon(item.lon);
             var mytitle:String="";
             mytitle=item.title;
                       
            var content:String= item.addr+
            					item.city+
             					item.state+
             					item.dphone;
           		startMarker= createMarker(mytitle,content); 
				markers[i]= startMarker;
				
	   		 	components.TripPlannerApp.poi_markers.push(new Poi_Markers(poi_label,markers[i]));
				
			
            }
            
                      	
        }  
        
        
        private function createMarker(mytitle:String,content:String):Marker
        {
              
          	
            
            var imgpoi:Class  = im.returnMarkerIcon(poi_label);
            var img:BitmapAsset = new imgpoi();
            
            

          
          startMarker = new Marker(new LatLng(getlat(),getlon()), 
          									new MarkerOptions({fillStyle: {color:AppConstants.color}, icon:img}));

              	
              	startMarker.addEventListener(MapMouseEvent.CLICK,function(e:MapMouseEvent):void{          	
                     
                     var marker:Marker= e.target as Marker;
                    var infowindowopt:InfoWindowOptions= new InfoWindowOptions();      	
              		infowindowopt.title= mytitle;
              		infowindowopt.contentHTML=content;
              		marker.openInfoWindow(infowindowopt);
              		
                          			});
                          			
                  Application.application.map.addOverlay(startMarker);
                  
                 return startMarker;
                 
           } 
		
		
		public function removeMarker(id_poi:String):void
		{
			assign_poi(id_poi);
			
			/* for(var i:uint = 0; i <poi_markers.length;i++)		
					Application.application.map.removeOverlay(poi_markers[i].poi_label as Marker);		
		 	 */	
			
			
			
			 for(var i:uint = 0; i <TripPlannerApp.poi_markers.length;i++)
			
				{
					 if(TripPlannerApp.poi_markers[i].poi_label==id_poi)
							Application.application.map.removeOverlay(TripPlannerApp.poi_markers[i].p_marker as Marker);
								
				 
							
		        } 
		
		
         }
       
   } //end of class
} //end of package