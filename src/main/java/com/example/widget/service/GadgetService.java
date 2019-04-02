package com.example.widget.service;

import com.example.widget.dto.CreateGadgetRequest;
import com.example.widget.dto.GadgetResponse;

import java.util.List;

public interface GadgetService {
    void createGadget(CreateGadgetRequest createGadgetRequest);

    List<GadgetResponse> findAll();

}
