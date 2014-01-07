package ru.snake.spritepacker.writer.expression;

@SuppressWarnings("serial")
public class EvaluationException extends Error {

	private EvaluationException(String message) {
		super(message);
	}

	public static EvaluationException create(String message) {
		return new EvaluationException(message);
	}

	public static EvaluationException create(String message, String name) {
		StringBuilder builder = new StringBuilder();

		builder.append(message);
		builder.append(" [");
		builder.append(name);
		builder.append("].");

		return new EvaluationException(builder.toString());
	}

}
