package se.wiklund.haderengine.util;

public class MathUtils {

	/**
	 * Lock a variable's value between one minimum and one maximum value.
	 * 
	 * @param variable
	 *            the value
	 * @param min
	 *            the minimum value
	 * @param max
	 *            the maximum value
	 * @return the new value
	 */
	public static double clamp(double variable, double min, double max) {
		if (variable < min)
			return min;
		if (variable > max)
			return max;
		return variable;
	}

	/**
	 * Encounter <code>variableTo</code> from <code>variableBase</code> by
	 * <code>amount</code>.
	 * 
	 * @param variableBase
	 *            the base value
	 * @param variableTo
	 *            the target value
	 * @param amount
	 *            the amount
	 * @return the new variable
	 */
	public static double encounter(double variableBase, double variableTo, double amount) {
		double result = 0;
		if (variableBase > variableTo) {
			result = variableBase - amount;
			if (result < variableTo)
				return variableTo;
		} else if (variableBase < variableTo) {
			result = variableBase + amount;
			if (result > variableTo)
				return variableTo;
		} else {
			return variableTo;
		}
		return result;
	}

	/**
	 * Encounter zero from <code>variable</code> by <code>amount</code>.
	 * 
	 * @param variable
	 *            the value to go from
	 * @param amount
	 *            the amount to encounter zero by
	 * @return the new variable
	 */
	public static double encounterZero(double variable, double amount) {
		if (variable > 0) {
			variable -= amount;
			if (variable < 0)
				return 0;
		} else if (variable < 0) {
			variable += amount;
			if (variable > 0)
				return 0;
		} else {
			return 0;
		}
		return variable;
	}
}
