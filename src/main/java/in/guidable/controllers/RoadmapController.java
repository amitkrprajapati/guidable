package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoadmapController implements RoadmapsApi {

    @Override
    public ResponseEntity<CreateRoadmapResponse> createRoadmap(CreateRoadmapDetail book) {
        return ResponseEntity.ok(
                new CreateRoadmapResponse()
                .id(book.getId())
                .name(book.getName()));
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
