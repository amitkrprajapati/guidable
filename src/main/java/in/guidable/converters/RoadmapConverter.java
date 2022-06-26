package in.guidable.converters;

import in.guidable.entities.PublicMetadata;
import in.guidable.entities.Roadmap;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.RoadmapResponse;
import in.guidable.model.SharableResourcePublicMetadata;

import java.util.stream.Collectors;

public class RoadmapConverter {
    public static Roadmap toRoadmapEntity(CreateRoadmapDetail createRoadmapDetail)
    {
        return Roadmap.builder()
                .name(createRoadmapDetail.getName())
                .description(createRoadmapDetail.getDescription())
                .publicMetadata(new PublicMetadata())
                .build();
    }

    public static RoadmapResponse toRoadmapResponse(Roadmap roadmap) {
        return new RoadmapResponse()
                .id(roadmap.getId().toString())
                .name(roadmap.getName())
                .journeyId(roadmap.getJourney().getId().toString())
                .description(roadmap.getDescription())
                .originalAuthor(roadmap.getOriginalAuthor())
                .updatedBy(roadmap.getUpdatedBy())
                .publicMetadata(new SharableResourcePublicMetadata()
                        .isSharable(roadmap.getPublicMetadata().getIsSharable())
                        .publicLinkKey(roadmap.getPublicMetadata().getLinkKey())
                        .likes(roadmap.getPublicMetadata().getLikeCount())
                        .views(roadmap.getPublicMetadata().getViewCount()));
    }
}
