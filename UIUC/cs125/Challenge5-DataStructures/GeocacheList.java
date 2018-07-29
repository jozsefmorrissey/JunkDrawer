//UIUC CS125 FALL 2014 MP. File: GeocacheList.java, CS125 Project: Challenge5-DataStructures, Version: 2014-10-12T12:12:55-0500.784759526
/**
 * Complete the following GeocacheList, to ensure all unit tests pass.
 * There are several errors in the code below
 *
 * Hint: Get the Geocache class working and passing its tests first.
 * @author jtmorri2
 */
class GeocacheList {
	private Geocache[] data = new Geocache[0];
	private int size = 0;

	public Geocache getGeocache(int i) {
		return data[i];
	}

	public int getSize() {
		return size;
	}

	public GeocacheList() {
	}

	public GeocacheList(GeocacheList other, boolean deepCopy) {
		data = new Geocache[other.data.length];
		
		
		
		if(!deepCopy){
		for(int i=0; i<data.length; i++)
			data[i] = other.data[i];

		}
		else if(deepCopy){
			for(int i=0; i<other.getSize(); i++){
				double w = other.data[i].getX();
				double l = other.data[i].getY();
				Geocache data = new Geocache(w, l);
				this.data[i]=data;
			}
		}
			size = other.size;
	}

	public void add(Geocache p) {
		size++;
		if (size > data.length) {
			Geocache[] old = data;
			data = new Geocache[size + 2];
			for (int i = 0; i < old.length; i++)
				data[i] = old[i];
		}
		data[size-1] = p;
	}

	public void removeFromTop() {
		size--;
		Geocache[] old = data;
		for(int i=0; i<old.length; i++)
			old[i] = data[i];
		data=old;
		
	}

	public String toString() {
		StringBuffer s = new StringBuffer("GeocacheList:");
		for (int i = 0; i < size; i++) {
			if (i > 0)
				s.append(',');
			s.append(data[i]);
		}
		return s.toString();
}	}
