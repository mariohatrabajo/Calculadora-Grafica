package pruebas;

import java.util.ArrayList;

public class LectorFunciones {

	public static void main(String[] args) {
//		conSplit();
		System.out.println(eval("abs-1"));
	}

	public static void conSplit() {
		final String funcion = "1 + 1";

		funcion.substring(funcion.indexOf("("), funcion.lastIndexOf(")"));

		final String[] partes = funcion.split(" ");

		double rdo = 0;
		boolean primerNumero = false;

		// Lectura
		for (int i = 0; i < partes.length; i++) {
			String p = partes[i];

//			System.out.println(p);

			switch (p) {
			case "+":

				break;
			case "-":

				break;
			case "*":

				break;
			case "/":

				break;
			case "sen":

				break;
			default:
				if (Character.isDigit(p.toCharArray()[0])) {
					if (!primerNumero) {
						primerNumero = true;
						rdo = Double.parseDouble(p);
					}
				} else {

				}
			}
		}
		System.out.println("Resultado = " + rdo);
	}

	public static void sinSplit() {
		final String funcion = "1.22 + 10";

		ArrayList<String> partes = new ArrayList<String>();
		for (int i = 0; i < funcion.length(); i++) {
			partes.add(funcion.charAt(i) + "");
		}

		// Lectura
		for (String s : partes) {
			System.out.print(s + "\t");

			switch (s) {
			case "+":

				break;
			case "-":

				break;
			case "*":

				break;
			case "/":

				break;
			default:
				if (Character.isDigit(s.toCharArray()[0])) {

				}
			}
		}
	}

	//Código encontrado en internet, creado por Boann en https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
	public static double eval(final String str) {
		return new Object() {
			int pos = -1, ch;

			void nextChar() {
				ch = (++pos < str.length()) ? str.charAt(pos) : -1;
			}

			boolean eat(int charToEat) {
				while (ch == ' ')
					nextChar();
				if (ch == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			double parse() {
				nextChar();
				double x = parseExpression();
				if (pos < str.length())
					throw new RuntimeException("Unexpected: " + (char) ch);
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			// | number | functionName factor | factor `^` factor

			double parseExpression() {
				double x = parseTerm();
				for (;;) {
					if (eat('+'))
						x += parseTerm(); // addition
					else if (eat('-'))
						x -= parseTerm(); // subtraction
					else
						return x;
				}
			}

			double parseTerm() {
				double x = parseFactor();
				for (;;) {
					if (eat('*'))
						x *= parseFactor(); // multiplication
					else if (eat('/'))
						x /= parseFactor(); // division
					else
						return x;
				}
			}

			double parseFactor() {
				if (eat('+'))
					return parseFactor(); // unary plus
				if (eat('-'))
					return -parseFactor(); // unary minus

				double x;
				int startPos = this.pos;
				if (eat('(')) { // parentheses
					x = parseExpression();
					eat(')');
				} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
					while ((ch >= '0' && ch <= '9') || ch == '.')
						nextChar();
					x = Double.parseDouble(str.substring(startPos, this.pos));
				} else if (ch >= 'a' && ch <= 'z') { // functions
					while (ch >= 'a' && ch <= 'z')
						nextChar();
					String func = str.substring(startPos, this.pos);
					x = parseFactor();
					if (func.equals("sqrt"))
						x = Math.sqrt(x);
					else if (func.equals("sin"))
						x = Math.sin(Math.toRadians(x));
					else if (func.equals("cos"))
						x = Math.cos(Math.toRadians(x));
					else if (func.equals("tan"))
						x = Math.tan(Math.toRadians(x));

					else if (func.equals("abs"))
						x = Math.abs(x);
					else
						throw new RuntimeException("Unknown function: " + func);
				} else {
					throw new RuntimeException("Unexpected: " + (char) ch);
				}

				if (eat('^'))
					x = Math.pow(x, parseFactor()); // exponentiation

				return x;
			}
		}.parse();
	}

}

/*
 * public int eval(String infix) { create a list of all the elements identify
 * which operations you would want to do first perform the operations and
 * simplify the list (e.g. if 5x4 were inside parantheses, remove the
 * parantheses and replace it overall with 20.) continue the simplification
 * until you have a final result return the result }
 */