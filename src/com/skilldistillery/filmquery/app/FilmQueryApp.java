package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
//		app.test();
    app.launch();
	}

	private void test() throws SQLException {
		System.out.println();
		Film film = db.findFilmById(4);
		System.out.print(film);
		System.out.println("Language: " + db.filmLanguageByFilmId(4));
		List<Actor> actors = film.getActors();
		for (Actor actor : actors) {
			System.out.println(actor);
		}
		System.out.println("***************************************************");
		System.out.println();
	}

	private void launch() throws SQLException {
		Scanner sc = new Scanner(System.in);

		startUserInterface(sc);

		sc.close();
	}

	private void startUserInterface(Scanner sc) throws SQLException {
//    loops display menu and gets user input, does what the user asks for
		String input = null;

		do {
			filmQueryMenu();
			input = sc.nextLine();

			switch (input) {
			
			case "1":
//			call method that prompts to query by film id then prints results
				promptForFilmId(sc);
				break;

			case "2":
//			call method that prompts to query by keyword then prints results
				promptForKeyword(sc);
				break;

			case "3":
				System.out.println("Thank you. Enjoy the film!");
				break;

			default:
				System.out.println("Oops. That's not a valid entry. Please choose a number between 1 and 3.");
				break;

			}
		} while (!input.equals("3"));

	}

	private void filmQueryMenu() {
		// method for menu display

		System.out.println("Welcome! Please choose an option from the following menu:");
		System.out.println();
		System.out.println("***************************************************");
		System.out.println("1. Look up a film by the Film's ID number");
		System.out.println("2. Search Films by Keyword");
		System.out.println("3. Exit the Application");
		System.out.println("***************************************************");
		System.out.println();
	}

	private void promptForFilmId(Scanner sc) throws SQLException {
//		Prompt for a number for Film ID.
		System.out.println("You have chosen to search for a film by it's Film ID.");
		System.out.println("Please enter the film's ID number you wish to view.");
//		Catch input mismatch.

				
		try {
			int filmId = sc.nextInt();
			sc.nextLine();
//			Call findFilmByKey method
			Film film = db.findFilmById(filmId);

//			Check return for null. If null, make an appropriate user message
			if (film == null) {
				System.out.println("Sorry, we don't have a record of a film with that ID.");
				System.out.println();
			} else {
//				If not null, print film results
				System.out.println();
				System.out.print(film);
				System.out.println("Language: " + db.filmLanguageByFilmId(filmId));
				List<Actor> actors = film.getActors();
				for (Actor actor : actors) {
					System.out.println(actor);
				}
				System.out.println("***************************************************");
				System.out.println();

			}

		} catch (InputMismatchException e) {
			System.out.println("Oops! That's not a valid entry.");
			sc.nextLine();
//			e.printStackTrace();
//			return;
		}

	}

	private void promptForKeyword(Scanner sc) throws SQLException {
//		Prompt for a keyword
		System.out.println("You have chosen to search for films by Keyword.");
		System.out.println("Please enter a Keyword.");
		String key = sc.nextLine();

//		Call findFilmsByKey method
		List<Film> films = db.findFilmsByKey(key);
//		Check return for null. If null, make an appropriate user message
		if (films.size() == 0) {
			System.out.println("Sorry, we don't have a record of any films with that Keyword.");
			System.out.println();
		} else {
//			If not null, print film results for each film
			for (Film film : films) {
				System.out.println();
				System.out.print(film);
				System.out.println("Language: " + db.filmLanguageByFilmId(film.getId()));
				List<Actor> actors = film.getActors();
				for (Actor actor : actors) {
					System.out.println(actor);
				}
				System.out.println("***************************************************");
				System.out.println();

			}
		}

	}

}
