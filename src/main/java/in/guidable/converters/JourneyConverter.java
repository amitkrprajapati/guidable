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
                        .isSharable(createJourneyDetail.getIsSharable())
                        .build())
                .build();
    }

    public static JourneyResponse toJourneyResponse(Journey journey) {
        return new JourneyResponse()
                .name(journey.getName())
                .description(journey.getDescription());
    }
}
