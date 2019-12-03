package TestCode;

public class HalsteadMetricsCheckTestCode {
	private int globalVariable = 0;

	public static void main(String[] args) {
	}

	public void firstMethod() {
		int first = switchStatement(10);
		int second = switchStatement(20);
		int third = switchStatement(15);
	}

	/*
	 * expression = 5 + 5 + 5; I love writing stuff globalVariable = 47 % 5;
	 */
	public void ifStatement() {
		globalVariable = 1;
		globalVariable = 2;
		if (globalVariable > 0) {
			int a = 2 + 2;
			int b = 10;
			b = a + 4;
			if (b > 5) {
				a = 0;
			}
		}
	}

	// number = number + 54 / 6;
	public int switchStatement(int number) {
		switch (number) {
		case 10:
			number++;
			break;
		case 20:
			number--;
			break;
		default:
			number = number + 3 / 35 % 3;
			break;
		}
		return number;
	}

	public void emptyMethod() {
	}

	public void checkOperators() {
		int value = 10000;
		value = value + 1000;
		value = value - 1000;
		value = value * 2;
		value = value / 2;
		value++;
		value--;

		value += value;
		value -= value;
		value *= 2;
		value /= 2;
		value ^= 2;
	}

	/*
	 * Testing lines of comment percentage
	 */
	public void checkComparisonOps() {
		int x = 3;
		int y = 4;
		if (x < y) {
			System.out.println("1");
		}
		if (y > x) {
			System.out.println("2");
		}
		if (y == (x + 1)) {
			System.out.println("3");
		}
		if (x != y) {
			System.out.println("4");
		}
		if (x <= y) {
			System.out.println("5");
		}
		if (y >= x) {
			System.out.println("6");
		}
		if (y >= x) {
			System.out.println("6");
		}
		if (y > x && x < y) {
			System.out.println("7");
		}
		if (y > x || x < y) {
			System.out.println("8");
		}
		if (!(x > y)) {
			System.out.println("9");
		}
	}
}
