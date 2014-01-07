package ru.snake.spritepacker.writer.lexer;

@SuppressWarnings("serial")
public class LexerException extends Error {

	private LexerException(String message) {
		super(message);
	}

	public static LexerException create(LexerState state, char currentChar,
			int position) {
		StringBuilder sb = new StringBuilder();

		sb.append("In state [");
		sb.append(state);
		sb.append("] unexpected character '");
		sb.append(currentChar);
		sb.append("' in position ");
		sb.append(position);
		sb.append('.');

		return new LexerException(sb.toString());
	}

	public static LexerException createUnknownState() {
		return new LexerException("Unknown lexer state.");
	}

}
