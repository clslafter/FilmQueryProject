package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		Film film = null;

		String user = "student";
		String pass = "student";

//		SQL query in film table
		String sql;
		sql = "SELECT * FROM film WHERE id = ?";

//		Connects to database
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);

//		    Sets variable for the bind 
		stmt.setInt(1, filmId);

		ResultSet rs = stmt.executeQuery();
		{

//			If found, makes new film object and returns it
			if (rs.next()) {
				film = new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getInt("rental_duration"),
						rs.getDouble("rental_rate"), rs.getInt("length"), rs.getDouble("replacement_cost"),
						rs.getString("rating"), rs.getString("special_features"));

//				set list of actors from film
				List<Actor> actors = findActorsByFilmId(filmId);
				film.setActors(actors);
			}
			rs.close();
			stmt.close();
			conn.close();

			return film;
		}

	}// findFilmById method cb

	@Override
	public List <Film> findFilmsByKey(String key) throws SQLException {
//		  Implement the findFilmByKey method that takes an String keyword, 
//		  and returns a Film object (or null, if the film ID returns no data.)
		List <Film> films = new ArrayList<>();

		String user = "student";
		String pass = "student";

//		SQL query in film table
		String sql;
		sql = "SELECT * FROM film WHERE title LIKE ? or description LIKE ?";

//		Connects to database
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);

//		    Sets variable for the bind 
		stmt.setString(1, "%" + key + "%");
		stmt.setString(2, "%" + key + "%");

		ResultSet rs = stmt.executeQuery();
		{

//			If found, makes new film object and returns it
			while (rs.next()) {
				Film film = new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getInt("rental_duration"),
						rs.getDouble("rental_rate"), rs.getInt("length"), rs.getDouble("replacement_cost"),
						rs.getString("rating"), rs.getString("special_features"));

//				set list of actors from film
				int filmId = rs.getInt("id");
				List<Actor> actors = findActorsByFilmId(filmId);
				film.setActors(actors);
				
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();

			return films;
		}

	}// findFilmByKey method cb

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		// Implement findActorById method that takes an int actor ID,
//	and returns an Actor object (or null, if the actor ID returns no data.)

		Actor actor = null;

		String user = "student";
		String pass = "student";

//		SQL query in actor table
		String sql;
		sql = "SELECT * FROM actor WHERE id = ?";

//		Connects to database
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);

//		    Sets variable for the bind 
		stmt.setInt(1, actorId);

		ResultSet rs = stmt.executeQuery();
		{

//			If found, makes new actor object and returns it
			if (rs.next()) {
				actor = new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
			}
			rs.close();
			stmt.close();
			conn.close();

			return actor;
		}

	}// findActorById method cb

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		// Implement findActorsByFilmId with an appropriate List implementation
//		that will be populated using a ResultSet and returned.
		List<Actor> actors = new ArrayList<>();

		String user = "student";
		String pass = "student";

//		SQL query in actor table
		String sql;
		sql = "SELECT actor.id, actor.first_name, actor.last_name FROM actor\n"
				+ "JOIN film_actor ON actor.id = film_actor.actor_id\n" + "JOIN film ON film.id = film_actor.film_id\n"
				+ "WHERE film.id = ?";

//		Connects to database
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);

//		    Sets variable for the bind 
		stmt.setInt(1, filmId);

		ResultSet rs = stmt.executeQuery();
		{

//			If found, makes new actor arraylist and populates it with the actors
			while (rs.next()) {
				int actorId = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");

				Actor actor = new Actor(actorId, firstName, lastName);

				actors.add(actor);

			}
			rs.close();
			stmt.close();
			conn.close();

			return actors;
		}

	}// findActorsByFilmId method cb

	@Override
	public String filmLanguageByFilmId(int filmId) throws SQLException {
		String language = null;

//		takes a int filmId and returns the language of the film
		String user = "student";
		String pass = "student";

//		SQL query in actor table
		String sql;
		sql = "SELECT language.name FROM language JOIN film ON language.id = film.language_id WHERE film.id = ?;";

//		Connects to database
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);

//		    Sets variable for the bind 
		stmt.setInt(1, filmId);

		ResultSet rs = stmt.executeQuery();
		{

//			If found, makes new actor object and returns it
			if (rs.next()) {
				language = rs.getString("name");
			}
			rs.close();
			stmt.close();
			conn.close();

			return language;
		}

	}

}
