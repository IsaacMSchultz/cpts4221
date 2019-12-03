package TestCode;

public class LocalMethodsCheckTestCode {

	@Override
	public String toString() {
		return "dumbo";
	}

	public void theMethod() {
		double d = Math.cos(Math.PI);
		// Math.cos(Math.PI);
		LocalMethodsCheckTestCode myMain = new LocalMethodsCheckTestCode();
		// Local method reference
		String test = this.toString();
		secondMethod();
		// External method reference
		System.out.println(test);
	}

	private void secondMethod() {
		System.out.println("Hi");
	}
}
