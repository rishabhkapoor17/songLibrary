# songLibrary
Song library using Java FXML and Java 13 with basic options to add, edit, and delete songs.
Songs have song details including artist, name, year released, and album, and are listed in alphabetic order of name, then artist, in the library. 
Popups are displayed if the user tries to commit an invalid action, or to confirm the action of the user.
Used Song as an object, with the appropriate fields, and a SongComparator class to be able to compare Songs using an interface.
SongLib has the main method and starts the program, calling the Controller class which handles the actions of the user. The UIFxml document contains FXML code that was made from SceneBuilder, to create the user interface for the library.
