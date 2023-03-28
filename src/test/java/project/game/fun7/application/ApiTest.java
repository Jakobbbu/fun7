package project.game.fun7.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenNoTokenThenForbidden() throws Exception {
        mockMvc.perform(get("/administration/user")
                        .param("id", "3"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenTokenAdminAdminController() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");

        mockMvc.perform(get("/administration/user/get-all")
                .header("Authorization", "Bearer " + accessToken)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

    }

    @Test
    public void givenTokenAdminServiceController() throws Exception {
        String accessToken = obtainAccessToken("admin", "admin");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("timeZone", "Africa/Tunis");
        params.add("id", "3");
        params.add("cc", "US");

        mockMvc.perform(get("/services/get-enabled")
                        .header("Authorization", "Bearer " + accessToken)
                        .params(params)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

    }

    @Test
    public void givenTokenUserAdminController() throws Exception {
        String accessToken = obtainAccessToken("gamer007", "1234");

        mockMvc.perform(get("/administration/user/get-all")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isForbidden());

    }

    @Test
    public void givenTokenUserServiceController() throws Exception {
        String accessToken = obtainAccessToken("gamer007", "1234");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("timeZone", "Africa/Tunis");
        params.add("id", "3");
        params.add("cc", "US");

        mockMvc.perform(get("/services/get-enabled")
                        .header("Authorization", "Bearer " + accessToken)
                        .params(params)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

    }

    @Test
    public void obtainAccessToken() throws Exception {
        mockMvc.perform(post("/login")
                        .content("{\n" +
                                "    \"username\": \"admin\",\n" +
                                "    \"password\": \"admin\"\n" +
                                "}")
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void obtainTokenTest() throws Exception {
        Assertions.assertNotNull(obtainAccessToken("admin", "admin"));
    }

    private String obtainAccessToken(String username, String password) throws Exception {

        String json = "{\n" +
                "    \"username\":\"" + username + "\",\n" +
                "    \"password\":\"" + password + "\"\n" +
                "}";
        ResultActions result
                = mockMvc.perform(post("/login")
                        .content(json)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());

        return result.andReturn().getResponse().getHeader("Authorization");
    }
}
