package com.example.widget.service;

import com.example.widget.domain.GadgetEntity;
import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.CreateGadgetRequest;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.repository.GadgetRepository;
import com.example.widget.repository.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GadgetServiceImpl implements GadgetService {
    private GadgetRepository gadgetRepository;

    @Autowired
    private WidgetRepository widgetRepository;

    public GadgetServiceImpl(GadgetRepository gadgetRepository, WidgetRepository widgetRepository) {
        this.gadgetRepository = gadgetRepository;
        this.widgetRepository = widgetRepository;
    }

    @Override
    public void createGadget(CreateGadgetRequest createGadgetRequest) {
        this.gadgetRepository.save(new GadgetEntity(createGadgetRequest.getName(), getRandomWidgetID()));
    }

    /**
     * Helper function that finds a random widget for new gadget
     * If there are no widgets at all, then widget id is just randomly generated number
     * @return Long
     */
    private Long getRandomWidgetID(){
        List<WidgetEntity> widgetEntities = widgetRepository.findAll();
        Random rand = new Random();
        Long randomWidgetId;
        if(widgetEntities.size() != 0) {
            randomWidgetId = widgetEntities.get(rand.nextInt(widgetEntities.size())).getId();
        }else{
            randomWidgetId = rand.nextLong();
        }
        return randomWidgetId;
    }

    @Override
    public List<GadgetResponse> findAll() {
        List<GadgetResponse> response = new ArrayList<>();


        List<GadgetEntity> entities = this.gadgetRepository.findAll();

        for (GadgetEntity entity : entities) {
            response.add(new GadgetResponse(entity.getName(), entity.getWidgetId()));
        }

        return response;
    }
}
