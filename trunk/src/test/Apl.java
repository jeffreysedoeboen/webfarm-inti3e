package test;

import com.inti3e.database.dao.TempDao;
import com.inti3e.model.DataManager;
import com.inti3e.model.Temperature;


public class Apl {

	public static void main( String[] args ) {
//		DataManager dm = DataManager.getInstance();
		TempDao d = new TempDao();
		for(Temperature t: d.getAllTemps()){
			System.out.println(t.getDate());
			System.out.println(t.getTemp());
			System.out.println(t.getTime());
			System.out.println();
		}
	}
}
