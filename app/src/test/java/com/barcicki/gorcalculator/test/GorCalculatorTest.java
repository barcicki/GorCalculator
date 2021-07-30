package com.barcicki.gorcalculator.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.barcicki.gorcalculator.core.Calculator;
import com.barcicki.gorcalculator.database.OpponentModel;
import com.barcicki.gorcalculator.database.OpponentModel.GameColor;
import com.barcicki.gorcalculator.database.OpponentModel.GameResult;
import com.barcicki.gorcalculator.database.PlayerModel;
import com.barcicki.gorcalculator.database.TournamentModel.TournamentClass;

@RunWith(RobolectricTestRunner.class)
@Config(sdk=28)
public class GorCalculatorTest {
	
	static double DELTA = 0.001;

	@Test
	public void formulaCon() {
		assertEquals(84.448, 	Calculator.formulaCon(100), DELTA);
		assertEquals(80.265, 	Calculator.formulaCon(200), DELTA);
		assertEquals(76.163, 	Calculator.formulaCon(300), DELTA);
		assertEquals(72.142, 	Calculator.formulaCon(400), DELTA);
		assertEquals(68.203, 	Calculator.formulaCon(500), DELTA);
		assertEquals(64.347, 	Calculator.formulaCon(600), DELTA);
		assertEquals(60.577, 	Calculator.formulaCon(700), DELTA);
		assertEquals(56.892, 	Calculator.formulaCon(800), DELTA);
		assertEquals(53.295, 	Calculator.formulaCon(900), DELTA);
		assertEquals(49.787, 	Calculator.formulaCon(1000), DELTA);
		assertEquals(46.369, 	Calculator.formulaCon(1100), DELTA);
		assertEquals(43.043, 	Calculator.formulaCon(1200), DELTA);
		assertEquals(39.810, 	Calculator.formulaCon(1300), DELTA);
		assertEquals(36.673, 	Calculator.formulaCon(1400), DELTA);
		assertEquals(33.634, 	Calculator.formulaCon(1500), DELTA);
		assertEquals(30.695, 	Calculator.formulaCon(1600), DELTA);
		assertEquals(27.857, 	Calculator.formulaCon(1700), DELTA);
		assertEquals(25.124, 	Calculator.formulaCon(1800), DELTA);
		assertEquals(22.498, 	Calculator.formulaCon(1900), DELTA);
		assertEquals(19.983, 	Calculator.formulaCon(2000), DELTA);
		assertEquals(17.580, 	Calculator.formulaCon(2100), DELTA);
		assertEquals(15.296, 	Calculator.formulaCon(2200), DELTA);
		assertEquals(13.132, 	Calculator.formulaCon(2300), DELTA);
		assertEquals(11.095, 	Calculator.formulaCon(2400), DELTA);
		assertEquals(9.189, 	Calculator.formulaCon(2500), DELTA);
		assertEquals(7.421, 	Calculator.formulaCon(2600), DELTA);
		assertEquals(5.799, 	Calculator.formulaCon(2700), DELTA);
		assertEquals(4.332, 	Calculator.formulaCon(2800), DELTA);
		assertEquals(3.031, 	Calculator.formulaCon(2900), DELTA);
		assertEquals(1.913, 	Calculator.formulaCon(3000), DELTA);
		assertEquals(1, 		Calculator.formulaCon(3100), DELTA);
		assertEquals(0.329, 	Calculator.formulaCon(3200), DELTA);
		assertEquals(0, 		Calculator.formulaCon(3300), DELTA);
	}
	
	@Test
	public void formulaSeEven() {
		assertEquals(0.5,		Calculator.formulaSe(100, 100, 0),	DELTA);
		assertEquals(0.281,		Calculator.formulaSe(100, 500, 0),	DELTA);
		assertEquals(0.090,		Calculator.formulaSe(100, 1000, 0),	DELTA);
		assertEquals(0.718,		Calculator.formulaSe(500, 100, 0),	DELTA);
		assertEquals(0.474,		Calculator.formulaSe(500, 541, 0),	DELTA);
		assertEquals(0.070,		Calculator.formulaSe(500, 1362, 0),	DELTA);
		assertEquals(0.973,		Calculator.formulaSe(1471, 234, 0),	DELTA);
		assertEquals(0.887,		Calculator.formulaSe(1471, 845, 0),	DELTA);
		assertEquals(0.159,		Calculator.formulaSe(1471, 1857, 0),	DELTA);
		assertEquals(0.999,		Calculator.formulaSe(2574, 647, 0),	DELTA);
		assertEquals(0.998,		Calculator.formulaSe(2574, 1455, 0),	DELTA);
		assertEquals(0.890,		Calculator.formulaSe(2574, 2320, 0),	DELTA);
		assertEquals(0.023,		Calculator.formulaSe(2574, 2874, 0),	DELTA);
		assertEquals(0.315,		Calculator.formulaSe(2824, 2874, 0),	DELTA);
		assertEquals(0.970,		Calculator.formulaSe(1423, 213, 0),	DELTA);
	}
	
	@Test
	public void formulaSeHandicapBlack() {
		assertEquals(0.583,		Calculator.formulaSe(100, 100, 2),	DELTA);
		assertEquals(0.469,		Calculator.formulaSe(100, 500, 4),	DELTA);
		assertEquals(0.391,		Calculator.formulaSe(100, 1000, 8),	DELTA);
		assertEquals(0.742,		Calculator.formulaSe(500, 100, 1),	DELTA);
		assertEquals(0.505,		Calculator.formulaSe(500, 541, 1),	DELTA);
		assertEquals(0.325,		Calculator.formulaSe(500, 1362, 7),	DELTA);
		assertEquals(0.985,		Calculator.formulaSe(1471, 234, 2),	DELTA);
		assertEquals(0.982,		Calculator.formulaSe(1471, 845, 5),	DELTA);
		assertEquals(0.257,		Calculator.formulaSe(1471, 1857, 2),	DELTA);
		assertEquals(0.999,		Calculator.formulaSe(2574, 647, 3),	DELTA);
		assertEquals(1,		Calculator.formulaSe(2574, 1455, 8),	DELTA);
		assertEquals(0.930,		Calculator.formulaSe(2574, 2320, 1),	DELTA);
		assertEquals(0.314,		Calculator.formulaSe(2574, 2874, 3),	DELTA);
	}
	
	@Test
	public void formulaSeHandicapWhite() {
		assertEquals(0.416,		Calculator.formulaSe(100, 100, -2),	DELTA);
		assertEquals(0.133,		Calculator.formulaSe(100, 500, -4),	DELTA);
		assertEquals(0.006,		Calculator.formulaSe(100, 1000, -8),	DELTA);
		assertEquals(0.695,		Calculator.formulaSe(500, 100, -1),	DELTA);
		assertEquals(0.442,		Calculator.formulaSe(500, 541, -1),	DELTA);
		assertEquals(0.004,		Calculator.formulaSe(500, 1362, -7),	DELTA);
		assertEquals(0.963,		Calculator.formulaSe(1471, 234, -2),	DELTA);
		assertEquals(0.655,		Calculator.formulaSe(1471, 845, -5),	DELTA);
		assertEquals(0.081,		Calculator.formulaSe(1471, 1857, -2),	DELTA);
		assertEquals(0.999,		Calculator.formulaSe(2574, 647, -3),	DELTA);
		assertEquals(0.946,		Calculator.formulaSe(2574, 1455, -8),	DELTA);
		assertEquals(0.849,		Calculator.formulaSe(2574, 2320, -1),	DELTA);
		assertEquals(0.0,		Calculator.formulaSe(2574, 2874, -3),	DELTA);
	}
	
	@Test
	public void lowKyuWins() {
		PlayerModel player = new PlayerModel(408);
		
		assertEquals(28.428, 	Calculator.calculate(player, new PlayerModel(100), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(39.865, 	Calculator.calculate(player, new PlayerModel(541), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(40.84, 	Calculator.calculate(player, new PlayerModel(762), 4, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(38.3,	 	Calculator.calculate(player, new PlayerModel(812), 5, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(40.859, 	Calculator.calculate(player, new PlayerModel(413), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(37.315, 	Calculator.calculate(player, new PlayerModel(895), 6, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(38.147, 	Calculator.calculate(player, new PlayerModel(1217), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(61.612, 	Calculator.calculate(player, new PlayerModel(1613), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(31.481, 	Calculator.calculate(player, new PlayerModel(1100), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(76.463,	Calculator.calculate(player, new PlayerModel(2512), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(59.891, 	Calculator.calculate(player, new PlayerModel(989), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(44.334, 	Calculator.calculate(player, new PlayerModel(142), 4, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(42.611, 	Calculator.calculate(player, new PlayerModel(203), 3, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(46.950, 	Calculator.calculate(player, new PlayerModel(101), 5, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(44.777, 	Calculator.calculate(player, new PlayerModel(502), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(46.906, 	Calculator.calculate(player, new PlayerModel(0), 	6, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(42.479, 	Calculator.calculate(player, new PlayerModel(-400), 9, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	}
	
	@Test
	public void lowKyuLosses() {
		PlayerModel player = new PlayerModel(408);
		
		assertEquals(-43.396, 	Calculator.calculate(player, new PlayerModel(100), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-31.959, 	Calculator.calculate(player, new PlayerModel(541), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-30.984, 	Calculator.calculate(player, new PlayerModel(762), 4, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-33.524, 	Calculator.calculate(player, new PlayerModel(812), 5, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-30.964, 	Calculator.calculate(player, new PlayerModel(413), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-34.509, 	Calculator.calculate(player, new PlayerModel(895), 6, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-33.677, 	Calculator.calculate(player, new PlayerModel(1217), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-10.211, 	Calculator.calculate(player, new PlayerModel(1613), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-40.342, 	Calculator.calculate(player, new PlayerModel(1100), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(4.639, 	Calculator.calculate(player, new PlayerModel(2512), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-11.933, 	Calculator.calculate(player, new PlayerModel(989), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-27.490, 	Calculator.calculate(player, new PlayerModel(142), 4, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-29.212, 	Calculator.calculate(player, new PlayerModel(203), 3, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-24.874, 	Calculator.calculate(player, new PlayerModel(101), 5, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-27.047, 	Calculator.calculate(player, new PlayerModel(502), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-24.918, 	Calculator.calculate(player, new PlayerModel(0), 	6, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-29.345, 	Calculator.calculate(player, new PlayerModel(-400), 9, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	}
	
	@Test
	public void midKyuWins() {
		PlayerModel player = new PlayerModel(1423);
		
		assertEquals(3.264, 	Calculator.calculate(player, new PlayerModel(213), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(6.270, 	Calculator.calculate(player, new PlayerModel(782), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(16.974, 	Calculator.calculate(player, new PlayerModel(1324), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(34.856, 	Calculator.calculate(player, new PlayerModel(1947), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(36.688, 	Calculator.calculate(player, new PlayerModel(2104), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(37.835, 	Calculator.calculate(player, new PlayerModel(2341), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(2.594, 	Calculator.calculate(player, new PlayerModel(213), 3, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(4.589, 	Calculator.calculate(player, new PlayerModel(782), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(15.361, 	Calculator.calculate(player, new PlayerModel(1324), 1, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(27.365, 	Calculator.calculate(player, new PlayerModel(1947), 4, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(16.488, 	Calculator.calculate(player, new PlayerModel(2104), 8, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(24.407, 	Calculator.calculate(player, new PlayerModel(2341), 9, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(10.339, 	Calculator.calculate(player, new PlayerModel(213), 9, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(20.478, 	Calculator.calculate(player, new PlayerModel(782), 7, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(21.904, 	Calculator.calculate(player, new PlayerModel(1324), 2, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(35.566, 	Calculator.calculate(player, new PlayerModel(1947), 1, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(37.864, 	Calculator.calculate(player, new PlayerModel(2104), 3, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(37.935, 	Calculator.calculate(player, new PlayerModel(2341), 1, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	
	}
	
	@Test
	public void midKyuLosses() {
		PlayerModel player = new PlayerModel(1423);
		
		assertEquals(-32.702, 	Calculator.calculate(player, new PlayerModel(213), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-29.695, 	Calculator.calculate(player, new PlayerModel(782), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-18.991, 	Calculator.calculate(player, new PlayerModel(1324), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-1.110, 	Calculator.calculate(player, new PlayerModel(1947), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.721,	 	Calculator.calculate(player, new PlayerModel(2104), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(1.869,		Calculator.calculate(player, new PlayerModel(2341), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(-33.372, 	Calculator.calculate(player, new PlayerModel(213), 3, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-31.377, 	Calculator.calculate(player, new PlayerModel(782), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-20.605, 	Calculator.calculate(player, new PlayerModel(1324), 1, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-8.601, 	Calculator.calculate(player, new PlayerModel(1947), 4, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-19.478, 	Calculator.calculate(player, new PlayerModel(2104), 8, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-11.560, 	Calculator.calculate(player, new PlayerModel(2341), 9, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(-25.628, 	Calculator.calculate(player, new PlayerModel(213), 9, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-15.488, 	Calculator.calculate(player, new PlayerModel(782), 7, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-14.062, 	Calculator.calculate(player, new PlayerModel(1324), 2, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-0.400, 	Calculator.calculate(player, new PlayerModel(1947), 1, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(1.898, 	Calculator.calculate(player, new PlayerModel(2104), 3, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(1.970, 	Calculator.calculate(player, new PlayerModel(2341), 1, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	}
	
	
	@Test
	public void strongDanWins() {
		PlayerModel player = new PlayerModel(2341);
		
		assertEquals(0.098, 	Calculator.calculate(player, new PlayerModel(308), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.111, 	Calculator.calculate(player, new PlayerModel(847), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.235, 	Calculator.calculate(player, new PlayerModel(1488), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.756, 	Calculator.calculate(player, new PlayerModel(1856), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(1.716, 	Calculator.calculate(player, new PlayerModel(2045), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(3.355, 	Calculator.calculate(player, new PlayerModel(2191), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(5.618, 	Calculator.calculate(player, new PlayerModel(2313), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(7.281, 	Calculator.calculate(player, new PlayerModel(2387), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(9.699, 	Calculator.calculate(player, new PlayerModel(2501), 0, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(0.096, 	Calculator.calculate(player, new PlayerModel(308), 1, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.094, 	Calculator.calculate(player, new PlayerModel(847), 6, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.111, 	Calculator.calculate(player, new PlayerModel(1488), 3, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.303, 	Calculator.calculate(player, new PlayerModel(1856), 2, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.116, 	Calculator.calculate(player, new PlayerModel(2045), 5, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.608, 	Calculator.calculate(player, new PlayerModel(2191), 3, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(4.513, 	Calculator.calculate(player, new PlayerModel(2313), 1, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.775, 	Calculator.calculate(player, new PlayerModel(2387), 4, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(3.806, 	Calculator.calculate(player, new PlayerModel(2501), 3, GameResult.WIN, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(0.138, 	Calculator.calculate(player, new PlayerModel(308), 9, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.422, 	Calculator.calculate(player, new PlayerModel(847), 9, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(6.167, 	Calculator.calculate(player, new PlayerModel(1488), 9, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(11.219, 	Calculator.calculate(player, new PlayerModel(1856), 8, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(5.237, 	Calculator.calculate(player, new PlayerModel(2045), 3, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(6.235, 	Calculator.calculate(player, new PlayerModel(2191), 2, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(6.732, 	Calculator.calculate(player, new PlayerModel(2313), 1, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(10.313, 	Calculator.calculate(player, new PlayerModel(2387), 2, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(12.315, 	Calculator.calculate(player, new PlayerModel(2501), 4, GameResult.WIN, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	
	}
	
	@Test
	public void strongDanLosses() {
		PlayerModel player = new PlayerModel(2341);
		
		assertEquals(-12.184, 	Calculator.calculate(player, new PlayerModel(308), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-12.171, 	Calculator.calculate(player, new PlayerModel(847), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-12.047, 	Calculator.calculate(player, new PlayerModel(1488), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-11.526, 	Calculator.calculate(player, new PlayerModel(1856), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-10.566, 	Calculator.calculate(player, new PlayerModel(2045), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-8.926, 	Calculator.calculate(player, new PlayerModel(2191), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-6.663, 	Calculator.calculate(player, new PlayerModel(2313), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-5.000, 	Calculator.calculate(player, new PlayerModel(2387), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-2.583, 	Calculator.calculate(player, new PlayerModel(2501), 0, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(-12.185, 	Calculator.calculate(player, new PlayerModel(308), 1, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-12.188, 	Calculator.calculate(player, new PlayerModel(847), 6, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-12.171, 	Calculator.calculate(player, new PlayerModel(1488), 3, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-11.979, 	Calculator.calculate(player, new PlayerModel(1856), 2, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-12.166, 	Calculator.calculate(player, new PlayerModel(2045), 5, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-11.674, 	Calculator.calculate(player, new PlayerModel(2191), 3, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-7.769, 	Calculator.calculate(player, new PlayerModel(2313), 1, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-11.506, 	Calculator.calculate(player, new PlayerModel(2387), 4, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		assertEquals(-8.476, 	Calculator.calculate(player, new PlayerModel(2501), 3, GameResult.LOSS, GameColor.BLACK, TournamentClass.CLASS_A), DELTA);
		
		assertEquals(-12.144, 	Calculator.calculate(player, new PlayerModel(308), 9, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-11.860, 	Calculator.calculate(player, new PlayerModel(847), 9, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-6.114, 	Calculator.calculate(player, new PlayerModel(1488), 9, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-1.062, 	Calculator.calculate(player, new PlayerModel(1856), 8, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-7.045, 	Calculator.calculate(player, new PlayerModel(2045), 3, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-6.047, 	Calculator.calculate(player, new PlayerModel(2191), 2, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-5.549, 	Calculator.calculate(player, new PlayerModel(2313), 1, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(-1.968, 	Calculator.calculate(player, new PlayerModel(2387), 2, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
		assertEquals(0.034, 	Calculator.calculate(player, new PlayerModel(2501), 4, GameResult.LOSS, GameColor.WHITE, TournamentClass.CLASS_A), DELTA);
	
	}
	
	
	@Test
	public void wysockiLewTestCase() {
		GameResult result = GameResult.WIN;
		TournamentClass category = TournamentClass.CLASS_A;

		double 	myGor = 1914,
				opponnentGor = 2103,
				handicap = OpponentModel.NO_HANDICAP,
				expectedResult = 17.266f;
		
		double actualResult = Calculator.calculateRatingChange(myGor, opponnentGor, result.value, handicap, category.value);
	 
	 	assertEquals (expectedResult, actualResult, 0.1);
	 }
	
}
