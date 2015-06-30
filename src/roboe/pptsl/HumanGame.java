package roboe.pptsl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HumanGame extends CpuGame {

	private boolean player1Turn = false;
	private boolean player2Turn = false;
	private int player1Move;
	private int player2Move;
	private static final int MOVE1 = 1;
	private static final int MOVE2 = 2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_human);
		((TextView) findViewById(R.id.player1Text)).setText(this
				.getString(R.string.player) + " 1");
		((TextView) findViewById(R.id.player2Text)).setText(this
				.getString(R.string.player) + " 2");

		initComponents();
	}

	public void makeMove(View v) {
		if (v.getId() == R.id.button1) {
			Intent i = new Intent(this, SelectMove.class);
			startActivityForResult(i, MOVE1);
		} else if (v.getId() == R.id.button2) {
			Intent i = new Intent(this, SelectMove.class);
			startActivityForResult(i, MOVE2);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case MOVE1:
				player1Move = data.getIntExtra("result", 5);
				findViewById(R.id.button1).setEnabled(false);
				player1Turn = true;
				if (!player2Turn) {
					changeImage(imagePlayer1, R.drawable.selected);
					makeOpaque(imagePlayer1);
					changeImage(imagePlayer2, R.drawable.blank);
					makeOpaque(imagePlayer2);
				}
				break;
			case MOVE2:
				player2Move = data.getIntExtra("result", 5);
				findViewById(R.id.button2).setEnabled(false);
				player2Turn = true;
				if (!player1Turn) {
					changeImage(imagePlayer2, R.drawable.selected);
					makeOpaque(imagePlayer2);
					changeImage(imagePlayer1, R.drawable.blank);
					makeOpaque(imagePlayer1);
				}
				break;
			}

			if (player1Turn && player2Turn) {
				game = new Game(player1Move, player2Move);
				gameEffects();
			}
		}
	}

	@Override
	protected void gameEffects() {
		super.gameEffects();

		player1Turn = false;
		player2Turn = false;
		findViewById(R.id.button1).setEnabled(true);
		findViewById(R.id.button2).setEnabled(true);
	}

	@Override
	public void onBackPressed() {
		if (player1Turn || player2Turn) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage(R.string.dialogMessage)
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).setNegativeButton(android.R.string.no, null);

			AlertDialog dialog = builder.create();
			dialog.show();
		} else {
			finish();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	protected void onResume() {
		super.onPause();
	}
}