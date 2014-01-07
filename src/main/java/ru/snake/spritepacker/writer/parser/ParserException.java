package ru.snake.spritepacker.writer.parser;

import ru.snake.spritepacker.writer.lexer.Token;

@SuppressWarnings("serial")
public class ParserException extends Error {

	private ParserException(String message) {
		super(message);
	}

	public static ParserException create(String state, Token token) {
		StringBuilder sb = new StringBuilder();

		sb.append("In state ");
		sb.append(state);
		sb.append(" unexpected token ");
		sb.append(token.type);
		sb.append(" in position ");
		sb.append(token.position);
		sb.append('.');

		return new ParserException(sb.toString());
	}

}
