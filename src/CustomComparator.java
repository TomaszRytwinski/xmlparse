import java.util.Comparator;

public class CustomComparator implements Comparator<TypeCode> {
	@Override
	public int compare(TypeCode o1, TypeCode o2) {
		return o1.typeCodeProp.get(0).compareTo(o2.typeCodeProp.get(0));
	}

}
