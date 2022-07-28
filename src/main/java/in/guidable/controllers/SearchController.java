package in.guidable.controllers;

import in.guidable.api.PublicApi;
import in.guidable.converters.JourneyConverter;
import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Journey;
import in.guidable.entities.Roadmap;
import in.guidable.model.JourneyResponse;
import in.guidable.model.RoadmapResponse;
import in.guidable.model.SharableResourceResponse;
import in.guidable.model.SortByType;
import in.guidable.services.SearchService;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchController implements PublicApi {

  private final SearchService searchService;

  @Override
  public ResponseEntity<SharableResourceResponse> findByLinkKey(String linkKey) {
    try {
      SharableResourceResponse sharableResourceResponse = searchService.findByLinkKey(linkKey);
      return ResponseEntity.ok(sharableResourceResponse);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  public ResponseEntity<List<RoadmapResponse>> getTopRoadmaps(
      Integer limit, Integer page, SortByType sortBy) {
    Page<Roadmap> roadmapList = searchService.getTopPublicRoadmaps(limit, page, sortBy);

    return ResponseEntity.ok(
        roadmapList.stream().map(RoadmapConverter::toRoadmapResponse).collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<List<JourneyResponse>> getTopJourneys(Integer limit, Integer page, SortByType sortBy) {
    Page<Journey> journeyList = searchService.getTopPublicJourneys(limit, page, sortBy);

    return ResponseEntity.ok(
            journeyList.getContent().stream()
                    .map(JourneyConverter::toJourneyResponse)
                    .collect(Collectors.toList()));
  }
}
