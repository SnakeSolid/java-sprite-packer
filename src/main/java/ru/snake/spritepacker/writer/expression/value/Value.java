package ru.snake.spritepacker.writer.expression.value;

public class Value {

	private String value;

	public Value() {
		this("");
	}

	public Value(int value) {
		this.value = String.valueOf(value);
	}

	public Value(String value) {
		this.value = value;
	}

	public boolean isInteger() {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public boolean isString() {
		return true;
	}

	public int getInteger() {
		return Integer.parseInt(value);
	}

	public String getString() {
		return value;
	}

	public void setInteger(int value) {
		this.value = String.valueOf(value);
	}

	public void setString(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
