package roboe.pptsl.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import roboe.pptsl.R;

public class Menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		boolean sheldonMode = settings.getBoolean("sheldonMode", false);

		if (!sheldonMode) {
			findViewById(R.id.rbSheldon).setVisibility(View.GONE);
			findViewById(R.id.TextView1).setOnLongClickListener(
					new OnLongClickListener() {
						public boolean onLongClick(View v) {
							// Graphical stuff
							findViewById(R.id.rbSheldon).setVisibility(
									View.VISIBLE);
							AlphaAnimation showUp = new AlphaAnimation(0f, 1f);
							showUp.setDuration(700);
							findViewById(R.id.rbSheldon).startAnimation(showUp);

							// Notifying user
							Toast.makeText(
									findViewById(R.id.rbSheldon).getContext(),
									getString(R.string.sheldonModeActivated),
									Toast.LENGTH_SHORT).show();

							// Making permanent change
							SharedPreferences settings = getPreferences(MODE_PRIVATE);
							SharedPreferences.Editor editor = settings.edit();
							editor.putBoolean("sheldonMode", true);
							editor.commit();
							return false;
						}
					});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.menu_rules:
			i = new Intent(this, Rules.class);
			startActivity(i);
			return true;
		case R.id.menu_records:
			i = new Intent(this, Records.class);
			startActivity(i);
			return true;
		case R.id.menu_information:
			i = new Intent(this, Information.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void launchMode(View v) {
		Intent i;
		switch (v.getId()) {
		case R.id.rbCpu:
			i = new Intent(this, CpuGame.class);
			startActivity(i);
			break;
		case R.id.rbHuman:
			i = new Intent(this, HumanGame.class);
			i.putExtra("local", true);
			startActivity(i);
			break;
		case R.id.rbSheldon:
			i = new Intent(this, SheldonGame.class);
			startActivity(i);
			break;
		}
	}
}
