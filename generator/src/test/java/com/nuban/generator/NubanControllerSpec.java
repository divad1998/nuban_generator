package com.nuban.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
public class NubanControllerSpec {

    //Algo:
    //send body to endpoint
    //return 201 and nuban

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NubanService service;

    @DisplayName("Accepts body and returns generated NUBAN")
    @Test
    void generateNuban() throws Exception {
        var detailsDto = new DetailsDto();
        detailsDto.setInstitutionCode("033");
        detailsDto.setSerialNumber("123456789");

        var jsonRequest = new ObjectMapper().writeValueAsString(detailsDto);

        //stub NubanService generate()
        when(service.generate(any())).thenReturn("1234567897");

        mockMvc.perform(post("http://localhost:8080/api/nuban")
                                        .content(jsonRequest)
                                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("1234567897"));
    }

    //
    @DisplayName("Serial number has to be 9 digits and Institution code has to be 3 digits")
    @Test
    void generateWithInvalidDetails() throws Exception {
        var detailsDto = new DetailsDto();
        detailsDto.setInstitutionCode("0");
        detailsDto.setSerialNumber("1234");

        var jsonRequest = new ObjectMapper().writeValueAsString(detailsDto);

        mockMvc.perform(post("http://localhost:8080/api/nuban")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}