package com.blueMorphoST.test.cucumber.dice.stepdefinitions;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;
import com.blueMorphoST.test.cucumber.dice.helper.PropertyReader;

import static com.jayway.restassured.RestAssured.given;


public class testDice {
    private List<String> diceList = new ArrayList<>();
    private Response response;
    private PropertyReader propertyReader = new PropertyReader();
    private String serverName = propertyReader.getPropertyValue("dice.server.name");
    private String serverPort = propertyReader.getPropertyValue("dice.server.port");
    private String servicePath = propertyReader.getPropertyValue("dice.service.path");

    @Given("^I have a dice cast at (\\d+)$")
       public void i_have_a_dice_cast_at(String diceToAddToList) {
        diceList.add(diceToAddToList);
        System.out.println("added dice " + diceToAddToList + " to list");
        System.out.println("list now contains: " + String.join(",", diceList));
    }

    @When("^I query the oracle$")
    public void i_query_the_oracle() throws Throwable {
        RestAssured.baseURI = serverName + ":" + serverPort + servicePath;
        response = given().contentType("application/json").
                queryParam("dice", String.join(",",   diceList )).when().post("") ;
        Assert.assertEquals("A different response code was returned while" +
                " querying the oracle ",200 ,response.statusCode());
        System.out.println(response.getBody().asString());
   }

    @Then("^I expect the oracle to return (\\d+)$")
    public void i_expected_the_oracle_to_return(int expectedOracleResponse) throws Throwable {
        Assert.assertEquals(200, response.getStatusCode());
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = JsonPath.from(responseBody);
        int responseValue = jsonPath.get("theOracleSays");
        Assert.assertEquals( "The response number received " +
                "did not match the number expected",expectedOracleResponse, responseValue);
   }
}
