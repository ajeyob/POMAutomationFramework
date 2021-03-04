package com.restassured.tests.singleTests;

import com.restassured.tests.baseTests.FootballAPIEndPoints;
import com.restassured.tests.baseTests.RestAssuredFootballApiConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class restTest1 extends RestAssuredFootballApiConfig {
    @Test
    public void test1(){

        Response response= given().
                            when().get(FootballAPIEndPoints.COMPETITIONS_2001_MATCHES);

        response.prettyPrint();
        int statusCode=response.getStatusCode();
        System.out.println("Status= "+statusCode);

    }

    @Test
    public void getAreas(){

     get(FootballAPIEndPoints.AREAS).prettyPrint();
    }


    @Test
    public void getPlayeDetails(){
        given()
                .pathParam("playerID","44").
        when()
                .get(FootballAPIEndPoints.PLAYER_DETAILS).
        then().log().all();

    }

    @Test
    public void getNCheckPlayeDetails(){
        given()
                .pathParam("playerID","44").
                when()
                .get(FootballAPIEndPoints.PLAYER_DETAILS).
                then().body("name",equalTo("Cristiano Ronaldo"));

    }

    @Test
    public void getPlayerAndMatchDetails(){
        Response response=given()
                .pathParam("playerID","47")
                .queryParams("status","FINISHED").
         when().
                get(FootballAPIEndPoints.PLAYER_MATCH_DETAILS);

        List<Integer> matchIds=new ArrayList<>();
        matchIds=  response.getBody().jsonPath().get("matches.id");
        System.out.println(matchIds.size());
        matchIds.forEach(d->System.out.println(String.valueOf(d)));
    }

    @Test
    public void captureResponseTime(){
        Response response=given()
                .pathParam("playerID","47")
                .queryParams("status","FINISHED").
                        when().
                        get(FootballAPIEndPoints.PLAYER_MATCH_DETAILS);

        long responsetime=response.time();
        System.out.println("Response Time="+responsetime);

        //Assert on the response time
        response.then().time(lessThan(10000l));

    }

}
