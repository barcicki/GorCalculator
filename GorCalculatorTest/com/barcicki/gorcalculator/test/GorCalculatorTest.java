package com.barcicki.gorcalculator.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.barcicki.gorcalculator.core.Calculator;
import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.OpponentModel.GameColor;
import com.barcicki.gorcalculator.database.OpponentModel.GameResult;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.database.TournamentModel.TournamentClass;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GorCalculatorTest {
	
	static double DELTA = 0.001;
	
	@Test
	public void formulaCon() {
		assertEquals(116, 		Calculator.formulaCon(100), 	DELTA);
		assertEquals(115.1,		Calculator.formulaCon(115), 	DELTA);
		assertEquals(110, 		Calculator.formulaCon(200), 	DELTA);
		assertEquals(108.8,	 	Calculator.formulaCon(224), 	DELTA);
		assertEquals(91.65,	 	Calculator.formulaCon(567), 	DELTA);
		assertEquals(77.95,		Calculator.formulaCon(841), 	DELTA);
		assertEquals(74.35,		Calculator.formulaCon(913), 	DELTA);
		assertEquals(45.24,		Calculator.formulaCon(1544), 	DELTA);
		assertEquals(35.24,	 	Calculator.formulaCon(1794), 	DELTA);
		assertEquals(22.65,		Calculator.formulaCon(2145), 	DELTA);
		assertEquals(12.06,		Calculator.formulaCon(2547), 	DELTA);
		assertEquals(10, 		Calculator.formulaCon(2700), 	DELTA);
		assertEquals(10, 		Calculator.formulaCon(2874), 	DELTA);
		assertEquals(50.08,		Calculator.formulaCon(1423), 	DELTA);
	}
	
	@Test
	public void formulaA() {
		assertEquals(200,		Calculator.formulaA(100, 100),			DELTA);
		assertEquals(200,		Calculator.formulaA(100, 500),			DELTA);
		assertEquals(200,		Calculator.formulaA(100, 1000),			DELTA);
		assertEquals(200,		Calculator.formulaA(500, 100),			DELTA);
		assertEquals(180,		Calculator.formulaA(500, 541),			DELTA);
		assertEquals(180,		Calculator.formulaA(500, 1362),			DELTA);
		assertEquals(193.3,		Calculator.formulaA(1471, 234),			DELTA);
		assertEquals(162.75,	Calculator.formulaA(1471, 845),			DELTA);
		assertEquals(131.45,	Calculator.formulaA(1471, 1857),		DELTA);
		assertEquals(172.65,	Calculator.formulaA(2574, 647),			DELTA);
		assertEquals(132.25,	Calculator.formulaA(2574, 1455),		DELTA);
		assertEquals(89,		Calculator.formulaA(2574, 2320),		DELTA);
		assertEquals(76.3,		Calculator.formulaA(2574, 2874),		DELTA);
		assertEquals(70,		Calculator.formulaA(2824, 2874),		DELTA);
		assertEquals(194.35,	Calculator.formulaA(1423, 213),			DELTA);
	}
	
	@Test
	public void formulaSeEven() {
		assertEquals(0.492,		Calculator.formulaSe(100, 100, 0),			DELTA);
		assertEquals(0.111,		Calculator.formulaSe(100, 500, 0),			DELTA);
		assertEquals(0.003,		Calculator.formulaSe(100, 1000, 0),			DELTA);
		assertEquals(0.873,		Calculator.formulaSe(500, 100, 0),			DELTA);
		assertEquals(0.435,		Calculator.formulaSe(500, 541, 0),			DELTA);
		assertEquals(0.0,		Calculator.formulaSe(500, 1362, 0),			DELTA);
		assertEquals(0.99,		Calculator.formulaSe(1471, 234, 0),			DELTA);
		assertEquals(0.971,		Calculator.formulaSe(1471, 845, 0),			DELTA);
		assertEquals(0.042,		Calculator.formulaSe(1471, 1857, 0),		DELTA);
		assertEquals(0.992,		Calculator.formulaSe(2574, 647, 0),			DELTA);
		assertEquals(0.992,		Calculator.formulaSe(2574, 1455, 0),		DELTA);
		assertEquals(0.938,		Calculator.formulaSe(2574, 2320, 0),		DELTA);
		assertEquals(0.011,		Calculator.formulaSe(2574, 2874, 0),		DELTA);
		assertEquals(0.321,		Calculator.formulaSe(2824, 2874, 0),		DELTA);
		assertEquals(0.990,		Calculator.formulaSe(1423, 213, 0),			DELTA);
	}
	
	@Test
	public void formulaSeHandicapBlack() {
		assertEquals(0.678,		Calculator.formulaSe(100, 100, 2),			DELTA);
		assertEquals(0.424,		Calculator.formulaSe(100, 500, 4),			DELTA);
		assertEquals(0.276,		Calculator.formulaSe(100, 1000, 8),			DELTA);
		assertEquals(0.919,		Calculator.formulaSe(500, 100, 1),			DELTA);
		assertEquals(0.505,		Calculator.formulaSe(500, 541, 1),			DELTA);
		assertEquals(0.184,		Calculator.formulaSe(500, 1362, 7),			DELTA);
		assertEquals(0.992,		Calculator.formulaSe(1471, 234, 2),			DELTA);
		assertEquals(0.992,		Calculator.formulaSe(1471, 845, 5),			DELTA);
		assertEquals(0.122,		Calculator.formulaSe(1471, 1857, 2),		DELTA);
		assertEquals(0.992,		Calculator.formulaSe(2574, 647, 3),			DELTA);
		assertEquals(0.992,		Calculator.formulaSe(2574, 1455, 8),		DELTA);
		assertEquals(0.976,		Calculator.formulaSe(2574, 2320, 1),		DELTA);
		assertEquals(0.321,		Calculator.formulaSe(2574, 2874, 3),		DELTA);
	}
	
	@Test
	public void formulaSeHandicapWhite() {
		assertEquals(0.306,		Calculator.formulaSe(100, 100, -2),			DELTA);
		assertEquals(0.002,		Calculator.formulaSe(100, 500, -4),			DELTA);
		assertEquals(0.0,		Calculator.formulaSe(100, 1000, -8),		DELTA);
		assertEquals(0.847,		Calculator.formulaSe(500, 100, -1),			DELTA);
		assertEquals(0.365,		Calculator.formulaSe(500, 541, -1),			DELTA);
		assertEquals(0.0,		Calculator.formulaSe(500, 1362, -7),		DELTA);
		assertEquals(0.989,		Calculator.formulaSe(1471, 234, -2),		DELTA);
		assertEquals(0.77,		Calculator.formulaSe(1471, 845, -5),		DELTA);
		assertEquals(0.0,		Calculator.formulaSe(1471, 1857, -2),		DELTA);
		assertEquals(0.992,		Calculator.formulaSe(2574, 647, -3),		DELTA);
		assertEquals(0.972,		Calculator.formulaSe(2574, 1455, -8),		DELTA);
		assertEquals(0.906,		Calculator.formulaSe(2574, 2320, -1),		DELTA);
		assertEquals(0.0,		Calculator.formulaSe(2574, 2874, -3),		DELTA);
	}
	
	@Test
	public void lowKyuWins() {
		PlayerModel player = new PlayerModel(408);
		
		assertEquals(18.38, 	Calculator.calculate(player, new PlayerModel(100), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(48.208, 	Calculator.calculate(player, new PlayerModel(541), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(51.193, 	Calculator.calculate(player, new PlayerModel(762), 4, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(43.578, 	Calculator.calculate(player, new PlayerModel(812), 5, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(51.271, 	Calculator.calculate(player, new PlayerModel(413), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(40.743, 	Calculator.calculate(player, new PlayerModel(895), 6, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(43.462, 	Calculator.calculate(player, new PlayerModel(1217), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(92.829, 	Calculator.calculate(player, new PlayerModel(1613), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(25.45, 	Calculator.calculate(player, new PlayerModel(1100), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(99.6, 		Calculator.calculate(player, new PlayerModel(2512), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(92.365, 	Calculator.calculate(player, new PlayerModel(989), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(61.986, 	Calculator.calculate(player, new PlayerModel(142), 4, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(56.711, 	Calculator.calculate(player, new PlayerModel(203), 3, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(69.643, 	Calculator.calculate(player, new PlayerModel(101), 5, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(63.009, 	Calculator.calculate(player, new PlayerModel(502), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
	}
	
	@Test
	public void lowKyuLosses() {
		PlayerModel player = new PlayerModel(408);
		
		assertEquals(-81.22, 	Calculator.calculate(player, new PlayerModel(100), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-51.392, 	Calculator.calculate(player, new PlayerModel(541), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-48.407, 	Calculator.calculate(player, new PlayerModel(762), 4, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-56.022, 	Calculator.calculate(player, new PlayerModel(812), 5, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-48.329, 	Calculator.calculate(player, new PlayerModel(413), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-58.857, 	Calculator.calculate(player, new PlayerModel(895), 6, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-56.138, 	Calculator.calculate(player, new PlayerModel(1217), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-6.771, 	Calculator.calculate(player, new PlayerModel(1613), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-74.15, 	Calculator.calculate(player, new PlayerModel(1100), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.0, 		Calculator.calculate(player, new PlayerModel(2512), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-7.235, 	Calculator.calculate(player, new PlayerModel(989), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-37.614, 	Calculator.calculate(player, new PlayerModel(142), 4, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-42.889, 	Calculator.calculate(player, new PlayerModel(203), 3, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-29.957, 	Calculator.calculate(player, new PlayerModel(101), 5, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-36.591, 	Calculator.calculate(player, new PlayerModel(502), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
	}
	
	@Test
	public void midKyuWins() {
		PlayerModel player = new PlayerModel(1423);
		
		assertEquals(0.499, 	Calculator.calculate(player, new PlayerModel(213), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(1.430, 	Calculator.calculate(player, new PlayerModel(782), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(16.871, 	Calculator.calculate(player, new PlayerModel(1324), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(49.501, 	Calculator.calculate(player, new PlayerModel(1947), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(50.080, 	Calculator.calculate(player, new PlayerModel(2104), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(50.080, 	Calculator.calculate(player, new PlayerModel(2341), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(0.401, 	Calculator.calculate(player, new PlayerModel(213), 3, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.496, 	Calculator.calculate(player, new PlayerModel(782), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(12.588, 	Calculator.calculate(player, new PlayerModel(1324), 1, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(41.311, 	Calculator.calculate(player, new PlayerModel(1947), 4, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(16.839, 	Calculator.calculate(player, new PlayerModel(2104), 8, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(34.353, 	Calculator.calculate(player, new PlayerModel(2341), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(4.679, 	Calculator.calculate(player, new PlayerModel(213), 9, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(26.285, 	Calculator.calculate(player, new PlayerModel(782), 7, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(30.243, 	Calculator.calculate(player, new PlayerModel(1324), 2, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(50.080, 	Calculator.calculate(player, new PlayerModel(1947), 1, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(50.080, 	Calculator.calculate(player, new PlayerModel(2104), 3, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(50.080, 	Calculator.calculate(player, new PlayerModel(2341), 1, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	
	}
	
	@Test
	public void midKyuLosses() {
		PlayerModel player = new PlayerModel(1423);
		
		assertEquals(-49.581, 	Calculator.calculate(player, new PlayerModel(213), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-48.650, 	Calculator.calculate(player, new PlayerModel(782), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-33.209, 	Calculator.calculate(player, new PlayerModel(1324), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-0.579, 	Calculator.calculate(player, new PlayerModel(1947), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.0,	 	Calculator.calculate(player, new PlayerModel(2104), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.0, 		Calculator.calculate(player, new PlayerModel(2341), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(-49.679, 	Calculator.calculate(player, new PlayerModel(213), 3, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-49.584, 	Calculator.calculate(player, new PlayerModel(782), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-37.492, 	Calculator.calculate(player, new PlayerModel(1324), 1, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-8.769, 	Calculator.calculate(player, new PlayerModel(1947), 4, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-33.241, 	Calculator.calculate(player, new PlayerModel(2104), 8, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-15.727, 	Calculator.calculate(player, new PlayerModel(2341), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(-45.401, 	Calculator.calculate(player, new PlayerModel(213), 9, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-23.795, 	Calculator.calculate(player, new PlayerModel(782), 7, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-19.837, 	Calculator.calculate(player, new PlayerModel(1324), 2, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.0,	 	Calculator.calculate(player, new PlayerModel(1947), 1, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.0, 		Calculator.calculate(player, new PlayerModel(2104), 3, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.0, 		Calculator.calculate(player, new PlayerModel(2341), 1, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	
	}
	
	
	@Test
	public void strongDanWins() {
		PlayerModel player = new PlayerModel(2341);
		
		assertEquals(0.135, 	Calculator.calculate(player, new PlayerModel(308), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.136, 	Calculator.calculate(player, new PlayerModel(847), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.159, 	Calculator.calculate(player, new PlayerModel(1488), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.354, 	Calculator.calculate(player, new PlayerModel(1856), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(1.025, 	Calculator.calculate(player, new PlayerModel(2045), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(3.019, 	Calculator.calculate(player, new PlayerModel(2191), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(7.216, 	Calculator.calculate(player, new PlayerModel(2313), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(10.663, 	Calculator.calculate(player, new PlayerModel(2387), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(14.564, 	Calculator.calculate(player, new PlayerModel(2501), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(0.134, 	Calculator.calculate(player, new PlayerModel(308), 1, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.134, 	Calculator.calculate(player, new PlayerModel(847), 6, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.134, 	Calculator.calculate(player, new PlayerModel(1488), 3, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.140, 	Calculator.calculate(player, new PlayerModel(1856), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.135, 	Calculator.calculate(player, new PlayerModel(2045), 5, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.217, 	Calculator.calculate(player, new PlayerModel(2191), 3, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(4.937, 	Calculator.calculate(player, new PlayerModel(2313), 1, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.355, 	Calculator.calculate(player, new PlayerModel(2387), 4, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(4.037, 	Calculator.calculate(player, new PlayerModel(2501), 3, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(0.140, 	Calculator.calculate(player, new PlayerModel(308), 9, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.213, 	Calculator.calculate(player, new PlayerModel(847), 9, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(8.376, 	Calculator.calculate(player, new PlayerModel(1488), 9, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(16.435, 	Calculator.calculate(player, new PlayerModel(1856), 8, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(6.427, 	Calculator.calculate(player, new PlayerModel(2045), 3, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(8.519, 	Calculator.calculate(player, new PlayerModel(2191), 2, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(9.576, 	Calculator.calculate(player, new PlayerModel(2313), 1, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(15.641, 	Calculator.calculate(player, new PlayerModel(2387), 2, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(16.770, 	Calculator.calculate(player, new PlayerModel(2501), 4, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	
	}
	
	@Test
	public void strongDanLosses() {
		PlayerModel player = new PlayerModel(2341);
		
		assertEquals(-16.635, 	Calculator.calculate(player, new PlayerModel(308), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.634, 	Calculator.calculate(player, new PlayerModel(847), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.611, 	Calculator.calculate(player, new PlayerModel(1488), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.416, 	Calculator.calculate(player, new PlayerModel(1856), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-15.745, 	Calculator.calculate(player, new PlayerModel(2045), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-13.751, 	Calculator.calculate(player, new PlayerModel(2191), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-9.554, 	Calculator.calculate(player, new PlayerModel(2313), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-6.107, 	Calculator.calculate(player, new PlayerModel(2387), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-2.206, 	Calculator.calculate(player, new PlayerModel(2501), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(-16.636, 	Calculator.calculate(player, new PlayerModel(308), 1, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.636, 	Calculator.calculate(player, new PlayerModel(847), 6, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.636, 	Calculator.calculate(player, new PlayerModel(1488), 3, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.630, 	Calculator.calculate(player, new PlayerModel(1856), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.635, 	Calculator.calculate(player, new PlayerModel(2045), 5, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.553, 	Calculator.calculate(player, new PlayerModel(2191), 3, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-11.833, 	Calculator.calculate(player, new PlayerModel(2313), 1, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.415, 	Calculator.calculate(player, new PlayerModel(2387), 4, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-12.733, 	Calculator.calculate(player, new PlayerModel(2501), 3, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(-16.630, 	Calculator.calculate(player, new PlayerModel(308), 9, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-16.557, 	Calculator.calculate(player, new PlayerModel(847), 9, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-8.394, 	Calculator.calculate(player, new PlayerModel(1488), 9, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-0.335, 	Calculator.calculate(player, new PlayerModel(1856), 8, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-10.343, 	Calculator.calculate(player, new PlayerModel(2045), 3, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-8.251, 	Calculator.calculate(player, new PlayerModel(2191), 2, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-7.194, 	Calculator.calculate(player, new PlayerModel(2313), 1, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-1.129, 	Calculator.calculate(player, new PlayerModel(2387), 2, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-0.0, 		Calculator.calculate(player, new PlayerModel(2501), 4, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	
	}
	
	
	@Test
	public void wysockiLewTestCase() {
		GameResult result = GameResult.WIN;
		TournamentClass category = TournamentClass.CLASS_A;
		double 	myGor = 1914, 
				OpponentModelGor = 2103,
				handicap = OpponentModel.NO_HANDICAP,
				expectedResult = 26.096f;
		
		double actualResult = Calculator.calculateRatingChange(myGor, OpponentModelGor, result.value, handicap, category.value);    
	 
	 	assertEquals (expectedResult, actualResult, 0.1);
	 }
	
}
