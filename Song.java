//Rishabh Kapoor 
//Mina Barsoum
//Sesh Venugopal
//Software Methodology

package songLib;

public class Song {

	private String artist;
	private String name;
	private String album;
	private int year;
	
	public Song(String name, String artist, String album, int year)
	{
		this.artist=artist;
		this.name=name;
		this.album=album;
		this.year=year;
	}
	
	public int getYear()
	{
		return year;
	}
	public String getArtist()
	{
		return artist;
	}
	public String getName()
	{
		return name;
	}
	public String getAlbum()
	{
		return album;
	}
	
	public void setYear(int year)
	{
		this.year=year;
	}
	
	public void setAlbum(String album)
	{
		this.album=album;
	}
	
	public void setArtist(String artist)
	{
		this.artist=artist;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	
}
