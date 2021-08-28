package matej.tejkogames.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import matej.tejkogames.constants.YambConstants;
import matej.tejkogames.models.yamb.Box;
import matej.tejkogames.models.yamb.BoxType;
import matej.tejkogames.models.yamb.Column;
import matej.tejkogames.models.yamb.ColumnType;
import matej.tejkogames.models.yamb.Dice;
import matej.tejkogames.models.yamb.YambForm;
import matej.tejkogames.models.yamb.YambType;

public class YambUtil {

	// public static Yamb generateYamb(User user, YambType type, int numberOfColumns, int numberOfDice) {
	// 	Yamb yamb = new Yamb();
	// 	yamb.setUser(user);
	// 	yamb.setType(type);
	// 	yamb.setNumberOfColumns(numberOfColumns);
	// 	yamb.setNumberOfDice(numberOfDice);
	// 	yamb.setYambForm(generateYambForm);
	// 	yamb.setUser(user);

	// 	return new Yamb(user, type, numberOfColumns, numberOfDice, generateYambForm(type, numberOfColumns, numberOfDice),
	// 			generateDiceSet(numberOfDice));
	// }

	public static YambForm generateYambForm(YambType type, int numberOfColumns, int numberOfDice) {

		List<Column> columnList = new ArrayList<>();
		for (int i = 1; i <= numberOfColumns; i++) {
			ColumnType columnType = type == YambType.CLASSIC || type == YambType.CHALLENGE ? ColumnType.values()[i - 1]
					: ColumnType.FREE;
			List<Box> boxList = generateBoxList(columnType);
			Column column = new Column(columnType, boxList);
			columnList.add(column);
		}

		YambForm form = new YambForm(columnList);
		return form;
	}

	public static Set<Dice> generateDiceSet(int numberOfDice) {
		Set<Dice> diceSet = new HashSet<>();
		for (int i = 1; i <= numberOfDice; i++) {
			diceSet.add(new Dice(i));
		}
		return diceSet;
	}

	public static List<Box> generateBoxList(ColumnType columnType) {
		List<Box> boxList = new ArrayList<>();
		for (BoxType boxType : BoxType.values()) {
			boolean available = (columnType == ColumnType.DOWNWARDS && boxType == BoxType.ONES
					|| columnType == ColumnType.UPWARDS && boxType == BoxType.YAMB || columnType == ColumnType.FREE
					|| columnType == ColumnType.ANNOUNCEMENT);
			Box box = new Box(boxType, available);
			boxList.add(box);
		}
		return boxList;
	}

	/**
	 * Calculates score based on rolled dice values and type of box to be filled.
	 * 
	 * @param diceSet the values of rolled dice
	 * @param boxType the type of box to be filled
	 * @return {@code int} the calculated score result
	 */
	public static int calculateScore(Set<Dice> diceSet, BoxType boxType) {
		int result = 0; // initialize variable for storing and returning score result to zero
		Set<Integer> diceValues = new HashSet<>(diceSet.size());
		diceSet.forEach((dice) -> diceValues.add(dice.getValue()));
		switch (boxType) {
			// determine method to be used for score calculation based on type of box to be
			// filled
			case ONES:
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
				result = calculateSumByType(diceValues, boxType);
				break;
			case MAX:
			case MIN:
				result = calculateSum(diceValues);
				break;
			case TRIPS:
				result = calculateSumOfRepeatingValue(diceValues, 3, YambConstants.BONUS_TRIPS);
				break;
			case STRAIGHT:
				result = calculateStraight(diceValues);
				break;
			case BOAT:
				result = calculateFull(diceValues, YambConstants.BONUS_BOAT);
				break;
			case CARRIAGE:
				result = calculateSumOfRepeatingValue(diceValues, 4, YambConstants.BONUS_CARRIAGE);
				break;
			case YAMB:
				result = calculateSumOfRepeatingValue(diceValues, 5, YambConstants.BONUS_YAMB);
				break;
		}
		return result;
	}

	/**
	 * Calculates sum of all dice values from received list.
	 * 
	 * @param diceSet the values of rolled dice
	 * @return {@code int} the sum of all dice values
	 */
	private static int calculateSum(Set<Integer> diceValues) {
		int sum = 0;
		for (int value : diceValues) {
			sum += value;
		}
		return sum;
	}

	/**
	 * Calculates sum of all dice values equal to box type.
	 * 
	 * @param diceSet the values of rolled dice
	 * @return {@code int} the sum of all dice values equal to box type
	 */
	private static int calculateSumByType(Set<Integer> diceValues, BoxType boxType) {
		int sum = 0;
		for (int value : diceValues) {
			int boxTypeId = boxType.ordinal() + 1; // boxType id matches first six boxes by representing number ("ONES"
													// -> Id: 1, "TWOS" -> Id: 2, ... , "SIXES" -> Id: 6)
			if (value == boxTypeId) {
				sum += value; // if dice value is equal to box type add to sum
			}
		}
		return sum;
	}

	/**
	 * Calculates sum of dice values that occur at least a certain number of times.
	 * 
	 * @param diceSet       the values of rolled dice
	 * @param repeatCounter the minimal number of times value has to occur to be
	 *                      summed in the total
	 * @param bonus         the predefined bonus added to the sum if a value occured
	 *                      at least a certain number of times
	 * 
	 * @return {@code int} the sum of dice values that they occured at least a
	 *         certain number of times; 0 if all values occured less than given
	 *         number of times
	 */
	private static int calculateSumOfRepeatingValue(Set<Integer> diceValues, int repeatNumber, int bonus) {
		for (int i = 1; i <= 6; i++) {
			int count = Collections.frequency(diceValues, i);
			if (count >= repeatNumber) { // if count has reached given number return sum increased by given bonus
				return i * repeatNumber + bonus; // multiply by repeat number instead of count
			}
		}
		return 0; // if count has not reached given number for any dice value return 0
	}

	/**
	 * Checks if a straight occures in dice list. Straight is defined as 5
	 * consecutive numbers, and can be either a small (1, 2, 3, 4 , 5), or big (2,
	 * 3, 4, 5, 6) straight.
	 * 
	 * @param diceSet the values of rolled dice
	 * 
	 * @return {@code int} the predefined value of a small or big straight if it
	 *         occured and 0 otherwise.
	 */
	private static int calculateStraight(Set<Integer> diceValues) {
		Set<Integer> straight = new HashSet<>();
		straight.add(2);
		straight.add(3);
		straight.add(4);
		straight.add(5);
		if (diceValues.containsAll(straight)) {
			if (diceValues.contains(1)) {
				return YambConstants.BONUS_STRAIGHT_SMALL;
			} else if (diceValues.contains(6))
				return YambConstants.BONUS_STRAIGHT_BIG;
		}
		return 0;
	}

	/**
	 * Checks if a full occures in dice list.
	 * 
	 * @param diceSet the values of rolled dice
	 * @param bonus   the predefined bonus added to the sum if a full occured
	 * 
	 * @return {@code int} the sum of dice if both a pair and trips occured
	 */
	public static int calculateFull(Set<Integer> diceValues, int bonus) {
		int valueOfPair = 0;
		int valueOfTrips = 0;
		for (int i = 1; i <= 6; i++) {
			int count = Collections.frequency(diceValues, i);
			if (count == 2) {
				valueOfPair = i * count;
			} else if (count == 3) {
				valueOfTrips = i * count;
			}
		}
		if (valueOfPair > 0 && valueOfTrips > 0) {
			return valueOfPair + valueOfTrips + bonus;
		}
		return 0;
	}

}
