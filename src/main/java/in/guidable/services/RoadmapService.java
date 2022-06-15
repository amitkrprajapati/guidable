package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Roadmap;
import in.guidable.entities.SharableResource;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.model.CreateRoadmapResponsePublicMetadata;
import in.guidable.repositories.RoadmapRepo;
import in.guidable.repositories.SharableResourceLikeRepo;
import in.guidable.repositories.SharableResourceViewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoadmapService {

    private final RoadmapRepo roadmapRepo;
//    private final SharableResourceViewRepo sharableResourceViewRepo;
    private final SharableResourceLikeRepo sharableResourceLikeRepo;

    public CreateRoadmapResponse createRoadmap(CreateRoadmapDetail createRoadmapDetail)
    {
        Roadmap roadmap =  roadmapRepo.save(RoadmapConverter.toRoadmapEntity(createRoadmapDetail));
        Long likeCount = sharableResourceLikeRepo.getLikeCountOfResource(roadmap.getId());
        CreateRoadmapResponsePublicMetadata publicMetadata = new CreateRoadmapResponsePublicMetadata()
                .isSharable(roadmap.getIsSharable())
                .likes(likeCount);
        return RoadmapConverter.toRoadmapResponse(roadmap, publicMetadata);
    }

    public List<CreateRoadmapResponse> listRoadmap() {
         return roadmapRepo
                 .findAll()
                 .stream()
                 .map(x -> RoadmapConverter.toRoadmapResponse(x, new CreateRoadmapResponsePublicMetadata() /*TODO: Fix this with group query*/))
                 .collect(Collectors.toList());

    }

    public CreateRoadmapResponse getRoadMap(String roadmapId) {
        Roadmap roadmap =  roadmapRepo.findById(roadmapId).orElseThrow(EntityNotFoundException::new);
        Long likeCount = sharableResourceLikeRepo.getLikeCountOfResource(roadmap.getId());
        CreateRoadmapResponsePublicMetadata publicMetadata = new CreateRoadmapResponsePublicMetadata()
                .isSharable(roadmap.getIsSharable())
                .likes(likeCount);
        return RoadmapConverter.toRoadmapResponse(roadmap, publicMetadata);
    }
}
