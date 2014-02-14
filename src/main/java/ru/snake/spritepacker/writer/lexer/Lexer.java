package ru.snake.spritepacker.writer.lexer;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Lexer {

	private static final String SYMBOL_LBRACE = "{";
	private static final String SYMBOL_WHITESPACE = " \t\r\n";
	private static final String SYMBOL_OPERATION = "+-*/%";
	private static final String SYMBOL_VARIABLE = ".abcdefghijklmnopqrstuvwxyz";
	private static final String SYMBOL_NUMBAR = "0123456789";
	private static final String SYMBOL_RBRACE = "}";

	private static final LexerState START_STATE = LexerState.TEXT;

	private final String input;
	private final List<Token> tokens;
	private final Map<LexerState, TokeType> stateTypes;

	private LexerState state;

	private int statePosition;
	private int position;

	public Lexer(String input) {
		this.input = input;

		tokens = new LinkedList<Token>();
		stateTypes = new EnumMap<LexerState, TokeType>(LexerState.class);

		stateTypes.put(LexerState.TEXT, TokeType.TEXT);
		stateTypes.put(LexerState.START_EXPRESSION, TokeType.START_EXPRESSION);
		stateTypes.put(LexerState.OPERATION, TokeType.OPERATION);
		stateTypes.put(LexerState.VARIABLE, TokeType.VARIABLE);
		stateTypes.put(LexerState.NUMBER, TokeType.NUMBER);
		stateTypes.put(LexerState.END_EXPRESSION, TokeType.END_EXPRESSION);

		state = START_STATE;

		statePosition = 0;
		position = 0;
	}

	public List<Token> getTokens() {
		while (position < input.length()) {
			char currentChar = input.charAt(position);

			switch (state) {
			case TEXT:
				// All symbols except left brace is text.
				if (SYMBOL_LBRACE.indexOf(currentChar) == -1) {
					setNextState(LexerState.TEXT, false);
				} else if (SYMBOL_LBRACE.indexOf(currentChar) != -1) {
					setNextState(LexerState.START_EXPRESSION, false);
				} else {
					throw LexerException.create(state, currentChar, position);
				}

				break;

			case START_EXPRESSION:
				if (SYMBOL_LBRACE.indexOf(currentChar) != -1) {
					setNextState(LexerState.TEXT, true);
				} else if (SYMBOL_WHITESPACE.indexOf(currentChar) != -1) {
					setNextState(LexerState.WHITE_SPACE, false);
				} else if (SYMBOL_OPERATION.indexOf(currentChar) != -1) {
					setNextState(LexerState.OPERATION, false);
				} else if (SYMBOL_VARIABLE.indexOf(currentChar) != -1) {
					setNextState(LexerState.VARIABLE, false);
				} else if (SYMBOL_NUMBAR.indexOf(currentChar) != -1) {
					setNextState(LexerState.NUMBER, false);
				} else if (SYMBOL_RBRACE.indexOf(currentChar) != -1) {
					setNextState(LexerState.END_EXPRESSION, false);
				} else {
					throw LexerException.create(state, currentChar, position);
				}

				break;

			case WHITE_SPACE:
			case OPERATION:
			case VARIABLE:
			case NUMBER:
				if (SYMBOL_WHITESPACE.indexOf(currentChar) != -1) {
					setNextState(LexerState.WHITE_SPACE, false);
				} else if (SYMBOL_OPERATION.indexOf(currentChar) != -1) {
					setNextState(LexerState.OPERATION, false);
				} else if (SYMBOL_VARIABLE.indexOf(currentChar) != -1) {
					setNextState(LexerState.VARIABLE, false);
				} else if (SYMBOL_NUMBAR.indexOf(currentChar) != -1) {
					setNextState(LexerState.NUMBER, false);
				} else if (SYMBOL_RBRACE.indexOf(currentChar) != -1) {
					setNextState(LexerState.END_EXPRESSION, false);
				} else {
					throw LexerException.create(state, currentChar, position);
				}

				break;

			case END_EXPRESSION:
				// All symbols except left brace is text.
				if (SYMBOL_LBRACE.indexOf(currentChar) == -1) {
					setNextState(LexerState.TEXT, false);
				} else if (SYMBOL_LBRACE.indexOf(currentChar) != -1) {
					setNextState(LexerState.START_EXPRESSION, false);
				} else {
					throw LexerException.create(state, currentChar, position);
				}

				break;

			default:
				throw LexerException.createUnknownState();
			}

			position++;
		}

		if (position > 0) {
			setNextState(null, false);
		}

		// Add epsilon token.
		tokens.add(new Token(position));

		return tokens;
	}

	private void setNextState(LexerState next, boolean skipToken) {
		if (state == next) {
			return;
		}

		if (!skipToken && stateTypes.containsKey(state)) {
			String text = input.substring(statePosition, position);
			Token token = new Token(this.stateTypes.get(state), text,
					statePosition);
			tokens.add(token);
		}

		statePosition = position;
		state = next;
	}

}
