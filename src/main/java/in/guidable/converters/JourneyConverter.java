package in.guidable.converters;

import in.guidable.entities.Journey;
import in.guidable.entities.PublicMetadata;
import in.guidable.model.CreateJourneyDetail;
import in.guidable.model.JourneyResponse;

import java.util.stream.Collectors;

public class JourneyConverter {
    public static Journey toJourneyEntity(CreateJourneyDetail createJourneyDetail) {
        return Journey.builder()
                .name(createJourneyDetail.getName())
                .description(createJourneyDetail.getDescription())
                .publicMetadata(PublicMetadata.builder()
                        .isSharable(false)
                        .likeCount(0L)
                        .viewCount(0L)
                        .build())
                .build();
    }

    public static JourneyResponse toJourneyResponse(Journey journey) {
        return new JourneyResponse()
                .id(journey.getId().toString())
                .name(journey.getName())
                .description(journey.getDescription());
    }
}
