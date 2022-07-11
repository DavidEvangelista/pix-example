package br.com.itau.pixexample.web;

import br.com.itau.pixexample.entity.dto.ChavePixDTO;
import br.com.itau.pixexample.repository.ChavePixRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PixControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ChavePixRepository repository;

    private MockMvc mvc;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldCreateChavePixCelularSuccess() throws Exception {
        var request = mapperJsonFileToObject("classpath:request/create-succes-chavepix-celular.json", ChavePixDTO.class);

        mvc.perform(MockMvcRequestBuilders.post("/api-pix/v1/chaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writeJsonToString(request)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldCreateChavePixCelularError() throws Exception {
        var request = mapperJsonFileToObject("classpath:request/create-succes-chavepix-celular-invalid.json", ChavePixDTO.class);

        mvc.perform(MockMvcRequestBuilders.post("/api-pix/v1/chaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writeJsonToString(request)))
                .andExpect(status().isUnprocessableEntity());
    }

    private Object mapperJsonFileToObject(String pathFile, Class<?> clazz) {
        try {
            return new ObjectMapper().readValue(ResourceUtils.getFile(pathFile), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String writeJsonToString(Object json) {
        try {
            return new ObjectMapper().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
