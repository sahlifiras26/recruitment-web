package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryTest {
    private Library library ;
    private BookRepository bookRepository;
    Member s1, s2, s3;
    List<Book> books = new ArrayList<Book>();
    @BeforeEach
    public void setup(){
        //TODO instantiate the library and the repository
    	this.bookRepository = new BookRepository();
        //DONE - add some test books (use BookRepository#addBooks) from books.json
    	JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/test/resources/books.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray bookList = (JSONArray) obj;
            bookList.forEach(book -> {
            	parseBookObject( (JSONObject) book);
            });
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookRepository.addBooks(books);
        this.library = new LibraryImplementation();
        ((LibraryImplementation)this.library).setBookRepository(this.bookRepository);

        s1 = new Student(1, "firas sahli", 100.0f, true);
        s2 = new Resident(2, "firas", 10.0f);
        s3 = new Student(3, "sahli", 5.0f, false);
    }
    
    private void parseBookObject(JSONObject book)
    {
        String title = (String) book.get("title");
        String author = (String) book.get("author");
        JSONObject isbn = (JSONObject) book.get("isbn");
        Object isbnCode = isbn.get("isbnCode");
        books.add(new Book(title, author, new ISBN((long)isbnCode)));
    }
    @Test
    public void member_can_borrow_a_book_if_book_is_available(){
    	assertNotNull(library.borrowBook(3326456467846L, s1, LocalDate.now()));
    }

    @Test
    public void borrowed_book_is_no_longer_available(){
    	library.borrowBook(3326456467846L, s1, LocalDate.now());
    	assertNull(bookRepository.findBook(3326456467846L));
    }

    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
    	Book b = bookRepository.findBook(3326456467846L);
    	library.borrowBook(3326456467846L, s2, LocalDate.from(LocalDate.now()).minusDays(10));
    	this.library.returnBook(b, s2);
    	assertEquals(9.0f, s2.getWallet());
    }

    @Test
    public void students_pay_10_cents_the_first_30days(){
    	Book b = bookRepository.findBook(3326456467846L);
    	library.borrowBook(3326456467846L, s3, LocalDate.from(LocalDate.now()).minusDays(10));
    	this.library.returnBook(b, s3);
    	assertEquals(4.0f, s3.getWallet());
    }

    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days(){
    	Book b = bookRepository.findBook(3326456467846L);
    	library.borrowBook(3326456467846L, s1, LocalDate.from(LocalDate.now()).minusDays(10));
    	this.library.returnBook(b, s1);
    	assertEquals(100.0f, s1.getWallet());
    }

    @Test
    public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days(){
    	Book b = bookRepository.findBook(3326456467846L);
    	library.borrowBook(3326456467846L, s3, LocalDate.from(LocalDate.now()).minusDays(40));
    	this.library.returnBook(b, s3);
    	assertEquals(0.5f, s3.getWallet());
    }

    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
    	Book b = bookRepository.findBook(3326456467846L);
    	library.borrowBook(3326456467846L, s2, LocalDate.from(LocalDate.now()).minusDays(70));
    	this.library.returnBook(b, s2);
    	assertEquals(2.0f, s2.getWallet());
    }

    @Test
    public void members_cannot_borrow_book_if_they_have_late_books(){
    	library.borrowBook(3326456467846L, s3, LocalDate.from(LocalDate.now()).minusDays(40));
    	assertNull(library.borrowBook(46578964513L, s3, LocalDate.now()));
    }
    @Test
    public void test_number_of_borrowed_books(){
    	Book b = bookRepository.findBook(3326456467846L);
    	library.borrowBook(46578964513L, s1, LocalDate.now());
    	library.borrowBook(3326456467846L, s1, LocalDate.now());
    	library.borrowBook(968787565445L, s1, LocalDate.now());
    	library.borrowBook(465789453149L, s1, LocalDate.now());
    	assertEquals(4, s1.getBooks().size());
    }
    @Test
    public void test_number_of_borrowed_books_after_retrun(){
    	Book b = bookRepository.findBook(3326456467846L);
    	library.borrowBook(46578964513L, s1, LocalDate.now());
    	library.borrowBook(3326456467846L, s1, LocalDate.now());
    	library.borrowBook(968787565445L, s1, LocalDate.now());
    	library.borrowBook(465789453149L, s1, LocalDate.now());
    	this.library.returnBook(b, s1);
    	assertEquals(3, s1.getBooks().size());
    }
}
