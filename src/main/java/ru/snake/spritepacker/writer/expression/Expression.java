package ru.snake.spritepacker.writer.expression;

import ru.snake.spritepacker.writer.expression.value.Value;

public interface Expression {

	public Value getValue();

	public void visit(ExpressionVisitor visitor);

}
