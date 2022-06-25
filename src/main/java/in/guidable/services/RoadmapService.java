package in.guidable.services;

import in.guidable.converters.CheckpointConverter;
import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Customer;
import in.guidable.entities.Roadmap;
import in.guidable.entities.SharableLinkKeyResourceMap;
import in.guidable.exceptions.NoRoadMapFoundException;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.PublicResourceType;
import in.guidable.model.RoadmapResponse;
import in.guidable.model.SharableResourceResponse;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.repositories.CustomerRepo;
import in.guidable.repositories.RoadmapRepo;
import in.guidable.repositories.SharableLinkKeyResourceMapRepo;
import in.guidable.repositories.SharableResourceLikeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoadmapService {
    private final CustomerRepo customerRepo;
    private final RoadmapRepo roadmapRepo;
    //    private final SharableResourceViewRepo sharableResourceViewRepo;
    private final SharableResourceLikeRepo sharableResourceLikeRepo;
    private final SharableLinkKeyResourceMapRepo sharableLinkKeyResourceMapRepo;

    public RoadmapResponse createRoadmap(String userName, CreateRoadmapDetail createRoadmapDetail) {
        Customer customer = customerRepo.findByCustomerUserName(userName).orElseThrow(EntityNotFoundException::new);
        Roadmap newRoadmap = RoadmapConverter.toRoadmapEntity(createRoadmapDetail)
                .toBuilder()
                .originalAuthor("todo-placeholder")
                .updatedBy("todo-placeholder")
                .build();
        Roadmap roadmap = roadmapRepo.save(newRoadmap);
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }

    public List<RoadmapResponse> listRoadmap(String username) {

//        List<Roadmap> roadMapList = roadmapRepo.findByCustomerCustomerUserName(username).orElseThrow(()-> new NoRoadMapFoundException("No Road Map linked to the user"));
        return new ArrayList<>();
//        roadMapList
//                .stream()
//                .map(RoadmapConverter::toRoadmapResponse)
//                .collect(Collectors.toList());

    }

    public RoadmapResponse getRoadMap(String roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(UUID.fromString(roadmapId)).orElseThrow(EntityNotFoundException::new);
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }

    @Transactional
    public RoadmapResponse enableShareLink(String roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(UUID.fromString(roadmapId)).orElseThrow(EntityNotFoundException::new);
        if (roadmap.getPublicMetadata().getLinkKey() != null) {
            roadmap.getPublicMetadata().setIsSharable(true);
            sharableLinkKeyResourceMapRepo.changeLinkStatus(roadmap.getId().toString(), true);
        } else {
            try {
                String linkKey = sharableLinkKeyResourceMapRepo.generateUniqueLinkKey();
                roadmap.getPublicMetadata().setLinkKey(linkKey);
                roadmap.getPublicMetadata().setIsSharable(true);
                sharableLinkKeyResourceMapRepo.save(SharableLinkKeyResourceMap
                        .builder()
                        .objectType(PublicResourceType.ROADMAP)
                        .resourceId(roadmap.getId())
                        .isEnabled(roadmap.getPublicMetadata().getIsSharable())
//                        .customerId("not-defined-placeholder")
                        .linkKey(roadmap.getPublicMetadata().getLinkKey())
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }

    @Transactional
    public RoadmapResponse disableShareLink(String roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(UUID.fromString(roadmapId)).orElseThrow(EntityNotFoundException::new);
        roadmap.getPublicMetadata().setIsSharable(false);
        sharableLinkKeyResourceMapRepo.changeLinkStatus(roadmap.getId().toString(), false);
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }

    @Transactional
    public RoadmapResponse updateRoadmap(String roadmapId, UpdateRoadmapDetail updataRoadmapDetail) {
        Roadmap roadmap = roadmapRepo.findById(UUID.fromString(roadmapId)).orElseThrow(EntityNotFoundException::new);
//        checkpointRepo.deleteCheckpointsByRoadmapId(roadmapId);
        roadmap.setName(updataRoadmapDetail.getName());
        roadmap.setDescription(updataRoadmapDetail.getDescription());
        roadmap.setName(updataRoadmapDetail.getName());
        roadmap.setUpdatedBy("todo_place_holder");

        return RoadmapConverter.toRoadmapResponse(roadmap);
    }
}
