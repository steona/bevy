package components
{
	import com.google.maps.Color;
	import com.google.maps.LatLng;
	import com.google.maps.overlays.Marker;
	import com.google.maps.overlays.MarkerOptions;
	import com.google.maps.services.Directions;
	import com.google.maps.services.DirectionsEvent;
	
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.events.*;
	
	public class GoogleMap
	{
		private var source_addr:String;
		private var des_addr:String;
		
		public function GoogleMap(){
		}
		/**
		 * Populate map and directions, this methode called from AddressForm.mxml
		 */ 	  
		public function getdirections(start_addr:String,dest_addr:String):void{
			this.source_addr= start_addr;
			this.des_addr 	= dest_addr;
			
			// Create a directions object
  			var dir:Directions = new Directions();
  			dir.addEventListener(DirectionsEvent.DIRECTIONS_SUCCESS, onDirLoad);
  			dir.addEventListener(DirectionsEvent.DIRECTIONS_FAILURE, onDirFail);
  			
  			dir.load("from: " + this.source_addr + " to: " + this.des_addr); 			
 		}	
		/**
		 * Failure condition
		 */ 
		private function onDirFail(event:DirectionsEvent):void {
 			
 		if(event.directions.status== 602)
 		Alert.show("No corresponding geographic location could be " + 
     		"found for one of the specified addresses. " + 
     		"This may be due to the fact that the " + 
     		"address is relatively new, or it may be incorrect.\nError code: "+ event.directions.status);
        
        else if(event.directions.status== 601)
   		Alert.show("An empty address was specified as an input.\nError code: "+ event.directions.status);
 			
 		else
 		{	
   		if(event.directions.status== 400)
   			 Alert.show("A directions request could not be successfully parsed.\nError code:"+ event.directions.status);
   		
   		if(event.directions.status== 500)
   			Alert.show("A geocoding, directions or maximum zoom level request " + 
   					"could not be successfully processed, yet the exact reason for the " + 
   					"failure is not known.\nError code: "+ event.directions.status);
   		
   		
   		if(event.directions.status==603)
   		Alert.show("The geocode for the given address or the " + 
   				"route for the given directions query cannot be returned due " + 
   				"to legal or contractual reasons..\nError code: "+ event.directions.status);
   		
   		if(event.directions.status==604)
   		Alert.show("not have data for routing in this region.\nError code: "+ event.directions.status);

		if(event.directions.status==610)
		Alert.show("The given key is either invalid or does not match " + 
				"the domain for which it was given.\nError code: "+ event.directions.status);   		
   		
   		if(event.directions.status==620)
   		Alert.show("The given key has gone over the requests " + 
   				"limit in the 24 hour period or has submitted too many " + 
   				"requests in too short a period of time.\nError code: "+ event.directions.status);
   		}
     				 			
 		  	Application.application.step.htmlText="";
		}
	
		/**
		 * Load all directions on UI when Success
		 */ 
		public function onDirLoad(event:DirectionsEvent):void {
  			
  			var dir:Directions = event.directions;
   			var startMarker:Marker;
  			var endMarker:Marker;
   			   			
   			var stepText:String;
 		 	var stepMarker:Marker;
 		 	var turnCounter:uint=0;   			
   			   			
   			//Application.application.map.addOverlay(startMarker);
   			Application.application.map.clearOverlays();
 			Application.application.map.addOverlay(dir.createPolyline());
			Application.application.map.setZoom(Application.application.map.getBoundsZoomLevel(dir.bounds));
			Application.application.map.setCenter(dir.bounds.getCenter());
			startMarker = new Marker(dir.getRoute(0).startGeocode.point, new MarkerOptions({fillStyle: {color:Color.GREEN}}));
			endMarker= new Marker(dir.getRoute(0).endGeocode.point, new MarkerOptions({fillStyle: {color:Color.BLUE}}));
			
			Application.application.map.addOverlay(startMarker);
			Application.application.map.addOverlay(endMarker);
			
			
			Application.application.directionpanel.visible=true;
			Application.application.step.htmlText="Start at " +this.source_addr;
	 
	 	    TripPlannerApp.latlon_route= new Array();
		
	 		while (turnCounter <dir.getRoute(0).numSteps) {
   		 		stepText = dir.getRoute(0).getStep(turnCounter).descriptionHtml;
   		 		var latlng:LatLng=  dir.getRoute(0).getStep(turnCounter).latLng;
   		 		 var lat:Number= latlng.lat();
   		 		 var lon:Number=latlng.lng(); 
   		 		
   		 		components.TripPlannerApp.latlon_route.push(new LatLonRoute(lat,lon));
   		 		
   		 		 		
   		 		 		
   		 		Application.application.step.htmlText+="<br>"+"Step " + (turnCounter+1) + ": " + stepText; 
   		 		turnCounter++;
 		 	} 
 			Application.application.step.htmlText+="<br>"+"Arrive at " + this.des_addr + " : " + dir.getRoute(0).summaryHtml;
  			/* for(var i:uint = 0; i < TripPlannerApp.latlon_route.length; i++) {
  			trace(TripPlannerApp.latlon_route[i].lat); 
  			}*/
  		
  		}
  	 		 
	}
}
		
		