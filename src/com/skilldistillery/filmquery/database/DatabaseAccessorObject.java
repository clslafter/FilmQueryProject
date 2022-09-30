package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver not found");
			e.printStackTrace();

		}
	}
	
  @Override
  public Film findFilmById(int filmId) throws SQLException {
//	  Implement the findFilmById method that takes an int film ID, 
//	  and returns a Film object (or null, if the film ID returns no data.)
	  
	  String user = "student";
	  String pass = "student";

		String sql;
		sql = "SELECT * FROM film WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();) {

//		    Sets variable for the bind (?)    similar: int first_? = storeId
			stmt.setInt(1, filmId);
			
			Film film = new Film(rs.getInt("id"), 
							   rs.getString("title"),
							   rs.getInt("release_year"),
							   rs.getInt("language_id"),
							   rs.getInt("rental_duration"),
							   rs.getDouble("rental_rate"),
							   rs.getInt("length"),
							   rs.getDouble("replacement_cost"),
							   rs.getString("rating"),
							   rs.getString("special_features"));

			while (rs.next()) {
				System.out.println("Full Name: " + rs.getString("Full Name") + ", " + "Email: " + rs.getString("email")
						+ ", " + "Last Update: " + rs.getString("last_update"));
				
			}
		}
	  
    return null;
  }

@Override
public Actor findActorById(int actorId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<Actor> findActorsByFilmId(int filmId) {
	// TODO Auto-generated method stub
	return null;
}

}
