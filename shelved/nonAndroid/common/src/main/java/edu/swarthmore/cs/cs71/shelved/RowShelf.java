package edu.swarthmore.cs.cs71.shelved;

public interface RowShelf {

    // Adds a new book object to the current shelf, at the specified position.
    void addBook(ShelvedBook shelvedBook, int position);

    // Remove book at the specified position.
    void removeBook(int position);

    // Change the position of a book that is already in the shelf, taking the
    // book object from a specified position and moving it to a new given
    // position.
    void resetPosition(int oldPosition, int newPosition);
    int getId();

    ShelvedBook getBook(int position);
}