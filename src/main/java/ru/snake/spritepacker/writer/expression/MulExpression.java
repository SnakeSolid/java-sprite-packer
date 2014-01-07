package ru.snake.spritepacker.writer.expression;

import ru.snake.spritepacker.writer.expression.value.Value;

public class MulExpression implements Expression {

	private final Expression left;
	private final Expression right;

	public MulExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public Value getValue() {
		Value leftValue = left.getValue();
		Value rightValue = right.getValue();
		Value result;

		if (leftValue.isInteger() && rightValue.isInteger()) {
			int opResult = leftValue.getInteger() * rightValue.getInteger();

			result = new Value(opResult);
		} else if (leftValue.isString() && rightValue.isInteger()) {
			StringBuilder builder = new StringBuilder();
			String leftString = leftValue.getString();
			int count = rightValue.getInteger();

			for (int i = 0; i < count; i++) {
				builder.append(leftString);
			}

			result = new Value(builder.toString());
		} else {
			throw EvaluationException
					.create("Multiplication operands can not be string.");
		}

		return result;
	}

	public void visit(ExpressionVisitor visitor) {
		visitor.visitMul(this);

		left.visit(visitor);
		right.visit(visitor);
	}

	@Override
	public String toString() {
		StringBuilder concat = new StringBuilder();

		concat.append(left.toString());
		concat.append(" * ");
		concat.append(right.toString());

		return concat.toString();
	}

}
