package ru.snake.spritepacker.writer.expression.value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ru.snake.spritepacker.writer.expression.AddExpression;
import ru.snake.spritepacker.writer.expression.ConstantExpression;
import ru.snake.spritepacker.writer.expression.DivExpression;
import ru.snake.spritepacker.writer.expression.ExpressionVisitor;
import ru.snake.spritepacker.writer.expression.MulExpression;
import ru.snake.spritepacker.writer.expression.SubExpression;
import ru.snake.spritepacker.writer.expression.TextExpression;
import ru.snake.spritepacker.writer.expression.VariableExpression;

public class VariableMap implements ExpressionVisitor {

	private final Map<String, Value> variables;

	public VariableMap() {
		variables = new HashMap<String, Value>();
	}

	public Set<String> getNames() {
		return Collections.unmodifiableSet(variables.keySet());
	}

	public void setValue(String name, String value) {
		if (variables.containsKey(name)) {
			Value variableValue = variables.get(name);

			variableValue.setString(value);
		}
	}

	public void visitText(TextExpression expression) {
	}

	public void visitConstant(ConstantExpression expression) {
	}

	public void visitVariable(VariableExpression expression) {
		String name = expression.getName();

		if (!variables.containsKey(name)) {
			variables.put(name, new Value());
		}

		expression.setValue(variables.get(name));
	}

	public void visitAdd(AddExpression expression) {
	}

	public void visitSub(SubExpression expression) {
	}

	public void visitMul(MulExpression expression) {
	}

	public void visitDiv(DivExpression expression) {
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<String, Value>> eachIt;
		Entry<String, Value> next;
		String nextName;
		Value nextValue;

		eachIt = variables.entrySet().iterator();

		if (eachIt.hasNext()) {
			next = eachIt.next();
			nextName = next.getKey();
			nextValue = next.getValue();

			builder.append(nextName);
			builder.append('=');
			builder.append(nextValue);

			while (eachIt.hasNext()) {
				next = eachIt.next();
				nextName = next.getKey();
				nextValue = next.getValue();

				builder.append(", ");
				builder.append(nextName);
				builder.append('=');
				builder.append(nextValue);
			}
		}

		return builder.toString();
	}

	public void clear() {
		variables.clear();
	}

}
