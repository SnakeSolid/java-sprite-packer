package ru.snake.spritepacker.writer.expression;

import ru.snake.spritepacker.writer.expression.value.Value;

public class VariableExpression implements Expression {

	private final String name;

	private Value value;

	public VariableExpression(String name) {
		this.name = name;

		value = null;
	}

	public String getName() {
		return name;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public Value getValue() {
		if (value == null) {
			throw EvaluationException.create("Unknown variabe", name);
		}

		return value;
	}

	public void visit(ExpressionVisitor visitor) {
		visitor.visitVariable(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (value == null) {
			builder.append('[');
			builder.append(name);
			builder.append(']');
		} else {
			builder.append('[');
			builder.append(value);
			builder.append(']');
		}

		return builder.toString();
	}

}
