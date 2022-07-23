package in.guidable.event.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Roadmap;
import in.guidable.entities.SharableLinkKeyResourceMap;
import in.guidable.model.PublicResourceType;
import in.guidable.model.SharableResourceResponse;
import in.guidable.model.SortByType;
import in.guidable.repositories.RoadmapRepo;
import in.guidable.repositories.SharableLinkKeyResourceMapRepo;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
  private final SharableLinkKeyResourceMapRepo sharableLinkKeyResourceMapRepo;
  private final RoadmapRepo roadmapRepo;

  public SharableResourceResponse findByLinkKey(String linkKey) {

    SharableLinkKeyResourceMap linkKeyMap =
        sharableLinkKeyResourceMapRepo
            .findByLinkKey(linkKey)
            .orElseThrow(EntityNotFoundException::new);
    if (linkKeyMap.getIsEnabled()) {
      if (linkKeyMap.getObjectType() == PublicResourceType.ROADMAP) {
        Roadmap roadmap =
            roadmapRepo
                .findById(linkKeyMap.getResourceId())
                .orElseThrow(EntityNotFoundException::new);
        return new SharableResourceResponse()
            .objectType(PublicResourceType.ROADMAP)
            .publicResource(RoadmapConverter.toRoadmapResponse(roadmap));

      } else if (linkKeyMap.getObjectType() == PublicResourceType.JOURNEY) {
        // TODO : get roadmap Collection
        throw new EntityNotFoundException();
      }
    }
    throw new EntityNotFoundException();
  }

  public Page<Roadmap> getTopPublicRoadmaps(Integer limit, Integer page, SortByType sortBy) {
    Pageable pageable = PageRequest.of(page, limit, Sort.by("publicMetadata." + sortBy.getValue()));
    return roadmapRepo.getAllBySharedRoadmaps(pageable);
  }
}
