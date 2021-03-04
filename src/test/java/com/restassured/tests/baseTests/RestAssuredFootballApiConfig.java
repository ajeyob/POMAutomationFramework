package com.restassured.tests.baseTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;

public class RestAssuredFootballApiConfig {

    public static RequestSpecification footballApi_reqSpec;
    public static ResponseSpecification footballApi_responseSpec;
    @BeforeTest
    public void restAssured_Setup(){
        RestAssured.baseURI= "https://api.football-data.org/";
        RestAssured.basePath="v2/";
        footballApi_reqSpec= new RequestSpecBuilder()
                .addHeader("X-Auth-Token","d0c851bec1d6424892aeef2261b5be10").build();
        RestAssured.requestSpecification=footballApi_reqSpec;

        ResponseSpecification footballApi_responseSpec= new ResponseSpecBuilder()
                .expectStatusCode(200).build();

        RestAssured.responseSpecification=footballApi_responseSpec;
    }


}
