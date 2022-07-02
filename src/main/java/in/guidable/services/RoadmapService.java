package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Customer;
import in.guidable.entities.Journey;
import in.guidable.entities.PublicMetadata;
import in.guidable.entities.Roadmap;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.repositories.CustomerRepo;
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
  private final CustomerRepo customerRepo;
  private final RoadmapRepo roadmapRepo;
  private final JourneyRepo journeyRepo;
  //    private final SharableResourceViewRepo sharableResourceViewRepo;
  private final SharableResourceLikeRepo sharableResourceLikeRepo;
  private final SharableLinkKeyResourceMapRepo sharableLinkKeyResourceMapRepo;

  public Roadmap createRoadmap(String userName, CreateRoadmapDetail createRoadmapDetail) {
    Customer customer =
        customerRepo
            .findByCustomerUserName(userName)
            .orElseThrow(() -> RenderableExceptionGenerator.generateInvalidUserException(userName));

    Journey journey =
        journeyRepo
            .findByCustomerAndId(customer, UUID.fromString(createRoadmapDetail.getJourneyId()))
            .orElseThrow(
                () ->
                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                        "Journey", createRoadmapDetail.getJourneyId()));

    Roadmap newRoadmap =
        RoadmapConverter.toRoadmapEntity(createRoadmapDetail)
            .toBuilder()
            .journey(journey)
            .customerId(customer.getId())
            .originalAuthor(customer.getCustomerUserName())
            .updatedBy(customer.getCustomerUserName())
            .build();
    newRoadmap.setPublicMetadata(new PublicMetadata());
    return roadmapRepo.save(newRoadmap);
  }

  public Page<Roadmap> listRoadmap(String userName, String journeyId, Integer limit, Integer page) {

    Customer customer =
        customerRepo
            .findByCustomerUserName(userName)
            .orElseThrow(() -> RenderableExceptionGenerator.generateInvalidUserException(userName));
    Journey journey =
        journeyRepo
            .findByCustomerAndId(customer, UUID.fromString(journeyId))
            .orElseThrow(
                () ->
                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                        "Journey", journeyId));
    Pageable pageable = PageRequest.of(page, limit);
    return roadmapRepo.findAllByJourney(journey, pageable);
  }

  public Roadmap getRoadmap(String userName, String roadmapId) {
    Customer customer =
        customerRepo
            .findByCustomerUserName(userName)
            .orElseThrow(() -> RenderableExceptionGenerator.generateInvalidUserException(userName));

    return roadmapRepo
        .findByIdAndCustomerId(UUID.fromString(roadmapId), customer.getId())
        .orElseThrow(
            () ->
                RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                    "Roadmap", roadmapId));
  }

  @Transactional
  public Roadmap updateRoadmap(
      String userName, String roadmapId, UpdateRoadmapDetail updateRoadmapDetail) {
    Roadmap roadmap = getRoadmap(userName, roadmapId);

    roadmap.setUpdatedBy(userName);
    roadmap.setName(updateRoadmapDetail.getName());
    roadmap.setDescription(updateRoadmapDetail.getDescription());
    return roadmapRepo.save(roadmap);
  }

  @Transactional
  public void deleteRoadmap(String userName, String roadmapId) {
    Customer customer =
        customerRepo
            .findByCustomerUserName(userName)
            .orElseThrow(() -> RenderableExceptionGenerator.generateInvalidUserException(userName));
    roadmapRepo.deleteByIdAndCustomerId(UUID.fromString(roadmapId), customer.getId());
  }
}
