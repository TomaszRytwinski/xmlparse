import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TypeCode {
	
	public List<String> typeCodeProp = new ArrayList<String>();
	public List<Category> category = new ArrayList<Category>();
	public void sortCategory(){
		Collections.sort(category, new CategoryComperator()); 
	}
	

}
