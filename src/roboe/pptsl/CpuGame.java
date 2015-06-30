package roboe.pptsl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CpuGame extends Activity {

	protected ImageView imagePlayer1;
	protected TextView textPlayer1;
	protected int nWinsP1 = 0;
	protected ImageView imagePlayer2;
	protected TextView textPlayer2;
	protected int nWinsP2 = 0;
	protected Toast lastToast;
	protected Game game;

	protected static AlphaAnimation toTransparent;
	static {
		toTransparent = new AlphaAnimation(0.7f, 0.2f);
		toTransparent.setDuration(700);
		toTransparent.setFillAfter(true);
	}
	protected static AlphaAnimation toOpaque;
	static {
		toOpaque = new AlphaAnimation(1f, 1f);
		toOpaque.setDuration(0);
		toOpaque.setFillAfter(true);
	}
	protected BDsquedejanSeQcueLas ownDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cpu);
		initComponents();
	}

	protected void initComponents() {
		imagePlayer1 = (ImageView) this.findViewById(R.id.player1);
		textPlayer1 = (TextView) this.findViewById(R.id.player1SubText);
		imagePlayer2 = (ImageView) this.findViewById(R.id.player2);
		textPlayer2 = (TextView) this.findViewById(R.id.player2SubText);

		ownDB = new BDsquedejanSeQcueLas(this,
				BDsquedejanSeQcueLas.playerRecords, null,
				BDsquedejanSeQcueLas.databaseVersion);
	}

	public void game(View v) {
		game = new Game(Move.getOrdinalByViewId(v.getId()));
		gameEffects();
	}

	protected void gameEffects() {
		Log.d("PPTLS",
				"Player: " + game.getPlayer1() + "; " + "CPU: "
						+ game.getPlayer2() + "; " + "Result: "
						+ game.getWinner());
		changeImage(imagePlayer1, textPlayer1, game.getPlayer1());
		changeImage(imagePlayer2, textPlayer2, game.getPlayer2());
		manageAlpha(game);
		String message = getString(game.getPlayer1().getStringResultId(
				game.getPlayer2(), this))
				+ "\n" + getString(game.getMessage());

		if (game.getWinner() == Game.P1WINS) {
			nWinsP1++;
		} else if (game.getWinner() == Game.P2WINS) {
			nWinsP2++;
		}

		if (lastToast != null) {
			lastToast.cancel();
		}
		lastToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		lastToast.show();
	}

	protected void manageAlpha(Game game) {
		switch (game.getWinner()) {
		case Game.P1WINS:
			makeOpaque(imagePlayer1);
			makeTransparent(imagePlayer2);
			break;
		case Game.P2WINS:
			makeTransparent(imagePlayer1);
			makeOpaque(imagePlayer2);
			break;
		case Game.DRAW:
			makeTransparent(imagePlayer1);
			makeTransparent(imagePlayer2);
			break;
		}
	}

	protected void makeTransparent(ImageView iv) {
		iv.startAnimation(toTransparent);
	}

	protected void makeOpaque(ImageView iv) {
		iv.startAnimation(toOpaque);
	}

	protected void changeImage(ImageView iv, TextView tv, Move move) {
		iv.setImageDrawable(this.getResources().getDrawable(move.getImageId()));
		tv.setText(move.getTextId());
	}

	protected void changeImage(ImageView iv, int drawableID) {
		iv.setImageDrawable(this.getResources().getDrawable(drawableID));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("player1", 5);
		editor.putInt("player2", 5);
		editor.commit();
		game = null;

		writeRecordToDatabase();
	}

	protected void writeRecordToDatabase() {
		SQLiteDatabase db = ownDB.getWritableDatabase();
		db.execSQL("INSERT INTO " + BDsquedejanSeQcueLas.tableName
				+ " (player, cpu, date) VALUES (" + nWinsP1 + ", " + nWinsP2
				+ ", '" + BDsquedejanSeQcueLas.getDate() + "');");
		db.close();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (game != null) {
			SharedPreferences settings = getPreferences(MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("player1", game.getPlayer1().ordinal());
			editor.putInt("player2", game.getPlayer2().ordinal());
			editor.commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		int p1 = settings.getInt("player1", 5);
		int p2 = settings.getInt("player2", 5);

		if (p1 != 5 || p2 != 5) {
			game = new Game(p1, p2);
			changeImage(imagePlayer1, textPlayer1, game.getPlayer1());
			changeImage(imagePlayer2, textPlayer2, game.getPlayer2());
			manageAlpha(game);
		}

	}

}