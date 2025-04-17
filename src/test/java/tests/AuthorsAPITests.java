package tests;

import api.AuthorsApi;
import io.qameta.allure.*;
import io.restassured.response.Response;
import models.Author;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReportManager;
import utils.TestBase;

import java.util.List;

@Epic("REST API Testing")
@Feature("Authors API")
public class AuthorsAPITests extends TestBase {

    @Test(priority = 1)
    @Story("Get All Authors")
    @Description("Test to verify getting all authors")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllAuthors() {
        Response response = AuthorsApi.getAllAuthors();
        ReportManager.logResponse(response);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        List<Author> authors = response.jsonPath().getList("", Author.class);
        Assert.assertFalse(authors.isEmpty(), "Authors list should not be empty");
    }

    @Test(priority = 2)
    @Story("Get Author by ID")
    @Description("Test to verify getting an author by their ID")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAuthorById() {
        // First get an author from the list
        Response allAuthorsResponse = AuthorsApi.getAllAuthors();
        int authorId = allAuthorsResponse.jsonPath().getInt("[0].id");

        Response response = AuthorsApi.getAuthorById(authorId);
        ReportManager.logResponse(response);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Author author = response.as(Author.class);
        Assert.assertEquals(author.getId(), authorId);
    }

    @Test(priority = 3)
    @Story("Create Author")
    @Description("Test to verify creating a new author")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateAuthor() {
        Author newAuthor = Author.builder()
                .id(0) // Will be assigned by the API
                .idBook("1")
                .firstName("Test " + generateRandomString())
                .lastName("Author " + generateRandomString())
                .build();

        Response response = AuthorsApi.createAuthor(newAuthor);
        ReportManager.logResponse(response);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        Author createdAuthor = response.as(Author.class);
        Assert.assertNotEquals(createdAuthor.getId(), 0, "Author ID should be assigned");
        Assert.assertEquals(createdAuthor.getFirstName(), newAuthor.getFirstName());
        Assert.assertEquals(createdAuthor.getLastName(), newAuthor.getLastName());
    }

    @Test(priority = 4)
    @Story("Update Author")
    @Description("Test to verify updating an existing author")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateAuthor() {
        // First create an author
        Author newAuthor = Author.builder()
                .id(0)
                .idBook("1")
                .firstName("Original First")
                .lastName("Original Last")
                .build();

        Response createResponse = AuthorsApi.createAuthor(newAuthor);
        Author createdAuthor = createResponse.as(Author.class);

        // Update the author
        createdAuthor.setFirstName("Updated First");
        createdAuthor.setLastName("Updated Last");

        Response updateResponse = AuthorsApi.updateAuthor(createdAuthor.getId(), createdAuthor);
        ReportManager.logResponse(updateResponse);

        Assert.assertEquals(updateResponse.getStatusCode(), HttpStatus.SC_OK);
        Author updatedAuthor = updateResponse.as(Author.class);
        Assert.assertEquals(updatedAuthor.getFirstName(), "Updated First");
        Assert.assertEquals(updatedAuthor.getLastName(), "Updated Last");
    }

    @Test(priority = 5)
    @Story("Delete Author")
    @Description("Test to verify deleting an author")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteAuthor() {
        // First create an author to delete
        Author newAuthor = Author.builder()
                .id(0)
                .idBook("1")
                .firstName("Delete First")
                .lastName("Delete Last")
                .build();

        Response createResponse = AuthorsApi.createAuthor(newAuthor);
        Author createdAuthor = createResponse.as(Author.class);

        // Delete the author
        Response deleteResponse = AuthorsApi.deleteAuthor(createdAuthor.getId());
        ReportManager.logResponse(deleteResponse);

        Assert.assertEquals(deleteResponse.getStatusCode(), HttpStatus.SC_OK);

        // Verify author is deleted
        Response getResponse = AuthorsApi.getAuthorById(createdAuthor.getId());
        Assert.assertEquals(getResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(priority = 6)
    @Story("Get Author with Invalid ID")
    @Description("Test to verify behavior when requesting an author with invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAuthorWithInvalidId() {
        Response response = AuthorsApi.getAuthorById(-1);
        ReportManager.logResponse(response);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
}