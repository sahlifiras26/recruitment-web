package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.constant.AppConstant;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

public class LibraryImplementation implements Library {
	
	public BookRepository bookRepository;
	
	
	public BookRepository getBookRepository() {
		return bookRepository;
	}

	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
		if (checkBookAvailibility(isbnCode)) {
			boolean status = false;
			if (member instanceof Student) {
				status = checkIfMemberIsLate(member, borrowedAt, AppConstant.STUDENT_PERIOD);
			}
			else if (member instanceof Resident) {
				status = checkIfMemberIsLate(member, borrowedAt, AppConstant.RESIDENT_PERIOD);
			}
			if (!status) {
				Book b = bookRepository.findBook(isbnCode);
				bookRepository.saveBookBorrow(b, borrowedAt);
				member.addBorrowedBook(b);
				return b;
			} else {
				throw new HasLateBooksException();
			}
		}
		return null;
	}

	@Override
	public void returnBook(Book book, Member member) {
		LocalDate borrowedAt = bookRepository.findBorrowedBookDate(book);
		LocalDate now = LocalDate.now();
		long dayDiff = ChronoUnit.DAYS.between(borrowedAt, now);
		if (member.payBook(Math.toIntExact(dayDiff))) {
			bookRepository.saveBookReturn(book);
			member.removeReturnedBook(book);
		}
	}
	//Method of checking if member have at least one book late on the delay.
	public boolean checkIfMemberIsLate(Member member, LocalDate borrowedAt, long maxBorrowDays ) {
		List<Book> memberBooks = member.getBooks();
		boolean status = false;
		if (memberBooks != null) {
			for (Book book : memberBooks) {
				LocalDate borrowedTime = bookRepository.findBorrowedBookDate(book);
				long dayDiff = ChronoUnit.DAYS.between(borrowedTime, borrowedAt);
				if (dayDiff > maxBorrowDays) {
					status = true;
				}
			}
		}
		return status;
	}
	//Method of checking if book is available
	@Override
	public boolean checkBookAvailibility(long isbnCode ) {
		Book book = bookRepository.findBook(isbnCode);
		if (book == null)
			return false;
		else
			return true;
	}
}
