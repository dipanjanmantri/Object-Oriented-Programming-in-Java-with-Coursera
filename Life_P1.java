package practice;


import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import java.util.HashMap;


import de.fhpotsdam.unfolding.marker.Marker;

public class Life_P1 extends PApplet{
	
	UnfoldingMap map;
	Map<String, Float> LifeExp;
	List<Feature> countries;
	List<Marker> countryMarkers;

	public void setup()
	{
		size(600,600,OPENGL);
		map=new UnfoldingMap(this,50,50,700,500,new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		LifeExp=loadLifeExpenctencyFromCSV("LifeExpectancyWorldBankModule3.csv");
		countries=GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers=MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		
		//shadecountries();
		
		
	}
	
	private void shadecountries() {
		for(Marker marker:countryMarkers)
		{
			String countryID=marker.getId();
			if(LifeExp.containsKey(countryID))
			{
				float lifeExp=LifeExp.get(countryID);
				int colorLevel = (int) map(lifeExp,40,90,10,255);
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(150,150,150));
			}
		}
		
	}

	private Map<String, Float> loadLifeExpenctencyFromCSV(String string) {
		// TODO Auto-generated method stub
		Map<String,Float> LifeExp=new HashMap<String, Float>();
		String[] rows=loadStrings(string);
		for(String row:rows)
		{
			String[] columns=row.split(",");
			if(columns.length == 6 && !columns[5].equals(".."))
			{
				float val=Float.parseFloat(columns[5]);
				LifeExp.put(columns[4],val);
				
			}
		}
		
		
		return LifeExp;
	}

	public void draw()
	{
		map.draw();
	}
	
	
	
}
