package in.guidable.converters;

import in.guidable.entities.PublicMetadata;
import in.guidable.entities.Roadmap;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.model.SharableResourcePublicMetadata;

import java.util.stream.Collectors;

public class RoadmapConverter {
    public static Roadmap toRoadmapEntity(CreateRoadmapDetail createRoadmapDetail)
    {
        return Roadmap.builder()
                .name(createRoadmapDetail.getName())
                .parentId(createRoadmapDetail.getParentId())
                .description(createRoadmapDetail.getDescription())
                .checkpoints(createRoadmapDetail.getCheckpoints().stream().map(CheckpointConverter::toCheckPointEntity).collect(Collectors.toList()))
                .publicMetadata(PublicMetadata
                        .builder()
                        .isSharable(createRoadmapDetail.getIsSharable())
                        .likeCount(0L)
                        .viewCount(0L)
                        .build())
                .build();
    }

    public static CreateRoadmapResponse toRoadmapResponse(Roadmap roadmap) {
        return new CreateRoadmapResponse()
                .id(roadmap.getId())
                .name(roadmap.getName())
                .parentId(roadmap.getParentId())
                .description(roadmap.getDescription())
                .originalAuthor(roadmap.getOriginalAuthor())
                .updatedBy(roadmap.getUpdatedBy())
                .checkpoints(roadmap.getCheckpoints().stream().map(CheckpointConverter::toCheckPointModel).collect(Collectors.toList()))
                .publicMetadata(new SharableResourcePublicMetadata()
                        .isSharable(roadmap.getPublicMetadata().getIsSharable())
                        .publicLinkKey(roadmap.getPublicMetadata().getLinkKey())
                        .likes(roadmap.getPublicMetadata().getLikeCount())
                        .views(roadmap.getPublicMetadata().getViewCount()));
    }
}
