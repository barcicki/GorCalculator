package com.barcicki.gorcalculator.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.barcicki.gorcalculator.core.Calculator;
import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.core.Tournament;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GorCalculatorTest {
	@Test
	public void wysockiLewTestCase() {
		float myGor = 1914, opponentGor = 2103,
		      gameResult = Opponent.WIN,
		      handicap = Opponent.NO_HANDICAP,
		      category = Tournament.CATEGORIES.get(Tournament.CATEGORY_A),
		      expectedResult = 26.096f;

		float actualResult = Calculator.calculateRatingChange(myGor, opponentGor, gameResult, handicap, category);		

		assertEquals (expectedResult, actualResult, 0.1);
	}
}
