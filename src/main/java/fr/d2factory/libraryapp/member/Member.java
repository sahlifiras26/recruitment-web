package fr.d2factory.libraryapp.member;

import java.util.ArrayList;
import java.util.List;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
	private long id;
	private String fullName;
    /**
     * An initial sum of money the member has
     */
    private float wallet;
    private List<Book> books = new ArrayList<Book>();
    
    



	public Member(long id, String fullName, float wallet) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.wallet = wallet;
	}



	/**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract boolean payBook(int numberOfDays);

    
    
    public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }
    
    public List<Book> getBooks() {
		return books;
	}



	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public void addBorrowedBook(Book book) {
		this.books.add(book);
	}

	public void removeReturnedBook(Book returnedBook) {
		this.books.removeIf(book -> book.equals(returnedBook));
	}
}
