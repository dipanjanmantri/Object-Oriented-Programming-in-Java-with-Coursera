package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;
import processing.core.PShape;
//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<SimplePointMarker> markers = new ArrayList<SimplePointMarker>();
	    //List<PointFeature> features=new ArrayList<PointFeature>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    //markers=MapUtils.createSimpleMarkers(earthquakes);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getLocation());
	    	System.out.println();
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	System.out.println(magObj);
	    	float mag = Float.parseFloat(magObj.toString());
	    	System.out.println(mag+1.0);
	    	// PointFeatures also have a getLocation method
	    }
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    MapUtils.createDefaultEventDispatcher(this, map);
	    
	    for(PointFeature f:earthquakes)
	    {
	    	markers.add(createMarker(f));
	    }
	    
	    for(SimplePointMarker m:markers)
	    {
	    	map.addMarker(m);
	    }
	     
	    for(SimplePointMarker m:markers)
	    {
	    	m.setRadius(10);
	    }
	    
	    Float[] mag=new Float[earthquakes.size()];
	    int c=0;
	    for(PointFeature f:earthquakes)
	    {
	    	Object obj=f.getProperty("magnitude");
	    	float val=Float.parseFloat(obj.toString());
	    	mag[c]=val;
	    	c++;
	    	
	    }
	    System.out.println();
	    int c1=0;
	    int c2=0;
	    
	    for(SimplePointMarker m:markers)
	    {
	    	
		    float f=mag[c1];
		    if(f<=4.0)
		    {
		    	m.setRadius(5);
		    	m.setColor(color(0,0,255));
		    }
		    else if(f>=4.0 & f<4.9)
	    	{
	    		m.setRadius(10);
	    		m.setColor(color(255,255,0));
	    	}
		    else if(f>=5.0)
	    	{
	    		m.setRadius(15);
	    		m.setColor(color(255,0,0));
	    	}
	    	c1++;
	    	
	    }
	    
	    
	   
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	
	
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addkey();
	}

	private void addkey() {
	    String s1="Earthquakes Key";
	   
	    
	    
	    rect(20,50,140,250);
	    fill(0);
	    text(s1,30,70);
	    fill(255);
	    
	    ellipse(35,90,10,10);
	    
	    
	    
		
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	

		
		
	
	
}
