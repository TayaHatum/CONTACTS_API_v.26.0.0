package com.telran.apachetest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.telran.dto.AuthRequestDto;
import com.telran.dto.AuthResponseDto;
import com.telran.dto.ErrorDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApacheTest {

    @Test
    public void testLoginWithoutDtoObject() throws IOException {
        String email = "john9090@mail.com";
        String password = "Aa12345~";

        Response response = Request.Post("https://contacts-telran.herokuapp.com/api/login")
                .bodyString("{\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"password\": \"" + password + "\"\n" +
                        "}", ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response);


        String responseJson = response.returnContent()
                .asString();

        System.out.println(responseJson);
        System.out.println("***********");

        JsonElement element = JsonParser.parseString(responseJson);

        JsonElement token = element.getAsJsonObject().get("token");
        System.out.println(token.getAsString());

    }

    @Test
    public void testLogin() throws IOException {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("john9090@mail.com")
                .password("Aa12345~").build();
        Gson gson = new Gson();
        Response response = Request.Post("https://contacts-telran.herokuapp.com/api/login")
                .bodyString(gson.toJson(requestDto), ContentType.APPLICATION_JSON)
                .execute();
        String responseJson = response.returnContent().asString();
        AuthResponseDto responseDto = gson.fromJson(responseJson, AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }

    @Test
    public void testLogin1() throws IOException {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("john9090@mail.com")
                .password("a12345~").build();
        Gson gson = new Gson();
        Response response = Request.Post("https://contacts-telran.herokuapp.com/api/login")
                .bodyString(gson.toJson(requestDto), ContentType.APPLICATION_JSON)
                .execute();
        HttpResponse httpresponse = response.returnResponse();
        System.out.println(httpresponse);
        System.out.println(httpresponse.getStatusLine().getStatusCode());

        InputStream is = httpresponse.getEntity().getContent();
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line= br.readLine())!=null){
            sb.append(line);
        }
        System.out.println(sb.toString());
        ErrorDto errorDto = gson.fromJson(sb.toString(),ErrorDto.class);
        System.out.println(errorDto.getCode());
        System.out.println(errorDto.getMessage());
    }
}
//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImpvaG45MDkwQG1haWwuY29tIn0.uFp-ZueoxtelSBWIrrsl7SmMAKUNBmjQNj33Zh2ik6w