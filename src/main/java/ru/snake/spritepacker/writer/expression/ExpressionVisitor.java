package ru.snake.spritepacker.writer.expression;

public interface ExpressionVisitor {

	public void visitText(TextExpression expression);

	public void visitConstant(ConstantExpression expression);

	public void visitVariable(VariableExpression expression);

	public void visitAdd(AddExpression expression);

	public void visitSub(SubExpression expression);

	public void visitMul(MulExpression expression);

	public void visitDiv(DivExpression expression);

	public void visitMod(ModExpression expression);

}
