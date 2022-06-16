package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Roadmap;
import in.guidable.entities.SharableLinkKeyResourceMap;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.repositories.RoadmapRepo;
import in.guidable.repositories.SharableLinkKeyResourceMapRepo;
import in.guidable.repositories.SharableResourceLikeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoadmapService {

    private final RoadmapRepo roadmapRepo;
    //    private final SharableResourceViewRepo sharableResourceViewRepo;
    private final SharableResourceLikeRepo sharableResourceLikeRepo;
    private final SharableLinkKeyResourceMapRepo sharableLinkKeyResourceMapRepo;

    public CreateRoadmapResponse createRoadmap(CreateRoadmapDetail createRoadmapDetail) {
        Roadmap roadmap = roadmapRepo.save(RoadmapConverter.toRoadmapEntity(createRoadmapDetail));
        if(createRoadmapDetail.getIsSharable())
            createSharableLink(roadmap.getId());
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }

    public List<CreateRoadmapResponse> listRoadmap() {
        return roadmapRepo
                .findAll()
                .stream()
                .map(RoadmapConverter::toRoadmapResponse)
                .collect(Collectors.toList());

    }

    public CreateRoadmapResponse getRoadMap(String roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(roadmapId).orElseThrow(EntityNotFoundException::new);
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }

    @Transactional
    public CreateRoadmapResponse createSharableLink(String roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(roadmapId).orElseThrow(EntityNotFoundException::new);
        if (roadmap.getPublicMetadata().getLinkKey() != null) {
            roadmap.getPublicMetadata().setIsSharable(true);
            sharableLinkKeyResourceMapRepo.enableShareLinkOfResource(roadmap.getId());
        } else {
            try {
                String linkKey = sharableLinkKeyResourceMapRepo.generateUniqueLinkKey();
                roadmap.getPublicMetadata().setLinkKey(linkKey);
                roadmap.getPublicMetadata().setIsSharable(true);
                sharableLinkKeyResourceMapRepo.save(SharableLinkKeyResourceMap
                        .builder()
                        .resourceId(roadmap.getId())
                        .isEnabled(roadmap.getPublicMetadata().getIsSharable())
                        .userId("not-defined-placeholder")
                        .linkKey(roadmap.getPublicMetadata().getLinkKey())
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }

}
