/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:43:56
 */
package test;

import com.inti3e.database.dao.TempDao;
import com.inti3e.model.Temperature;

/**
 * The Class Apl.
 */
public class Apl {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main( String[] args ) {
		TempDao d = new TempDao();
		for(Temperature t: d.getAllTemps()){
			System.out.println(t.getDate());
			System.out.println(t.getTemp());
			System.out.println(t.getTime());
			System.out.println();
		}
	}
}
