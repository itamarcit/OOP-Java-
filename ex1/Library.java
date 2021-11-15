/**
 * This class represents a library, which hold a collection of books.
 * Patrons can register at the library to be able to check out books,
 * if a copy of the requested book is available.
 */
class Library {

    /**
     * Max number of books the library can hold.
     */
    private final int maxBookCapacity;

    /**
     * Max number of books a patron can borrow.
     */
    private final int maxBorrowedBooks;

    /**
     * Max number of patrons that can register in the library.
     */
    private final int maxPatronCapacity;

    /**
     * An array of all the books in the library.
     */
    private Book[] books;

    /**
     * Amount of books currently in the library.
     */
    private int currentBooks = 0;

    /**
     *  An array of all the patrons registered in the library.
     */
    private Patron[] patrons;

    /**
     *  The amount of patrons currently registered in the library.
     */
    private int currentPatrons = 0;

    /* -----=  Constructors  =----- */

    /**
     * Creates a new library with the given parameters.
     * @param maxBookCapacity The maximal number of books this library can hold.
     * @param maxBorrowedBooks The maximal number of books this library allows a single patron to borrow at the same
     *                         time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        this.maxBookCapacity = maxBookCapacity;
        this.maxBorrowedBooks = maxBorrowedBooks;
        this.maxPatronCapacity = maxPatronCapacity;
        this.books = new Book[maxBookCapacity];
        this.patrons = new Patron[maxPatronCapacity];
    }

    /* -----=  Instance Methods  =----- */

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * @param book The book to add to this library.
     * @return A non-negative id number for the book if there is a spot and the book was successfully added.
     * The current id of the book if already in the library. Otherwise, a negative number.
     */
    int addBookToLibrary(Book book) {
        int result = isBookInLibrary(book);
        if(this.isBookInLibrary(book) != -1) {
            return result;
        }
        else if(currentBooks == maxBookCapacity) {
            return -1;
        }
        books[currentBooks] = book;
        currentBooks++;
        return currentBooks - 1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this book is
     * available, the given patron isn't already borrowing the maximal number of books allowed, and if the patron
     * will enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return True if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if(!isBookAvailable(bookId) || !isPatronIdValid(patronId) || !patrons[patronId].willEnjoyBook(books[bookId])
                || sumPatronBooks(patronId) >= maxBorrowedBooks) {
            return false;
        }
        books[bookId].currentBorrowerId = patronId;
        return true;
    }

    /**
     * Returns the id number of the given book if it is owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return A non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        return isBookInLibrary(book);
    }

    /**
     * Returns the id number of the given patron if he is registered to this library, -1 otherwise.
     * @param patron The patron for which to find the id number.
     * @return A non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        return isPatronInLibrary(patron);
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId The id number of the book to check.
     * @return True if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        return isBookIdValid(bookId) && books[bookId].currentBorrowerId == -1;
    }

    /**
     * Returns if the given number is an id of a book in the library.
     * @param bookId The id to check.
     * @return True if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        return currentBooks > bookId && bookId >= 0;
    }

    /**
     * Returns if the given number is an id of a patron in the library.
     * @param patronId The id to check.
     * @return True if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        return patronId >= 0 && patronId < currentPatrons;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return A non-negative id number for the patron if there is a spot and the patron was successfully registered.
     * The current id of the patron if already registered. Otherwise, a negative number.
     */
    int registerPatronToLibrary(Patron patron) {
        int result = this.isPatronInLibrary(patron);
        if(result != -1) {
            return result;
        }
        else if(maxPatronCapacity == currentPatrons) {
            return -1;
        }
        patrons[currentPatrons] = patron;
        currentPatrons++;
        return currentPatrons - 1;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId) {
        if(!isBookIdValid(bookId)) {
            return;
        }
        books[bookId].currentBorrowerId = -1;
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he will enjoy,
     * if any such exist.
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given will enjoy the most. 'null' if no book is available.
     */
    Book suggestBookToPatron(int patronId) {
        if(!isPatronIdValid(patronId) || currentBooks == 0){
            return null;
        }
        int max = patrons[patronId].getBookScore(books[0]), id = 0;
        for(int i = 1; i < currentBooks; i++) {
            if(isBookAvailable(i) && patrons[patronId].getBookScore(books[i]) > max) {
                max = patrons[patronId].getBookScore(books[i]);
                id = i;
            }
        }
        if(id == 0 && !isBookAvailable(id)) { // if the for loop didn't change the id, check if available
            return null;
        }
        if(patrons[patronId].willEnjoyBook(books[id])) {
            return books[id];
        }
        return null;
    }


    /* -----=  Private Methods  =----- */

    /**
     * Checks if the book is already in the library.
     * @param book The book to search for.
     * @return Id of the book if exists, -1 otherwise.
     */
    private int isBookInLibrary(Book book) {
        for(int i = 0; i < currentBooks; i++) {
            if(book == books[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if the patron is registered in the library.
     * @param patron The patron to search for.
     * @return The id of the patron if he exists, -1 otherwise.
     */
    private int isPatronInLibrary(Patron patron) {
        for(int i = 0; i < currentPatrons; i++) {
            if(patron == patrons[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks how many books the patron already borrowed from the library.
     * @param patronId The patron to check.
     * @return The amount of books borrowed by the patron.
     */
    private int sumPatronBooks(int patronId) {
        int sum = 0;
        for(int i = 0; i < currentBooks; i++) {
            if(books[i].currentBorrowerId == patronId) {
                sum++;
            }
        }
        return sum;
    }
}