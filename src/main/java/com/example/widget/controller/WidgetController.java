package com.example.widget.controller;

import com.example.widget.dto.CreateWidgetRequest;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.service.WidgetService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = {"api/v1/widgets"})
public class WidgetController {
    private WidgetService widgetService;

    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createWidget(@Valid @RequestBody CreateWidgetRequest createWidgetRequest) {
        this.widgetService.createWidget(createWidgetRequest);
    }

    //In order to get paged response, you will need to provide page number and page size
    //Example: http://localhost:8080/api/v1/widgets?page=0&size=4&sort=name
    //It will return 1st page with 4 elements in it sorted by "name"
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WidgetResponse> getWidgets(Pageable pageable) {
        return this.widgetService.findAll(pageable);
    }
}
