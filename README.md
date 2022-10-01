# FilmQueryProject

# Description
This project presents the user with a menu giving them the option to search for a film
in the connected database by its unique Film ID or to search for all films in the database
by typing in a keyword. 

Every time a film is found, the film information, including ID, title, description, release year,
rating, language, and list of actors, is printed to the screen.

These options are presented after each selection until the user chooses to exit the program.

# Technologies Used
Eclipse, MacOs, MySql, SQL, MAMP, git, GitHub, Java, Atom

# Lessons Learned

For some reason, I had a logic block when it came to setting the list of actors into the
film object as the film was being called by the database. This was easily solved by calling the appropriate
method inside the methods where films were being pulled from the database.

The next challenge I came across was realizing that reading the database with a keyword could
potentially create zero to multiple films and solved that logic problem by returning a List of 
films from the method, rather than just one.

The other challenges involved remembering that the list size would be == 0 if no films were found
(not .equals(null)), using an InputMismatchException rather than a  more generic exception so that my try could make
an appropriate message for a filmID that returned null, and remembering that dastardly nextLine statment for the scanner
so that things don't get wiggy.