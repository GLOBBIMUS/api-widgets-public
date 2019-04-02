package com.example.widget.service;

import com.example.widget.domain.GadgetEntity;
import com.example.widget.domain.WidgetEntity;
import com.example.widget.dto.CreateWidgetRequest;
import com.example.widget.dto.GadgetResponse;
import com.example.widget.dto.WidgetResponse;
import com.example.widget.repository.GadgetRepository;
import com.example.widget.repository.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class WidgetServiceImpl implements WidgetService {
    private WidgetRepository widgetRepository;

    @Autowired
    private GadgetRepository gadgetRepository;

    public WidgetServiceImpl(WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    @Override
    public void createWidget(CreateWidgetRequest createWidgetRequest) {
        this.widgetRepository.save(new WidgetEntity(createWidgetRequest.getName()));
    }

    @Override
    public List<WidgetResponse> findAll(Pageable pageable) {
        List<WidgetResponse> response = new ArrayList<>();

        Page<WidgetEntity> widgetEntities = this.widgetRepository.findAll(pageable);

        List<GadgetEntity> gadgetEntities = this.gadgetRepository.findAll();

        for (WidgetEntity widgetEntity : widgetEntities) {
            List<GadgetResponse> associatedGadgets = new ArrayList<>();

            gadgetEntities.stream()
                    .filter(gadgetEntity -> gadgetEntity.getWidgetId().equals(widgetEntity.getId()))
                    .forEach(gadgetEntity ->{
                        associatedGadgets.add(new GadgetResponse(gadgetEntity.getName(), gadgetEntity.getWidgetId()));
                    });

            response.add(new WidgetResponse(widgetEntity.getName(), associatedGadgets));
        }

        return response;
    }
}
