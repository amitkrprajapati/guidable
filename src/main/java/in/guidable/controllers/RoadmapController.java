package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.services.RoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CreateRoadmapResponse> createRoadmap(CreateRoadmapDetail createRoadmapDetail) {
        return ResponseEntity.ok(roadmapService.createRoadmap(createRoadmapDetail));
    }

    @Override
    public ResponseEntity<List<CreateRoadmapResponse>> getRoadMap() {
        return ResponseEntity.ok(
                new ArrayList<>()
                        {{
                            add(new CreateRoadmapResponse()
                                .id("default")
                                .name("Some-journey"));
                        }});
    }
}
