package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.services.RoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoadmapController implements RoadmapsApi {

    private RoadmapService roadmapService;
    @Autowired
    public RoadmapController(RoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    @Override
    public ResponseEntity<CreateRoadmapResponse> createRoadmap(CreateRoadmapDetail createRoadmapDetail) {
        return ResponseEntity.ok(roadmapService.createRoadmap(createRoadmapDetail));
    }

    @Override
    public ResponseEntity<List<CreateRoadmapResponse>> getRoadMap() {
        return ResponseEntity.ok(roadmapService.listRoadmap());
    }
}
