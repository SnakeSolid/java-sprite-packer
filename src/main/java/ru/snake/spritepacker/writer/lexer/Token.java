package ru.snake.spritepacker.writer.lexer;

public class Token {

	public final TokeType type;
	public final String value;
	public final int position;

	public Token(int position) {
		this.position = position;

		type = TokeType.EPSILON;
		value = "";
	}

	public Token(TokeType type, String value, int position) {
		this.type = type;
		this.value = value;
		this.position = position;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(type);
		sb.append(" [");
		sb.append(value);
		sb.append("] at ");
		sb.append(position);

		return sb.toString();
	}

}
