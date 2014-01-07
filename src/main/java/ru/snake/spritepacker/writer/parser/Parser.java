package ru.snake.spritepacker.writer.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ru.snake.spritepacker.writer.expression.AddExpression;
import ru.snake.spritepacker.writer.expression.ConstantExpression;
import ru.snake.spritepacker.writer.expression.DivExpression;
import ru.snake.spritepacker.writer.expression.Expression;
import ru.snake.spritepacker.writer.expression.MulExpression;
import ru.snake.spritepacker.writer.expression.SubExpression;
import ru.snake.spritepacker.writer.expression.TextExpression;
import ru.snake.spritepacker.writer.expression.VariableExpression;
import ru.snake.spritepacker.writer.lexer.TokeType;
import ru.snake.spritepacker.writer.lexer.Token;

public class Parser {

	private final Queue<Token> tokens;

	private Token current;
	private Token next;

	public Parser(List<Token> tokens) {
		this.tokens = new LinkedList<Token>(tokens);
	}

	public Expression parse() {
		nextToken();

		return parseTextExpression();
	}

	private void nextToken() {
		current = tokens.poll();
		next = tokens.peek();
	}

	private Expression parseTextExpression() {
		TextExpression expression = new TextExpression();

		while (current.type != TokeType.EPSILON) {
			if (current.type == TokeType.TEXT) {
				expression.addContant(current.value);
			} else if (current.type == TokeType.START_EXPRESSION) {
				expression.addExpression(parseExpressionBraces());
			} else {
				throw ParserException.create("parseTextExpression", current);
			}

			nextToken();
		}

		return expression;
	}

	private Expression parseExpressionBraces() {
		if (current.type != TokeType.START_EXPRESSION) {
			throw ParserException.create("parseExpressionBraces", current);
		}

		nextToken();

		Expression node;

		if (current.type == TokeType.END_EXPRESSION) {
			node = new ConstantExpression("");
		} else if (next.type == TokeType.END_EXPRESSION) {
			node = parseExpressionValue();
		} else {
			node = parseExpressionNode();
		}

		if (current.type != TokeType.END_EXPRESSION) {
			throw ParserException.create("parseExpressionBraces", current);
		}

		return node;
	}

	private Expression parseExpressionNode() {
		Expression node;
		Expression value = parseExpressionValue();

		if (next.type == TokeType.END_EXPRESSION) {
			node = value;
		} else if (current.type == TokeType.OPERATION) {
			node = parseOperatorNode(value);
		} else {
			throw ParserException.create("parseExpressionNode", current);
		}

		return node;
	}

	private Expression parseOperatorNode(Expression left) {
		if (current.type != TokeType.OPERATION) {
			throw ParserException.create("parseOperatorNode", current);
		}

		String operation = current.value;

		Expression node;

		nextToken();

		if (operation.equals("+")) {
			node = parseAddOperation(left);
		} else if (operation.equals("-")) {
			node = parseSubOperation(left);
		} else if (operation.equals("*")) {
			node = parseMulOperation(left);
		} else if (operation.equals("/")) {
			node = parseDivOperation(left);
		} else {
			throw ParserException.create("parseOperatorNode", current);
		}

		return node;
	}

	private Expression parseAddOperation(Expression left) {
		Expression node;
		Expression value = parseExpressionValue();

		boolean isOperition = current.type == TokeType.OPERATION;

		if (isOperition && current.value.equals("+")) {
			nextToken();

			value = new AddExpression(left, value);

			node = parseAddOperation(value);
		} else if (isOperition && current.value.equals("-")) {
			nextToken();

			value = new AddExpression(left, value);

			node = parseSubOperation(value);
		} else if (isOperition && current.value.equals("*")) {
			nextToken();

			Expression right = parseMulOperation(value);

			node = new AddExpression(left, right);
		} else if (isOperition && current.value.equals("/")) {
			nextToken();

			Expression right = parseDivOperation(value);

			node = new AddExpression(left, right);
		} else {
			node = new AddExpression(left, value);
		}

		return node;
	}

	private Expression parseSubOperation(Expression left) {
		Expression node;
		Expression value = parseExpressionValue();

		boolean isOperition = current.type == TokeType.OPERATION;

		if (isOperition && current.value.equals("+")) {
			nextToken();

			value = new SubExpression(left, value);

			node = parseAddOperation(value);
		} else if (isOperition && current.value.equals("-")) {
			nextToken();

			value = new SubExpression(left, value);

			node = parseSubOperation(value);
		} else if (isOperition && current.value.equals("*")) {
			nextToken();

			Expression right = parseMulOperation(value);

			node = new SubExpression(left, right);
		} else if (isOperition && current.value.equals("/")) {
			nextToken();

			Expression right = parseDivOperation(value);

			node = new SubExpression(left, right);
		} else {
			node = new SubExpression(left, value);
		}

		return node;
	}

	private Expression parseMulOperation(Expression left) {
		Expression node;
		Expression value = parseExpressionValue();

		boolean isOperition = current.type == TokeType.OPERATION;

		if (isOperition && current.value.equals("+")) {
			nextToken();

			value = new MulExpression(left, value);

			node = parseAddOperation(value);
		} else if (isOperition && current.value.equals("-")) {
			nextToken();

			value = new MulExpression(left, value);

			node = parseSubOperation(value);
		} else if (isOperition && current.value.equals("*")) {
			nextToken();

			value = new MulExpression(left, value);

			node = parseMulOperation(value);
		} else if (isOperition && current.value.equals("/")) {
			nextToken();

			value = new MulExpression(left, value);

			node = parseDivOperation(value);
		} else {
			node = new MulExpression(left, value);
		}

		return node;
	}

	private Expression parseDivOperation(Expression left) {
		Expression node;
		Expression value = parseExpressionValue();

		boolean isOperition = current.type == TokeType.OPERATION;

		if (isOperition && current.value.equals("+")) {
			nextToken();

			value = new DivExpression(left, value);

			node = parseAddOperation(value);
		} else if (isOperition && current.value.equals("-")) {
			nextToken();

			value = new DivExpression(left, value);

			node = parseSubOperation(value);
		} else if (isOperition && current.value.equals("*")) {
			nextToken();

			value = new DivExpression(left, value);

			node = parseMulOperation(value);
		} else if (isOperition && current.value.equals("/")) {
			nextToken();

			value = new DivExpression(left, value);

			node = parseDivOperation(value);
		} else {
			node = new DivExpression(left, value);
		}

		return node;
	}

	private Expression parseExpressionValue() {
		Expression node = null;

		if (current.type == TokeType.NUMBER) {
			node = new ConstantExpression(current.value);
		} else if (current.type == TokeType.VARIABLE) {
			node = new VariableExpression(current.value);
		} else {
			throw ParserException.create("parseExpressionValue", current);
		}

		nextToken();

		return node;
	}

}
