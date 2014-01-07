package ru.snake.spritepacker.writer.expression;

import ru.snake.spritepacker.writer.expression.value.Value;

public class SubExpression implements Expression {

	private final Expression left;
	private final Expression right;

	public SubExpression(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public Value getValue() {
		Value leftValue = left.getValue();
		Value rightValue = right.getValue();
		Value result;

		if (leftValue.isInteger() && rightValue.isInteger()) {
			int sum = leftValue.getInteger() - rightValue.getInteger();

			result = new Value(sum);
		} else {
			throw EvaluationException
					.create("Substartion operands can not be string.");
		}

		return result;
	}

	public void visit(ExpressionVisitor visitor) {
		visitor.visitSub(this);

		left.visit(visitor);
		right.visit(visitor);
	}

	@Override
	public String toString() {
		StringBuilder concat = new StringBuilder();

		concat.append(left.toString());
		concat.append(" - ");
		concat.append(right.toString());

		return concat.toString();
	}

}
