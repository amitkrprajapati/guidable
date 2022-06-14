package in.guidable.converters;

import in.guidable.entities.Roadmap;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;

public class RoadmapConverter {
    public static Roadmap toRoadmapEntity(CreateRoadmapDetail createRoadmapDetail)
    {
        return Roadmap.builder()
                .id("default")
                .name(createRoadmapDetail.getName())
                .build();
    }

    public static CreateRoadmapResponse toRoadmapResponse(Roadmap roadmap) {
        return new CreateRoadmapResponse()
                .name(roadmap.getName());
    }
}
