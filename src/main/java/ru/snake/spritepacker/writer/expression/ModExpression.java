package ru.snake.spritepacker.writer.expression;

import ru.snake.spritepacker.writer.expression.value.Value;

public class ModExpression implements Expression {

	private final Expression left;
	private final Expression right;

	public ModExpression(Expression left, Expression right) {
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
				throw EvaluationException.create("Module division by zero.");
			}

			int opResult = leftValue.getInteger() % rightInt;

			result = new Value(opResult);
		} else {
			throw EvaluationException
					.create("Module division operands can not be string.");
		}

		return result;
	}

	public void visit(ExpressionVisitor visitor) {
		visitor.visitMod(this);

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
