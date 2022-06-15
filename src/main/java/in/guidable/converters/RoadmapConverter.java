package in.guidable.converters;

import in.guidable.entities.Roadmap;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.model.CreateRoadmapResponsePublicMetadata;

import java.util.stream.Collectors;

public class RoadmapConverter {
    public static Roadmap toRoadmapEntity(CreateRoadmapDetail createRoadmapDetail)
    {
        return Roadmap.builder()
                .name(createRoadmapDetail.getName())
                .parentId(createRoadmapDetail.getParentId())
                .description(createRoadmapDetail.getDescription())
                .originalAuthor(createRoadmapDetail.getOriginalAuthor())
                .updatedBy(createRoadmapDetail.getUpdatedBy())
                .checkpoints(createRoadmapDetail.getCheckpoints().stream().map(CheckpointConverter::toCheckPointEntity).collect(Collectors.toList()))
                .isSharable(createRoadmapDetail.getIsSharable())
                .build();
    }

    public static CreateRoadmapResponse toRoadmapResponse(Roadmap roadmap, CreateRoadmapResponsePublicMetadata publicMetadata) {
        return new CreateRoadmapResponse()
                .id(roadmap.getId())
                .name(roadmap.getName())
                .parentId(roadmap.getParentId())
                .description(roadmap.getDescription())
                .originalAuthor(roadmap.getOriginalAuthor())
                .updatedBy(roadmap.getUpdatedBy())
                .checkpoints(roadmap.getCheckpoints().stream().map(CheckpointConverter::toCheckPointModel).collect(Collectors.toList()))
                .publicMetadata(publicMetadata);
    }
}
