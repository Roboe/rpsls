package roboe.pptsl.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import roboe.pptsl.R;

public class SelectMove extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectmove);
	}

	public void game(View v) {
		Intent i = new Intent();
		i.putExtra("result", Move.getOrdinalByViewId(v.getId()));
		setResult(RESULT_OK, i);
		finish();
	}

}
