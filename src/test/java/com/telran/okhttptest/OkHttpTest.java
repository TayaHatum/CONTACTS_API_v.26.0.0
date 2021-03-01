package com.telran.okhttptest;

import com.google.gson.Gson;
import com.telran.dto.AuthRequestDto;
import com.telran.dto.AuthResponseDto;
import com.telran.dto.ErrorDto;
import okhttp3.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpTest {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");


@Test
    public void testLogin() throws IOException {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    AuthRequestDto requestDto = AuthRequestDto.builder()
            .email("john9090@mail.com")
            .password("a12345~").build();

    RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);
    Request request = new Request.Builder()
            .url("https://contacts-telran.herokuapp.com/api/login")
            .post(requestBody)
            .build();

    Response response = client.newCall(request).execute();
    String  responseJson =response.body().string();
    if(response.isSuccessful()){

        AuthResponseDto responseDto = gson.fromJson(responseJson,AuthResponseDto.class);
        System.out.println(responseDto.getToken() );

    }else {
        ErrorDto errorDto =gson.fromJson(responseJson,ErrorDto.class);
        System.out.println(errorDto.getCode());
        System.out.println(errorDto.getMessage());
    }
}
}
