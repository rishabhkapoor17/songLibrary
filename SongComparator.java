//Rishabh Kapoor 
//Mina Barsoum
//Sesh Venugopal
//Software Methodology

package songLib;

import java.util.Comparator;

public class SongComparator implements Comparator<Song>{

	@Override
	public int compare(Song song1, Song song2) 
	{
		if(song1.getName().toLowerCase().compareTo(song2.getName().toLowerCase())!=0)
		{
			return song1.getName().toLowerCase().compareTo(song2.getName().toLowerCase());
		}
		else
		{
			return song1.getArtist().toLowerCase().compareTo(song2.getArtist().toLowerCase());
		}
	}
}