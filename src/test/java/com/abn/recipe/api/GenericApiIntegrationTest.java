package com.abn.recipe.api;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import com.abn.recipe.RecipeApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@SpringBootTest(
    classes = { RecipeApplication.class, },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class GenericApiIntegrationTest {
    
    static Gson GSON = new Gson();
    
    protected <T> T extractEntity(final MvcResult result, final Class<T> responseClass)
        throws UnsupportedEncodingException {
        return GSON.fromJson(result.getResponse().getContentAsString(StandardCharsets.UTF_8), responseClass);
    }
    
    public String asJsonString(final Object object) {
        return new Gson().toJson(object);
    }
}
