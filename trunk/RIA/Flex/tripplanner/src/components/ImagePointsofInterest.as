package components
{
	public class ImagePointsofInterest
	{   
		
		 [Embed(source="images/blue-pushpin.png")]
         [Bindable]
          public var Motelsicon:Class;
          
          [Embed(source="images/green2-pushpin.png")]
         [Bindable]
          public var Hotelsicon:Class;
          
          [Embed(source="images/lightblue-pushpin.png")]
         [Bindable]
          public var Hospitalsicon:Class;
          
          [Embed(source="images/orange-pushpin.png")]
         [Bindable]
          public var GasStationsicon:Class;
          
          [Embed(source="images/yellow-pushpin.png")]
         [Bindable]
          public var Starbucksicon:Class;
          
          [Embed(source="images/violet-pushpin.png")]
         [Bindable]
          public var ShoppingMallsicon:Class;
          
          [Embed(source="images/red-pushpin.png")]
         [Bindable]
          public var Theatresicon:Class;
          
          [Embed(source="images/green-pushpin.png")]
         [Bindable]
          public var Parksicon:Class;
          
          [Embed(source="images/blue-pushpin.png")]
         [Bindable]
          public var Restaurantsicon:Class;
          
          [Embed(source="images/lightblue-pushpin.png")]
         [Bindable]
          public var Airportsicon:Class;
          
          [Embed(source="images/pink-pushpin.png")]
         [Bindable]
          public var NightClubsicon:Class;
          
          [Embed(source="images/red-pushpin.png")]
         [Bindable]
          public var Festiveicon:Class;
          
          [Embed(source="images/orange-pushpin.png")]
         [Bindable]
          public var CarRentalsicon:Class;
          
          [Embed(source="images/violet-pushpin.png")]
         [Bindable]
          public var Winearyicon:Class;
                 
          
          [Embed(source="images/yellow2-pushpin.png")]
         [Bindable]
          public var SchoolsUnivicon:Class;
          
          
          
          [Embed(source="images/orange-pushpin.png")]
         [Bindable]
          public var Banksicon:Class;
		
		[Embed(source="images/default-pushpin.png")]
		[Bindable]
          public var defaulticon:Class;
		
				
		
		public function ImagePointsofInterest()
		{
			
		}
		public function returnMarkerIcon(poi_label:String):Class
		{
			
					
			
			
			if(poi_label=="motels")		
			return Motelsicon;
			
			else
			
			if(poi_label=="hotels")		
			return Hotelsicon;
			else
			
			if(poi_label=="hospitals")		
			return Hospitalsicon;
			
			else
			
			if(poi_label=="gas stations")		
			return GasStationsicon;
			
			else
			
			if(poi_label=="starbucks")		
			return Starbucksicon;
			
			else
			
			if(poi_label=="shopping malls")		
			return ShoppingMallsicon;
			
			else
			if(poi_label=="theaters")		
			return Theatresicon;
			
			else
			
			if(poi_label=="parks")		
			return Parksicon;
			
			else
			
			if(poi_label=="restaurants")		
			return Restaurantsicon;
			
			else
			
			if(poi_label=="airports")		
			return Airportsicon;
			
			else
			
			if(poi_label=="night clubs")		
			return NightClubsicon;
			
			else
			
			if(poi_label=="events")		
			return Festiveicon;
			
			else
			
			if(poi_label=="Rental Car")		
			return CarRentalsicon;
			
			else
			
			if(poi_label=="wine")		
			return Winearyicon;
			
			else
			
			if(poi_label=="schools")		
			return SchoolsUnivicon;
			
			else
			
			if(poi_label=="bank")		
			return Banksicon;
			
			
			
			
			return defaulticon;
			
		}

	}
}