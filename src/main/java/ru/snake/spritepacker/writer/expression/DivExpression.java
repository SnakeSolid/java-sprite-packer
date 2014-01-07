package ru.snake.spritepacker.writer.expression;

import ru.snake.spritepacker.writer.expression.value.Value;

public class DivExpression implements Expression {

	private final Expression left;
	private final Expression right;

	public DivExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public Value getValue() {
		Value leftValue = left.getValue();
		Value rightValue = right.getValue();
		Value result;

		if (leftValue.isInteger() && rightValue.isInteger()) {
			int rightInt = rightValue.getInteger();

			if (rightInt == 0) {
				throw EvaluationException.create("Division by zero.");
			}

			int opResult = leftValue.getInteger() / rightInt;

			result = new Value(opResult);
		} else {
			throw EvaluationException
					.create("Division operands can not be string.");
		}

		return result;
	}

	public void visit(ExpressionVisitor visitor) {
		visitor.visitDiv(this);

		left.visit(visitor);
		right.visit(visitor);
	}

	@Override
	public String toString() {
		StringBuilder concat = new StringBuilder();

		concat.append(left.toString());
		concat.append(" / ");
		concat.append(right.toString());

		return concat.toString();
	}

}
