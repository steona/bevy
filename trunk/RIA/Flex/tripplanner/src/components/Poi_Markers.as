package components
{
	import com.google.maps.overlays.Marker;
	
	public class Poi_Markers
	{
		public var poi_label:String;
		public var p_marker:Marker;
		
		public function Poi_Markers(poi_label:String,p_marker:Marker)
		{
			this.poi_label=poi_label;
			this.p_marker=p_marker;
		}

	}
}
