package roboe.pptsl;

import java.util.Calendar;
import java.util.Date;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class BDsquedejanSeQcueLas extends SQLiteOpenHelper {

	public static String playerRecords = "PlayerRecords";
	public static String tableName = "records";
	public static int databaseVersion = 1;

	private String createTable = "CREATE TABLE " + tableName
			+ " (player INTEGER, cpu INTEGER, date TEXT)";

	public BDsquedejanSeQcueLas(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public BDsquedejanSeQcueLas(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public static String getDate() {
		Date d = new Date();
		return d.getYear()
				+ 1900
				+ "/"
				+ ((d.getMonth() < 9) ? "0" + (1 + d.getMonth()) : (1 + d
						.getMonth()))
				+ "/"
				+ d.getDate()
				+ " - "
				+ d.getHours()
				+ "."
				+ d.getMinutes()
				+ "."
				+ ((d.getSeconds() < 10) ? "0" + d.getSeconds() : d
						.getSeconds());

		// No uso Calendar porque no puedo referenciar estáticamente a sus
		// métodos y no tengo tiempo para buscar una solución
	}
}
