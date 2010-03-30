package components
{
	
	import mx.core.Application;
	import components.TripPlannerApp;
	import constants.AppConstants;
	import mx.controls.Alert;
	
	import mx.rpc.http.HTTPService;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.events.FaultEvent;


	public class Weather
	{
		private var httpService:HTTPService = null; 
		
		public function updateWeather():void{
			if(TripPlannerApp.sourceAddr != ""){
				if(httpService==null){
					this.httpService = new HTTPService(); //if null then create object
				}
				httpService.url = AppConstants.googleWeatherApiUrl+escape(TripPlannerApp.sourceAddr);
				httpService.method = "GET";
			    httpService.resultFormat = "xml";
			    httpService.addEventListener(ResultEvent.RESULT, httpStartAddressHandler);
				try{
					httpService.send();	
				}catch(error:Error){
					
				}
			}
			if(TripPlannerApp.destAddr != ""){
				httpService=null;
				if(httpService==null){
					this.httpService = new HTTPService(); //if null then create object
				}
				httpService.url = AppConstants.googleWeatherApiUrl+escape(TripPlannerApp.destAddr);
				httpService.method = "GET";
			    httpService.resultFormat = "xml";
			    httpService.addEventListener(ResultEvent.RESULT, httpDestinationAddressHandler);
				try{
					httpService.send();	
				}catch(error:Error){
					
				}
			}	
			
			
		}
		/**
		 * Handler which will responsible for Display Start Address details
		 */ 
		private function httpStartAddressHandler(event:ResultEvent):void{
			var httpService:HTTPService = event.target as HTTPService;
			httpService.removeEventListener(ResultEvent.RESULT, httpStartAddressHandler);
			var str:String = httpService.lastResult.toString();
			
			var xmlRes:XML = new XML(str);
			
			//Set Values on Wather view
			//Start
			Application.application.weatherId.s_addr.text = xmlRes.weather.forecast_information.postal_code.attribute("data");
			var strImagePath:String = AppConstants.google+xmlRes.weather.current_conditions.icon.attribute("data");
			Application.application.weatherId.s_image.load(strImagePath);
			Application.application.weatherId.s_condition.htmlText = "Current Condition: <br>"
												+xmlRes.weather.current_conditions.condition.attribute("data")
												+", "
												+xmlRes.weather.current_conditions.temp_f.attribute("data")
												+" F";
			//Destination
			
		}
			
		private function httpDestinationAddressHandler(event:ResultEvent):void{
			var httpService:HTTPService = event.target as HTTPService;
			httpService.removeEventListener(ResultEvent.RESULT, httpDestinationAddressHandler);
			var str:String = httpService.lastResult.toString();
			
			var xmlRes:XML = new XML(str);
			
			//Set Values on Wather view
			//Destination
			Application.application.weatherId.d_addr.text = xmlRes.weather.forecast_information.postal_code.attribute("data");
			var strImagePath:String = AppConstants.google+xmlRes.weather.current_conditions.icon.attribute("data");
			Application.application.weatherId.d_image.load(strImagePath);
			Application.application.weatherId.d_condition.htmlText = "Current Condition: <br>"
												+xmlRes.weather.current_conditions.condition.attribute("data")
												+", "
												+xmlRes.weather.current_conditions.temp_f.attribute("data")
												+" F";
			
		}
	}
}