package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.services.RoadmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CreateRoadmapResponse> updateRoadmap(String roadmapId, UpdateRoadmapDetail updataRoadmapDetail) {
        return ResponseEntity.ok(roadmapService.updateRoadmap(roadmapId, updataRoadmapDetail));
    }

    @Override
    public ResponseEntity<CreateRoadmapResponse> enableShareLink(String roadmapId) {
        return ResponseEntity.ok(roadmapService.enableShareLink(roadmapId));
    }

    @Override
    public ResponseEntity<CreateRoadmapResponse> disableShareLink(String roadmapId) {
        return ResponseEntity.ok(roadmapService.disableShareLink(roadmapId));
    }
}
