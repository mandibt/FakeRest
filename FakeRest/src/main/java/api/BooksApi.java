package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Book;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class BooksApi {
    private static final String BASE_URL = ConfigReader.getProperty("base.url");
    private static final String BOOKS_ENDPOINT = "/api/v1/Books";

    /**
     * Get all books
     * @return Response object
     */
    public static Response getAllBooks() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + BOOKS_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    /**
     * Get book by ID
     * @param id Book ID
     * @return Response object
     */
    public static Response getBookById(int id) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + BOOKS_ENDPOINT + "/" + id)
                .then()
                .extract()
                .response();
    }

    /**
     * Create a new book
     * @param book Book object
     * @return Response object
     */
    public static Response createBook(Book book) {
        return given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post(BASE_URL + BOOKS_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    /**
     * Update an existing book
     * @param id Book ID
     * @param book Updated Book object
     * @return Response object
     */
    public static Response updateBook(int id, Book book) {
        return given()
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .put(BASE_URL + BOOKS_ENDPOINT + "/" + id)
                .then()
                .extract()
                .response();
    }

    /**
     * Delete a book
     * @param id Book ID
     * @return Response object
     */
    public static Response deleteBook(int id) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URL + BOOKS_ENDPOINT + "/" + id)
                .then()
                .extract()
                .response();
    }
}