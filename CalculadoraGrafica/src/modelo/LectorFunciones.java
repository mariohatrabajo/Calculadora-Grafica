package modelo;

import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class LectorFunciones {
	
	public static String funcion = "0";
	
	// Código encontrado en internet, creado por Boann en
	// https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
	// Y luego alterado por Mario para añadir las funciones de valor absoluto, raiz cuadrada. logaeritmos, exponenciales
	// y arreglé los elevado a
	public static double eval(final String str) throws RuntimeException {
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

			double parse() throws RuntimeException{
				nextChar();
				double x = parseExpression();
				if (pos < str.length()) {
					throw new RuntimeException("Eval:\nLa sintaxis no es correcta:\nCarácter desconocido "/* + (char) ch*/);
				}
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			// | number | functionName factor | factor `^` factor

			double parseExpression() throws RuntimeException{
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

			double parseTerm() throws RuntimeException {
				double x = parseFactor();
				for (;;) {
					if (eat('^'))  // exponentiation
						x = Math.pow(x, parseFactor());
					else if (eat('*'))
						x *= parseFactor(); // multiplication
					else if (eat('/'))
						x /= parseFactor(); // division
					else if (eat('%'))
						x %= parseFactor(); // Módulo
					else
						return x;
				}
			}

			double parseFactor() throws RuntimeException {
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
						x = Math.sin(x);
					else if (func.equals("cos"))
						x = Math.cos(x);
					else if (func.equals("tan"))
						x = Math.tan(x);

					else if (func.equals("abs"))
						x = Math.abs(x);
					else if (func.equals("log"))
						x = Math.log(x);
					else if (func.equals("logd"))
						x = Math.log10(x);
					else if (func.equals("exp"))
						x = Math.exp(x);
					else if (func.equals("fac"))
						x = factorial(x);
					else if (func.equals("rand")) {
						Random rd = new Random();
						x = rd.nextDouble()*x;
					}else {
						throw new RuntimeException("Eval:\nLa sintaxis no es correcta:\nFunción desconocida: " + func);
					}
				} else if (ch >= 'A' && ch <= 'Z') { // Constantes
					while (ch >= 'A' && ch <= 'Z')
						nextChar();
					String cons = str.substring(startPos, this.pos);
					if (cons.equals("PI"))
						x = Math.PI;
					else if (cons.equals("E"))
						x = Math.E;
					else if (cons.equals("C"))
						x = 3 * Math.exp(8);
					else if (cons.equals("G"))
						x = 6.67 * Math.exp(-11);
					else {
						throw new RuntimeException("Eval:\nLa sintaxis no es correcta:\nConstante desconocida: " + cons);
					}
				} else {
					throw new RuntimeException("Eval:\nLa sintaxis no es correcta:\nCarácter desconocido "/* + (char) ch*/);
				}

				return x;
			}
		}.parse();
	}
	
	//x!
	private static double factorial(double x) {
		double rdo = x;
		for(double i = x-1; i > 0; i--) {
			rdo *= i;
		}
		return rdo;
	}
}
