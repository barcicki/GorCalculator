package com.barcicki.gorcalculator.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.barcicki.gorcalculator.core.Calculator;
import com.barcicki.gorcalculator.core.Opponent;
import com.barcicki.gorcalculator.core.Opponent.GameColor;
import com.barcicki.gorcalculator.core.Opponent.GameResult;
import com.barcicki.gorcalculator.core.Player;
import com.barcicki.gorcalculator.core.Tournament.TournamentClass;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GorCalculatorTest {
	
	@Test
	public void formulaCon() {
		double delta = 0.001;
		assertEquals(116, 		Calculator.formulaCon(100), 	delta);
		assertEquals(115.1, 	Calculator.formulaCon(115), 	delta);
		assertEquals(110, 		Calculator.formulaCon(200), 	delta);
		assertEquals(108.8,	 	Calculator.formulaCon(224), 	delta);
		assertEquals(91.65,	 	Calculator.formulaCon(567), 	delta);
		assertEquals(77.95,		Calculator.formulaCon(841), 	delta);
		assertEquals(74.35,		Calculator.formulaCon(913), 	delta);
		assertEquals(45.24,		Calculator.formulaCon(1544), 	delta);
		assertEquals(35.24,	 	Calculator.formulaCon(1794), 	delta);
		assertEquals(22.65,		Calculator.formulaCon(2145), 	delta);
		assertEquals(12.06,		Calculator.formulaCon(2547), 	delta);
		assertEquals(10, 		Calculator.formulaCon(2700), 	delta);
		assertEquals(10, 		Calculator.formulaCon(2874), 	delta);
	}
	
	@Test
	public void formulaA() {
		double delta = 0.001;
		assertEquals(200,		Calculator.formulaA(100, 100),			delta);
		assertEquals(200,		Calculator.formulaA(100, 500),			delta);
		assertEquals(200,		Calculator.formulaA(100, 1000),			delta);
		assertEquals(200,		Calculator.formulaA(500, 100),			delta);
		assertEquals(180,		Calculator.formulaA(500, 541),			delta);
		assertEquals(180,		Calculator.formulaA(500, 1362),			delta);
		assertEquals(193.3,		Calculator.formulaA(1471, 234),			delta);
		assertEquals(162.75,	Calculator.formulaA(1471, 845),			delta);
		assertEquals(131.45,	Calculator.formulaA(1471, 1857),		delta);
		assertEquals(172.65,	Calculator.formulaA(2574, 647),			delta);
		assertEquals(132.25,	Calculator.formulaA(2574, 1455),		delta);
		assertEquals(89,		Calculator.formulaA(2574, 2320),		delta);
		assertEquals(76.3,		Calculator.formulaA(2574, 2874),		delta);
		assertEquals(70,		Calculator.formulaA(2824, 2874),		delta);
	}
	
	@Test
	public void formulaSeEven() {
		double delta = 0.001;
		assertEquals(0.492,		Calculator.formulaSe(100, 100, 0),			delta);
		assertEquals(0.111,		Calculator.formulaSe(100, 500, 0),			delta);
		assertEquals(0.003,		Calculator.formulaSe(100, 1000, 0),			delta);
		assertEquals(0.873,		Calculator.formulaSe(500, 100, 0),			delta);
		assertEquals(0.435,		Calculator.formulaSe(500, 541, 0),			delta);
		assertEquals(0.0,		Calculator.formulaSe(500, 1362, 0),			delta);
		assertEquals(0.99,		Calculator.formulaSe(1471, 234, 0),			delta);
		assertEquals(0.971,		Calculator.formulaSe(1471, 845, 0),			delta);
		assertEquals(0.042,		Calculator.formulaSe(1471, 1857, 0),		delta);
		assertEquals(0.992,		Calculator.formulaSe(2574, 647, 0),			delta);
		assertEquals(0.992,		Calculator.formulaSe(2574, 1455, 0),		delta);
		assertEquals(0.938,		Calculator.formulaSe(2574, 2320, 0),		delta);
		assertEquals(0.011,		Calculator.formulaSe(2574, 2874, 0),		delta);
		assertEquals(0.321,		Calculator.formulaSe(2824, 2874, 0),		delta);
	}
	
	@Test
	public void formulaSeHandicapBlack() {
		double delta = 0.001;
		assertEquals(0.678,		Calculator.formulaSe(100, 100, 2),			delta);
		assertEquals(0.424,		Calculator.formulaSe(100, 500, 4),			delta);
		assertEquals(0.276,		Calculator.formulaSe(100, 1000, 8),			delta);
		assertEquals(0.919,		Calculator.formulaSe(500, 100, 1),			delta);
		assertEquals(0.505,		Calculator.formulaSe(500, 541, 1),			delta);
		assertEquals(0.184,		Calculator.formulaSe(500, 1362, 7),			delta);
		assertEquals(0.992,		Calculator.formulaSe(1471, 234, 2),			delta);
		assertEquals(0.992,		Calculator.formulaSe(1471, 845, 5),			delta);
		assertEquals(0.122,		Calculator.formulaSe(1471, 1857, 2),		delta);
		assertEquals(0.992,		Calculator.formulaSe(2574, 647, 3),			delta);
		assertEquals(0.992,		Calculator.formulaSe(2574, 1455, 8),		delta);
		assertEquals(0.976,		Calculator.formulaSe(2574, 2320, 1),		delta);
		assertEquals(0.321,		Calculator.formulaSe(2574, 2874, 3),		delta);
	}
	
	@Test
	public void formulaSeHandicapWhite() {
		double delta = 0.001;
		assertEquals(0.306,		Calculator.formulaSe(100, 100, -2),			delta);
		assertEquals(0.002,		Calculator.formulaSe(100, 500, -4),			delta);
		assertEquals(0.0,		Calculator.formulaSe(100, 1000, -8),		delta);
		assertEquals(0.847,		Calculator.formulaSe(500, 100, -1),			delta);
		assertEquals(0.365,		Calculator.formulaSe(500, 541, -1),			delta);
		assertEquals(0.0,		Calculator.formulaSe(500, 1362, -7),		delta);
		assertEquals(0.989,		Calculator.formulaSe(1471, 234, -2),		delta);
		assertEquals(0.77,		Calculator.formulaSe(1471, 845, -5),		delta);
		assertEquals(0.0,		Calculator.formulaSe(1471, 1857, -2),		delta);
		assertEquals(0.992,		Calculator.formulaSe(2574, 647, -3),		delta);
		assertEquals(0.972,		Calculator.formulaSe(2574, 1455, -8),		delta);
		assertEquals(0.906,		Calculator.formulaSe(2574, 2320, -1),		delta);
		assertEquals(0.0,		Calculator.formulaSe(2574, 2874, -3),		delta);
	}
	
	@Test
	public void lowKyuWins() {
		double delta = 0.001;
		Player player = new Player(408);
		
		assertEquals(18.38, 	Calculator.calculate(player, new Player(100), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(48.208, 	Calculator.calculate(player, new Player(541), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(51.193, 	Calculator.calculate(player, new Player(762), 4, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(43.578, 	Calculator.calculate(player, new Player(812), 5, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(51.271, 	Calculator.calculate(player, new Player(413), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(40.743, 	Calculator.calculate(player, new Player(895), 6, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(43.462, 	Calculator.calculate(player, new Player(1217), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(92.829, 	Calculator.calculate(player, new Player(1613), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(25.45, 	Calculator.calculate(player, new Player(1100), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(99.6, 		Calculator.calculate(player, new Player(2512), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(92.365, 	Calculator.calculate(player, new Player(989), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(61.986, 	Calculator.calculate(player, new Player(142), 4, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), delta);
		assertEquals(56.711, 	Calculator.calculate(player, new Player(203), 3, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), delta);
		assertEquals(69.643, 	Calculator.calculate(player, new Player(101), 5, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), delta);
		assertEquals(63.009, 	Calculator.calculate(player, new Player(502), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), delta);
	}
	
	@Test
	public void lowKyuLosses() {
		double delta = 0.001;
		Player player = new Player(408);
		
		assertEquals(-81.22, 	Calculator.calculate(player, new Player(100), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-51.392, 	Calculator.calculate(player, new Player(541), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-48.407, 	Calculator.calculate(player, new Player(762), 4, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-56.022, 	Calculator.calculate(player, new Player(812), 5, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-48.329, 	Calculator.calculate(player, new Player(413), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-58.857, 	Calculator.calculate(player, new Player(895), 6, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-56.138, 	Calculator.calculate(player, new Player(1217), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-6.771, 	Calculator.calculate(player, new Player(1613), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-74.15, 	Calculator.calculate(player, new Player(1100), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(0.0, 		Calculator.calculate(player, new Player(2512), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-7.235, 	Calculator.calculate(player, new Player(989), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
		assertEquals(-37.614, 	Calculator.calculate(player, new Player(142), 4, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), delta);
		assertEquals(-42.889, 	Calculator.calculate(player, new Player(203), 3, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), delta);
		assertEquals(-29.957, 	Calculator.calculate(player, new Player(101), 5, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), delta);
		assertEquals(-36.591, 	Calculator.calculate(player, new Player(502), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), delta);
	}
	
	@Test
	public void wysockiLewTestCase() {
		GameResult result = GameResult.WIN;
		TournamentClass category = TournamentClass.CLASS_A;
		double 	myGor = 1914, 
				opponentGor = 2103,
				handicap = Opponent.NO_HANDICAP,
				expectedResult = 26.096f;
		
		double actualResult = Calculator.calculateRatingChange(myGor, opponentGor, result.value, handicap, category.value);    
	 
	 	assertEquals (expectedResult, actualResult, 0.1);
	 }
	
}
