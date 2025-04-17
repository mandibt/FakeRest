package tests;

import api.BooksApi;
import io.qameta.allure.*;
import io.restassured.response.Response;
import models.Book;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReportManager;
import utils.TestBase;

import java.time.LocalDateTime;
import java.util.List;

@Epic("REST API Testing")
@Feature("Books API")
public class BooksAPITests extends TestBase {

    @Test(priority = 1)
    @Story("Get All Books")
    @Description("Test to verify getting all books")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllBooks() {
        Response response = BooksApi.getAllBooks();
        ReportManager.logResponse(response);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        List<Book> books = response.jsonPath().getList("", Book.class);
        Assert.assertFalse(books.isEmpty(), "Books list should not be empty");
    }

    @Test(priority = 2)
    @Story("Get Book by ID")
    @Description("Test to verify getting a book by its ID")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetBookById() {
        // First get a book from the list
        Response allBooksResponse = BooksApi.getAllBooks();
        int bookId = allBooksResponse.jsonPath().getInt("[0].id");

        Response response = BooksApi.getBookById(bookId);
        ReportManager.logResponse(response);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Book book = response.as(Book.class);
        Assert.assertEquals(book.getId(), bookId);
    }

    @Test(priority = 3)
    @Story("Create Book")
    @Description("Test to verify creating a new book")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateBook() {
        Book newBook = Book.builder()
                .id(0) // Will be assigned by the API
                .title("Test Book " + generateRandomString())
                .description("Description for test book")
                .pageCount(100)
                .excerpt("Excerpt from test book")
                .publishDate(LocalDateTime.now().toString())
                .build();

        Response response = BooksApi.createBook(newBook);
        ReportManager.logResponse(response);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Book createdBook = response.as(Book.class);
        Assert.assertNotEquals(createdBook.getId(), 0, "Book ID should be assigned");
        Assert.assertEquals(createdBook.getTitle(), newBook.getTitle());
    }

    @Test(priority = 4)
    @Story("Update Book")
    @Description("Test to verify updating an existing book")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateBook() {
        // First create a book
        Book newBook = Book.builder()
                .id(0)
                .title("Book to update")
                .description("Original description")
                .pageCount(100)
                .excerpt("Original excerpt")
                .publishDate(LocalDateTime.now().toString())
                .build();

        Response createResponse = BooksApi.createBook(newBook);
        Book createdBook = createResponse.as(Book.class);

        // Update the book
        createdBook.setTitle("Updated Book Title");
        createdBook.setDescription("Updated description");

        Response updateResponse = BooksApi.updateBook(createdBook.getId(), createdBook);
        ReportManager.logResponse(updateResponse);

        Assert.assertEquals(updateResponse.getStatusCode(), HttpStatus.SC_OK);
        Book updatedBook = updateResponse.as(Book.class);
        Assert.assertEquals(updatedBook.getTitle(), "Updated Book Title");
        Assert.assertEquals(updatedBook.getDescription(), "Updated description");
    }

    @Test(priority = 5)
    @Story("Delete Book")
    @Description("Test to verify deleting a book")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteBook() {
        // First create a book to delete
        Book newBook = Book.builder()
                .id(0)
                .title("Book to delete")
                .description("This book will be deleted")
                .pageCount(100)
                .excerpt("Excerpt")
                .publishDate(LocalDateTime.now().toString())
                .build();

        Response createResponse = BooksApi.createBook(newBook);
        Book createdBook = createResponse.as(Book.class);

        // Delete the book
        Response deleteResponse = BooksApi.deleteBook(createdBook.getId());
        ReportManager.logResponse(deleteResponse);

        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_OK);

        // Verify book is deleted
        Response getResponse = BooksApi.getBookById(createdBook.getId());
        Assert.assertEquals(getResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(priority = 6)
    @Story("Get Book with Invalid ID")
    @Description("Test to verify behavior when requesting a book with invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBookWithInvalidId() {
        Response response = BooksApi.getBookById(-1);
        ReportManager.logResponse(response);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
    
    @Test(priority = 7)
    @Story("POST Create Book with Empty Title")
    @Description("Test to verify API behavior when creating a book with an empty title")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateBookWithInvalidDate() {
        
        Book invalidBook = Book.builder()
                .id(0) // Will be assigned by the API
                .title("Invalid Date Format")
                .description("Description for test book")
                .pageCount(10)
                .excerpt("Excerpt from test book")
                .publishDate("this-is-not-a-date")
                .build();

        Response response = BooksApi.createBook(invalidBook);
        ReportManager.logResponse(response);
        
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }
}