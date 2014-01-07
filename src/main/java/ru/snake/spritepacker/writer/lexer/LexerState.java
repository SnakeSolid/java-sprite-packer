package ru.snake.spritepacker.writer.lexer;

public enum LexerState {

	TEXT,

	START_EXPRESSION,

	WHITE_SPACE, OPERATION, VARIABLE, NUMBER,

	END_EXPRESSION,

}
