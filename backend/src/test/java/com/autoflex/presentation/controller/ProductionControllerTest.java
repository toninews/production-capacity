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
                .andExpect(jsonPath("$[0].productName").value("Produto A"))
                .andExpect(jsonPath("$[0].producibleQuantity").value(7))
                .andExpect(jsonPath("$[1].productName").value("Produto B"))
                .andExpect(jsonPath("$[1].producibleQuantity").value(5))
                .andExpect(jsonPath("$[2].productName").value("Produto C"))
                .andExpect(jsonPath("$[2].producibleQuantity").value(1))
                .andExpect(jsonPath("$[3].productName").value("Produto D"))
                .andExpect(jsonPath("$[3].producibleQuantity").value(0));
    }
}
