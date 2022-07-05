package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Journey;
import in.guidable.entities.PublicMetadata;
import in.guidable.entities.Roadmap;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.models.CustomerModel;
import in.guidable.repositories.JourneyRepo;
import in.guidable.repositories.RoadmapRepo;
import in.guidable.repositories.SharableLinkKeyResourceMapRepo;
import in.guidable.repositories.SharableResourceLikeRepo;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoadmapService {
  private final RoadmapRepo roadmapRepo;
  private final JourneyRepo journeyRepo;
  //    private final SharableResourceViewRepo sharableResourceViewRepo;
  private final SharableResourceLikeRepo sharableResourceLikeRepo;
  private final SharableLinkKeyResourceMapRepo sharableLinkKeyResourceMapRepo;

  public Roadmap createRoadmap(
      CustomerModel customerModel, CreateRoadmapDetail createRoadmapDetail) {

    Journey journey =
        journeyRepo
            .findByCustomer_IdAndId(customerModel.getUserId(), createRoadmapDetail.getJourneyId())
            .orElseThrow(
                () ->
                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                        "Journey", createRoadmapDetail.getJourneyId()));

    Roadmap newRoadmap =
        RoadmapConverter.toRoadmapEntity(createRoadmapDetail)
            .toBuilder()
            .journey(journey)
            .customerId(customerModel.getUserId())
            .originalAuthor(customerModel.getUserName())
            .updatedBy(customerModel.getUserName())
            .build();
    newRoadmap.setPublicMetadata(new PublicMetadata());
    return roadmapRepo.save(newRoadmap);
  }

  public Page<Roadmap> listRoadmap(
      CustomerModel customerModel, UUID journeyId, Integer limit, Integer page) {

    Journey journey =
        journeyRepo
            .findByCustomer_IdAndId(customerModel.getUserId(), journeyId)
            .orElseThrow(
                () ->
                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                        "Journey", journeyId));
    Pageable pageable = PageRequest.of(page, limit);
    return roadmapRepo.findAllByJourney(journey, pageable);
  }

  public Roadmap getRoadmap(CustomerModel customerModel, UUID roadmapId) {
    return roadmapRepo
        .findByCustomerIdAndId(customerModel.getUserId(), roadmapId)
        .orElseThrow(
            () ->
                RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                    "Roadmap", roadmapId));
  }

  @Transactional
  public Roadmap updateRoadmap(
      CustomerModel customerModel, UUID roadmapId, UpdateRoadmapDetail updateRoadmapDetail) {
    Roadmap roadmap = getRoadmap(customerModel, roadmapId);

    roadmap.setUpdatedBy(customerModel.getUserName());
    roadmap.setName(updateRoadmapDetail.getName());
    roadmap.setDescription(updateRoadmapDetail.getDescription());
    return roadmapRepo.save(roadmap);
  }

  @Transactional
  public void deleteRoadmap(CustomerModel customerModel, UUID roadmapId) {
    if (roadmapRepo.deleteByCustomerIdAndId(customerModel.getUserId(), roadmapId) != 1) {
      throw RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
          "Roadmap", roadmapId);
    }
    ;
  }
}
