package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.services.RoadmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoadmapController implements RoadmapsApi {

    private final RoadmapService roadmapService;
    @Override
    public ResponseEntity<CreateRoadmapResponse> createRoadmap(CreateRoadmapDetail createRoadmapDetail) {
        return ResponseEntity.ok(roadmapService.createRoadmap(createRoadmapDetail));
    }

    @Override
    public ResponseEntity<List<CreateRoadmapResponse>> getRoadMaps() {
        return ResponseEntity.ok(roadmapService.listRoadmap());
    }

    @Override
    public ResponseEntity<CreateRoadmapResponse> getRoadMap(String roadmapId) {
        return ResponseEntity.ok(roadmapService.getRoadMap(roadmapId));
    }

    @Override
    public ResponseEntity<CreateRoadmapResponse> createSharableLink(String roadmapId) {
        return ResponseEntity.ok(roadmapService.createSharableLink(roadmapId));
    }
}
