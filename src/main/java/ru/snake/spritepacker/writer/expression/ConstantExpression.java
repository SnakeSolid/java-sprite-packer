package ru.snake.spritepacker.writer.expression;

import ru.snake.spritepacker.writer.expression.value.Value;

public class ConstantExpression implements Expression {

	private final Value value;

	public ConstantExpression() {
		this.value = new Value();
	}

	public ConstantExpression(String value) {
		this.value = new Value(value);
	}

	public Value getValue() {
		return value;
	}

	public void visit(ExpressionVisitor visitor) {
		visitor.visitConstant(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (value == null) {
			builder.append("[CONST]");
		} else if (value.isInteger()) {
			builder.append(value.getString());
		} else {
			builder.append('"');
			builder.append(value.getString());
			builder.append('"');
		}

		return builder.toString();
	}

}
