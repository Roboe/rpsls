package roboe.pptsl;

import java.util.NoSuchElementException;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Rules extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules);
		setRulesText(getRulesText());
	}

	private void setRulesText(String strong) {
		((TextView) findViewById(R.id.rulesText)).setText(strong);
		((TextView) findViewById(R.id.rulesText))
				.setMovementMethod(new ScrollingMovementMethod());
	}

	private String getRulesText() {
		/* This was a class work requirement: reading from a file.
		 * Thus Spanish only and stuff.
		 * You are free to change this part. 
		 * Change it. 
		 * PLEASE.
		 */
		Scanner sc = new Scanner(getResources().openRawResource(R.raw.rules));
		String text = "Reglas: ", t;
		try {
			while ((t = sc.nextLine()) != null) {
				text = text.concat("\n").concat(t);
			}
		} catch (NoSuchElementException e) {
		}
		sc.close();
		return text;
	}
}
