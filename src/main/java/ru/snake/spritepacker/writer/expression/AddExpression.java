package ru.snake.spritepacker.writer.expression;

import ru.snake.spritepacker.writer.expression.value.Value;

public class AddExpression implements Expression {

	private final Expression left;
	private final Expression right;

	public AddExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public Value getValue() {
		Value leftValue = left.getValue();
		Value rightValue = right.getValue();
		Value result;

		if (leftValue.isInteger() && rightValue.isInteger()) {
			int opResult = leftValue.getInteger() + rightValue.getInteger();

			result = new Value(opResult);
		} else {
			StringBuilder concat = new StringBuilder();

			concat.append(leftValue.getString());
			concat.append(rightValue.getString());

			result = new Value(concat.toString());
		}

		return result;
	}

	public void visit(ExpressionVisitor visitor) {
		visitor.visitAdd(this);

		left.visit(visitor);
		right.visit(visitor);
	}

	@Override
	public String toString() {
		StringBuilder concat = new StringBuilder();

		concat.append(left.toString());
		concat.append(" + ");
		concat.append(right.toString());

		return concat.toString();
	}

}
