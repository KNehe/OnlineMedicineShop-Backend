package nehe.demo;

import com.google.gson.Gson;
import nehe.demo.Modals.JwtRequest;
import nehe.demo.Modals.User;
import nehe.demo.Services.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OnlineMedicineShopRestApiApplicationTests {

    Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User user = new User("John","Moses","kk@gmail.com","dsdsd","0754504545",12,"User");


    @Before
    HttpHeaders getAuthToken() throws Exception {
        JwtRequest jwtRequest =  new JwtRequest();

        MvcResult mvcResult =  mockMvc.perform( post("/authenticate")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(gson.toJson(jwtRequest) ))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        Token token = gson.fromJson(result,Token.class);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization","Bearer "+token.getToken());

       return  httpHeaders;
    }

    @Test
    void shouldRegisterUserTest() throws Exception {

        when(userService.checkIfEmailExists("kd@gmail.com")).thenReturn(true);

        MvcResult mvcResult =  mockMvc.perform( post("/register")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(user)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNot(null);
    }
}


class Token{
    String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
