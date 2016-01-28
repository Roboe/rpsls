package roboe.pptsl.activities;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import roboe.pptsl.utils.BDsquedejanSeQcueLas;
import roboe.pptsl.R;

public class Records extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		BDsquedejanSeQcueLas ownDB = new BDsquedejanSeQcueLas(this,
				BDsquedejanSeQcueLas.playerRecords, null,
				BDsquedejanSeQcueLas.databaseVersion);

		SQLiteDatabase db = ownDB.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM "
				+ BDsquedejanSeQcueLas.tableName + " ORDER BY player DESC",
				null);

		String res = "";
		if (c.moveToFirst()) {
			do {
				res = res.concat("Jugador: ")
						.concat(Integer.toString(c.getInt(0)))
						.concat("\tCPU: ")
						.concat(Integer.toString(c.getInt(1)))
						.concat("\tFecha: ").concat(c.getString(2))
						.concat("\n\n");
			} while (c.moveToNext());

		}

		((TextView) findViewById(R.id.text)).setText(res);
		db.close();
	}

}
