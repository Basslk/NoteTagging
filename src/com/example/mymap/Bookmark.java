package com.example.mymap;

public class Bookmark {
	
	public Bookmark()
	{}
	
	public Bookmark(String header,double lat,double lon)
	{
		this.Name=header;
		this.lat=lat;
		this.lon=lon;
	}
	
	public Bookmark(String header,String deatils,double lat,double lon)
	{
		this.Name=header;
		this.Description=deatils;
		this.lat=lat;
		this.lon=lon;
	}

public int id;	
public String Name;
public String Description;
public double lat;
public double lon;

}
