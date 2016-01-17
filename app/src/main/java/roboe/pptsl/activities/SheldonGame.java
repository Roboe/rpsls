package roboe.pptsl.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import roboe.pptsl.R;

public class SheldonGame extends CpuGame {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((TextView) findViewById(R.id.player2Text)).setText(R.string.sheldon);
	}

	public void game(View v) {
		int p1 = Move.getOrdinalByViewId(v.getId());
		int p2 = p1 + 1;
		game = new Game(p1, (p2 == Move.values().length) ? 0 : p2);
		gameEffects();
	}

	protected void writeRecordToDatabase() {
	}
}