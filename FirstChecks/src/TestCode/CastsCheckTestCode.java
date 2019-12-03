package TestCode;

public class CastsCheckTestCode {
	public static void main(String[] args) {
		int a = 2;
		double d = (double) a; // double d = (double)a;
		a = (int) d;
		/*
		 * double d = (double)a;
		 */
		// implicit cast
		int i = 20;
		int j = 40;
		float k = i + j;
		// reflection cast
		Object o = "str";
		String tempStr = String.class.cast(o);
	}
}
