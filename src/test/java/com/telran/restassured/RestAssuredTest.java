package com.telran.restassured;

import com.jayway.restassured.RestAssured;
import com.telran.dto.AuthRequestDto;
import com.telran.dto.AuthResponseDto;
import com.telran.dto.ContactDto;
import com.telran.dto.GetAllContactsResponseDto;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.RestAssured.given;

public class RestAssuredTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginTest() {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("john9090@mail.com")
                .password("Aa12345~")
                .build();
        AuthResponseDto responseDto = RestAssured.given()
                .contentType("application/json")
                .body(requestDto)
                .post("https://contacts-telran.herokuapp.com/api/login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);

        System.out.println(responseDto.getToken());

String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImpvaG45MDkwQG1haWwuY29tIn0.uFp-ZueoxtelSBWIrrsl7SmMAKUNBmjQNj33Zh2ik6w";
         String token2 =given()
                .contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .body("token",equalTo(token))
                .extract().path("token");
        System.out.println(token2);

    }
    @Test
    public void  loginNegativeTest(){
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("john9090@mail.com")
                .password("Ca12345~")
                .build();

        String message= given()
                .contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(401)
                .extract().path("message");
        System.out.println( message);
    }

    @Test
    public void addContact(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImpvaG45MDkwQG1haWwuY29tIn0.uFp-ZueoxtelSBWIrrsl7SmMAKUNBmjQNj33Zh2ik6w";
        ContactDto contactDto =
                ContactDto.builder()
                        .address("Haifa")
                        .description("Haifa")
                        .email("Moran1@gmail.com")
                        .lastName("Dow1")
                        .name("Moran1")
                        .phone("9917324568").build();
        int id =given().header("Authorization",token)
                .contentType("application/json")
                .body(contactDto)
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println(id);
    }
//7784

    @Test
    public void deleteContact(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImpvaG45MDkwQG1haWwuY29tIn0.uFp-ZueoxtelSBWIrrsl7SmMAKUNBmjQNj33Zh2ik6w";
        String status=
                given().header("Authorization",token)
                .delete("contact/7792")
                .then()
                .assertThat().statusCode(200)
                .extract().path("status");

        System.out.println(status);
    }

    @Test
    public void getAllContacts(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImpvaG45MDkwQG1haWwuY29tIn0.uFp-ZueoxtelSBWIrrsl7SmMAKUNBmjQNj33Zh2ik6w";
        GetAllContactsResponseDto responseDto=
                given()
                .header("Authorization",token)
                .get("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(GetAllContactsResponseDto.class);

        for(ContactDto contact:responseDto.getContacts()){
            System.out.println( "======" + contact.getId()+"========");
            System.out.println(contact);
        }

    }
    @Test
    public void vadimTest(){
        RestAssured.when().get("https://superhero.qa-test.csssr.com/superheroes/2")
                .then().assertThat().statusCode(200);
    }
}
