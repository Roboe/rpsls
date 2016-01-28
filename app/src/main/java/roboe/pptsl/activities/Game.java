package roboe.pptsl.activities;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import roboe.pptsl.R;

public class Game {

	private Move player1;
	private Move player2;
	private int winner;

	protected static final int P1WINS = 1;
	protected static final int DRAW = 0;
	protected static final int P2WINS = -1;

	// Constructors
	public Game(int p1) {
		constructor(p1, (int) (Math.random() * Move.values().length));
	}

	public Game(int p1, int p2) {
		constructor(p1, p2);
	}

	private void constructor(int p1, int p2) {
		if (p1 > Move.values().length - 1 || p2 > Move.values().length - 1) {
			
		} else {
			player1 = Move.values()[p1];
			player2 = Move.values()[p2];
			winner = player1.beats(player2);
		}
	}

	// Public
	public int getWinner() {
		return winner;
	}

	public Move getPlayer1() {
		return player1;
	}

	public Move getPlayer2() {
		return player2;
	}

	public int getMessage() {
		return (winner == P1WINS) ? R.string.win
				: ((winner == P2WINS) ? R.string.lose : R.string.draw);
	}

}

enum Move {
	ROCK, PAPER, SCISSORS, SPOCK, LIZARD;
	private static int[] imageIDs = { R.drawable.s0, R.drawable.s1,
			R.drawable.s2, R.drawable.s3, R.drawable.s4 };
	private static int[] viewIDs = { R.id.image0, R.id.image1, R.id.image2,
			R.id.image3, R.id.image4 };
	private static int[] textIDs = { R.string.rock, R.string.paper,
			R.string.scissors, R.string.spock, R.string.lizard };

	public static int getOrdinalByViewId(int id) {
		int ret;
		boolean cont = true;
		for (ret = viewIDs.length; cont; ret--) {
			if (id == viewIDs[ret - 1])
				cont = false;
		}
		return ret;
	}

	public int getImageId() {
		return imageIDs[ordinal()];
	}

	public int getTextId() {
		return textIDs[ordinal()];
	}

	public int getViewId() {
		return viewIDs[ordinal()];
	}

	public int beats(Move m) {
		int winner, result = this.ordinal() - m.ordinal();
		if (result == 0) { // El movimiento es el mismo, empate.
			winner = Game.DRAW;
		} else if (Math.abs(result) % 2 == 0) { // Si el resultado es par
			if (result < 0) { // y player1 es menor
				winner = Game.P1WINS;
			} else { // y player1 es mayor
				winner = Game.P2WINS;
			}
		} else if (Math.abs(result % 2) == 1) { // Si el resultado es impar
			if (result < 0) { // y player1 es menor
				winner = Game.P2WINS;
			} else { // y player1 es mayor
				winner = Game.P1WINS;
			}
		} else {
			Log.e(Resources.getSystem().getString(R.string.app_name),
					"Move.beats() should not send this message.");
			winner = Game.DRAW;
		}

		return winner;
	}

	public int getStringResultId(Move move, Context context) {
		int n1 = this.ordinal(), n2 = move.ordinal();
		int id;
		if (n1 != n2) {
			id = context.getResources().getIdentifier(
					"r" + ((n1 < n2) ? n1 : n2) + "vs" + ((n1 < n2) ? n2 : n1),
					"string", "roboe.pptsl");
		} else {
			id = R.string.rDraw;
		}
		return id;
	}
}
