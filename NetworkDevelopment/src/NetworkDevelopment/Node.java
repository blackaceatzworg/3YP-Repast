package NetworkDevelopment;
import java.util.ArrayList;


public class Node {
	String id;
	double weight;
	
	ArrayList<DataAttribute> attributeList;
	
	Node(String id)
	{
		this.id = id;
		this.weight = 1;
	}
	
	Node(String id, double weight)
	{
		this.id = id;
		this.weight = weight;
	}
	
	public void setAttributeList(ArrayList<DataAttribute> list)
	{
		this.attributeList = list;
	}
	
	public Object findAttribute(String key)
	{
		for(DataAttribute d: attributeList)
		{
			if(d.getKey() == key)
				return d.getValue();
		}
		return false;
	}
	
	public boolean addAttribute(String key, Object value)
	{
		for(DataAttribute d: attributeList)
		{
			if(d.getKey() == key)
				return false;
		}
		attributeList.add(new DataAttribute(key, value));
		return true;
	}
	
	public boolean changeAttribute(String key, Object value)
	{
		boolean changed = false;
		for(DataAttribute d: attributeList)
		{
			if(d.getKey() == key)
			{
				d.setKey(key);
				changed = true;
			}
		}
		return changed;
	}
	
	public boolean removeAttribute(String key)
	{
		int delIndex = -1, trackIndex = 0;
		for(DataAttribute d: attributeList)
		{
			if(d.getKey() == key)
				delIndex = trackIndex;
			
			trackIndex++;
		}
		if(delIndex >= 0)
		{
			attributeList.remove(delIndex);
			return true;
		}
		else
			return false;
	}
	
}
