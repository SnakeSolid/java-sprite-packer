package ru.snake.spritepacker.writer.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import ru.snake.spritepacker.writer.expression.value.Value;

public class TextExpression implements Expression {

	private final Collection<Expression> expressions;

	public TextExpression() {
		expressions = new LinkedList<Expression>();
	}

	public Value getValue() {
		StringBuilder sb = new StringBuilder();

		for (Expression each : expressions) {
			Value value = each.getValue();

			sb.append(value.getString());
		}

		return new Value(sb.toString());
	}

	public void addContant(String value) {
		ConstantExpression expression = new ConstantExpression(value);

		expressions.add(expression);
	}

	public void addExpression(Expression expression) {
		expressions.add(expression);
	}

	public void visit(ExpressionVisitor visitor) {
		visitor.visitText(this);

		for (Expression each : expressions) {
			each.visit(visitor);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Expression> eachIt = expressions.iterator();
		Expression next;

		if (eachIt.hasNext()) {
			next = eachIt.next();

			builder.append(next.toString());

			while (eachIt.hasNext()) {
				next = eachIt.next();

				builder.append(", ");
				builder.append(next.toString());
			}
		}

		return builder.toString();
	}

}
