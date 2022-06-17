package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Roadmap;
import in.guidable.entities.SharableLinkKeyResourceMap;
import in.guidable.entities.SharableResource;
import in.guidable.model.SharableResourceResponse;
import in.guidable.repositories.RoadmapRepo;
import in.guidable.repositories.SharableLinkKeyResourceMapRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SharableLinkKeyResourceMapRepo sharableLinkKeyResourceMapRepo;
    private final RoadmapRepo roadmapRepo;

    public SharableResourceResponse findByLinkKey(String linkKey) {

        SharableLinkKeyResourceMap linkKeyMap = sharableLinkKeyResourceMapRepo.findByLinkKey(linkKey).orElseThrow(EntityNotFoundException::new);
        if(linkKeyMap.getIsEnabled()) {
            if (linkKeyMap.getObjectType() == SharableResourceResponse.ObjectTypeEnum.ROADMAP) {
                Roadmap roadmap = roadmapRepo.findById(linkKeyMap.getResourceId()).orElseThrow(EntityNotFoundException::new);
                return new SharableResourceResponse()
                        .objectType(SharableResourceResponse.ObjectTypeEnum.ROADMAP)
                        .publicResource(RoadmapConverter.toRoadmapResponse(roadmap));

            } else if (linkKeyMap.getObjectType() == SharableResourceResponse.ObjectTypeEnum.ROADMAP_COLLECTION) {
                //TODO : get roadmap Collection
                throw new EntityNotFoundException();
            }
        }
        throw new EntityNotFoundException();
    }
}
