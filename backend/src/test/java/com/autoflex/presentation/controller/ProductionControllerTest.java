package com.autoflex.presentation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPossibleProductionForSeedData() throws Exception {
        mockMvc.perform(get("/api/producao/possivel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto A"))
                .andExpect(jsonPath("$[0].quantidadeProduzivel").value(7))
                .andExpect(jsonPath("$[1].nome").value("Produto B"))
                .andExpect(jsonPath("$[1].quantidadeProduzivel").value(5))
                .andExpect(jsonPath("$[2].nome").value("Produto C"))
                .andExpect(jsonPath("$[2].quantidadeProduzivel").value(1))
                .andExpect(jsonPath("$[3].nome").value("Produto D"))
                .andExpect(jsonPath("$[3].quantidadeProduzivel").value(0));
    }
}
