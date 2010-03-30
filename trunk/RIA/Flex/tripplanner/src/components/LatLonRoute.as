package components
{
	public class LatLonRoute
	{
		public var lat:Number;
		public var lon:Number;
		
		public function LatLonRoute(lat:Number,lon:Number)
		{ 
			this.lat=lat;
			this.lon=lon;
		}
        public function toString():String {
        return " " + lat + ":" + lon;
        }
	}
}