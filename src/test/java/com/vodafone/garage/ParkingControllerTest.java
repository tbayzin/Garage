package com.vodafone.garage;

import com.vodafone.garage.model.Vehicle;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

    @RunWith(SpringRunner.class)
    @SpringBootTest
    public class ParkingControllerTest {

        @Autowired
        private WebApplicationContext wac;

        private MockMvc mockMvc;

        @Before
        public void setup() {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }

        @Test
        public void parkTest() throws Exception {
            Vehicle vehicle = new Vehicle();
            vehicle.setPlateNumber("06DF205");
            vehicle.setColor("Red");
            vehicle.setType("Car");

            MvcResult mvcResult = mockMvc.perform(post("/park").contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(vehicle))).andReturn();

            Assert.assertEquals(200, mvcResult.getResponse().getStatus());
            Assert.assertEquals("Vehicle parked successfully", mvcResult.getResponse().getContentAsString());
        }
    }

