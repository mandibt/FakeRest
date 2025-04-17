package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Author;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class AuthorsApi {
    private static final String BASE_URL = ConfigReader.getProperty("base.url");
    private static final String AUTHORS_ENDPOINT = "/api/v1/Authors";

    /**
     * Get all authors
     * @return Response object
     */
    public static Response getAllAuthors() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + AUTHORS_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    /**
     * Get author by ID
     * @param id Author ID
     * @return Response object
     */
    public static Response getAuthorById(int id) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + AUTHORS_ENDPOINT + "/" + id)
                .then()
                .extract()
                .response();
    }

    /**
     * Create a new author
     * @param author Author object
     * @return Response object
     */
    public static Response createAuthor(Author author) {
        return given()
                .contentType(ContentType.JSON)
                .body(author)
                .when()
                .post(BASE_URL + AUTHORS_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    /**
     * Update an existing author
     * @param id Author ID
     * @param author Updated Author object
     * @return Response object
     */
    public static Response updateAuthor(int id, Author author) {
        return given()
                .contentType(ContentType.JSON)
                .body(author)
                .when()
                .put(BASE_URL + AUTHORS_ENDPOINT + "/" + id)
                .then()
                .extract()
                .response();
    }

    /**
     * Delete an author
     * @param id Author ID
     * @return Response object
     */
    public static Response deleteAuthor(int id) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URL + AUTHORS_ENDPOINT + "/" + id)
                .then()
                .extract()
                .response();
    }
}