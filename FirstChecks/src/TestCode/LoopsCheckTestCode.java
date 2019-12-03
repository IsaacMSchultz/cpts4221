package TestCode;

public class LoopsCheckTestCode {
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			do {
				i = i + 11;
				System.out.println("Heyo");
				for (int j = 0; j < 10; j++) {
					i--;
				}
			} while (i < 800);
		}
		// for (int j = 0; j < 10; j++){
		// System.out.println("Heyo");
		// }
		/*
		 * int test = 15; while (test >= 0){ test--; }
		 */
	}

	public static void forLoop() {
		for (int i = 0; i < 10; i++) {
			System.out.println("Just existing for loop");
		}
	}

	public static void whileLoop() {
		// infinite loop
		while (true) {
			System.out.println("While loop");
		}
	}

	public static void doWhileLoop() {
		do {
			System.out.println("Just existing do while loop");
			break;
		} while (true);
	}
}
