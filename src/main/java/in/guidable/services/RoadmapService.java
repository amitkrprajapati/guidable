package in.guidable.services;

import in.guidable.converters.CheckpointConverter;
import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Checkpoints;
import in.guidable.entities.Roadmap;
import in.guidable.entities.SharableLinkKeyResourceMap;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.model.SharableResourceResponse;
import in.guidable.model.UpdateRoadmapDetail;
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
        Roadmap newRoadmap = RoadmapConverter.toRoadmapEntity(createRoadmapDetail)
                .toBuilder()
                .originalAuthor("todo-placeholder")
                .updatedBy("todo-placeholder")
                .build();
        Roadmap roadmap = roadmapRepo.save(newRoadmap);
        if(createRoadmapDetail.getIsSharable())
            enableShareLink(roadmap.getId());
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
    public CreateRoadmapResponse enableShareLink(String roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(roadmapId).orElseThrow(EntityNotFoundException::new);
        if (roadmap.getPublicMetadata().getLinkKey() != null) {
            roadmap.getPublicMetadata().setIsSharable(true);
            sharableLinkKeyResourceMapRepo.changeLinkStatus(roadmap.getId(), true);
        } else {
            try {
                String linkKey = sharableLinkKeyResourceMapRepo.generateUniqueLinkKey();
                roadmap.getPublicMetadata().setLinkKey(linkKey);
                roadmap.getPublicMetadata().setIsSharable(true);
                sharableLinkKeyResourceMapRepo.save(SharableLinkKeyResourceMap
                        .builder()
                        .objectType(SharableResourceResponse.ObjectTypeEnum.ROADMAP)
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

    @Transactional
    public CreateRoadmapResponse disableShareLink(String roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(roadmapId).orElseThrow(EntityNotFoundException::new);
        roadmap.getPublicMetadata().setIsSharable(false);
        sharableLinkKeyResourceMapRepo.changeLinkStatus(roadmap.getId(), false);
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }

    @Transactional
    public CreateRoadmapResponse updateRoadmap(String roadmapId, UpdateRoadmapDetail updataRoadmapDetail) {
        Roadmap roadmap = roadmapRepo.findById(roadmapId).orElseThrow(EntityNotFoundException::new);
//        checkpointRepo.deleteCheckpointsByRoadmapId(roadmapId);
        roadmap.setName(updataRoadmapDetail.getName());
        roadmap.setDescription(updataRoadmapDetail.getDescription());
        roadmap.setCheckpoints(updataRoadmapDetail.getCheckpoints().stream().map(CheckpointConverter::toCheckPointEntity).collect(Collectors.toList()));
        roadmap.setName(updataRoadmapDetail.getName());
        roadmap.setUpdatedBy("todo_place_holder");

        if(updataRoadmapDetail.getIsSharable())
            enableShareLink(roadmap.getId());
        roadmapRepo.save(roadmap);
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }
}
