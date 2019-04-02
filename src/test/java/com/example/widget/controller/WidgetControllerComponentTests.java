package com.example.widget.controller;

import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.repository.WidgetRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WidgetControllerComponentTests {
    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void FindAll() throws Exception {
        WidgetEntity widget = this.widgetRepository.save(new WidgetEntity("Cool fake widget"));

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/widgets"))
                .andExpect(status().isOk()).andReturn().getResponse();

        List<WidgetResponse> widgetResponseList = mapper.readValue(response.getContentAsString(), new TypeReference<List<WidgetResponse>>() {
        });

        assertThat(widgetResponseList.size(), equalTo(1));
        assertThat(widgetResponseList.get(0).getName(), equalTo(widget.getName()));
    }

    @Test
    void FindFirstPage() throws Exception {
        WidgetEntity widget1 = this.widgetRepository.save(new WidgetEntity("Cool fake widget1"));
        WidgetEntity widget2 = this.widgetRepository.save(new WidgetEntity("Cool fake widget2"));
        WidgetEntity widget3 = this.widgetRepository.save(new WidgetEntity("Cool fake widget3"));
        WidgetEntity widget4 = this.widgetRepository.save(new WidgetEntity("Cool fake widget4"));


        //Here I will test the pagination
        //This mock test will require the first page, size of the page is 2. And it will sort it by field "name"
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/widgets?page=0&size=2&sort=name"))
                .andExpect(status().isOk()).andReturn().getResponse();

        List<WidgetResponse> widgetResponseList = mapper.readValue(response.getContentAsString(), new TypeReference<List<WidgetResponse>>() {
        });

        assertThat(widgetResponseList.size(), equalTo(2));
        assertThat(widgetResponseList.get(0).getName(), equalTo(widget1.getName()));
        assertThat(widgetResponseList.get(1).getName(), equalTo(widget2.getName()));
    }
}
