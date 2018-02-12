import java.util.Comparator;

public class CategoryComperator implements Comparator<Category> {
	@Override
	public int compare(Category o1, Category o2) {
		return o1.categoryProperties.get(0).compareTo(o2.categoryProperties.get(0));
	}

}
