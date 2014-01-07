package ru.snake.spritepacker.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import ru.snake.spritepacker.core.CoreFactoryWalker;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.writer.expression.Expression;
import ru.snake.spritepacker.writer.expression.value.Value;
import ru.snake.spritepacker.writer.expression.value.VariableMap;
import ru.snake.spritepacker.writer.lexer.Lexer;
import ru.snake.spritepacker.writer.lexer.Token;
import ru.snake.spritepacker.writer.parser.Parser;

public abstract class TextWriter implements CoreFactoryWalker {

	private Writer fileWriter;
	private Writer bufferedWriter;

	private boolean initialized;

	private VariableMap variableMap;
	private Expression expression;

	public TextWriter() {
		fileWriter = null;
		bufferedWriter = null;

		initialized = false;

		variableMap = new VariableMap();
		expression = null;
	}

	public void prepareExpression(String textExpression) {
		Lexer lexer = new Lexer(textExpression);
		List<Token> tokens = lexer.getTokens();
		Parser parser = new Parser(tokens);
		expression = parser.parse();

		variableMap.clear();
		expression.visit(variableMap);
	}

	protected void setValue(String variableName, int value) {
		variableMap.setValue(variableName, String.valueOf(value));
	}

	protected void setValue(String variableName, String value) {
		variableMap.setValue(variableName, value);
	}

	protected void writeLine() {
		Value value = expression.getValue();

		try {
			bufferedWriter.write(value.getString());
			bufferedWriter.write('\n');
		} catch (IOException e) {
			Dialogs.error(null, e.getMessage());
		}
	}

	protected void startWrite(File outputFile) {
		if (initialized) {
			return;
		}

		fileWriter = null;
		bufferedWriter = null;

		try {
			fileWriter = new FileWriter(outputFile);
		} catch (IOException ex) {
			Dialogs.error(null, ex.getMessage());

			return;
		}

		bufferedWriter = new BufferedWriter(fileWriter);

		initialized = true;
	}

	protected void endWrite() {
		if (!initialized) {
			return;
		}

		try {
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException ex) {
			Dialogs.error(null, ex.getMessage());
		}

		initialized = false;
	}

}
