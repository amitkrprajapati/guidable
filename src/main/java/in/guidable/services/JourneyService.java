package in.guidable.services;

import in.guidable.converters.JourneyConverter;
import in.guidable.entities.Customer;
import in.guidable.entities.Journey;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.CreateJourneyDetail;
import in.guidable.model.UpdateJourneyDetail;
import in.guidable.repositories.CustomerRepo;
import in.guidable.repositories.JourneyRepo;
import java.util.Date;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JourneyService {

  private final JourneyRepo journeyRepo;
  private final CustomerRepo customerRepo;

  @Transactional
  public Journey createJourney(String userName, CreateJourneyDetail createJourneyDetail) {
    Customer customer =
        customerRepo
            .findByCustomerUserName(userName)
            .orElseThrow(
                () ->
                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                        "Customer", userName));
    Journey journey =
        JourneyConverter.toJourneyEntity(createJourneyDetail)
            .toBuilder()
            .originalAuthor(userName)
            .updatedBy(userName)
            .customer(customer)
            .build();
    journey.setCreationDate(new Date());
    return journeyRepo.save(journey);
  }

  public Page<Journey> getJourneyList(String userName, Integer limit, Integer page) {
    Customer customer =
        customerRepo
            .findByCustomerUserName(userName)
            .orElseThrow(
                () ->
                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                        "Customer", userName));
    Pageable paging = PageRequest.of(page, limit);
    return journeyRepo
        .findByCustomer(customer, paging)
        .orElseThrow(
            () ->
                RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                    "Journey", userName));
  }

  public Journey getJourney(String userName, String journeyId) {
    Customer customer =
        customerRepo
            .findByCustomerUserName(userName)
            .orElseThrow(
                () ->
                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                        "Customer", userName));
    return journeyRepo
        .findByCustomerAndId(customer, UUID.fromString(journeyId))
        .orElseThrow(
            () ->
                RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                    "Journey", userName));
  }

  @Transactional
  public Journey updateJourney(
      String userName, String journeyId, UpdateJourneyDetail updateJourneyDetail) {
    Journey journey = getJourney(userName, journeyId);
    journey.setUpdatedBy(userName);
    journey.setName(updateJourneyDetail.getName());
    journey.setDescription(updateJourneyDetail.getDescription());
    return journeyRepo.save(journey);
  }

  public void deleteJourney(String userName, String journeyId) {
    Journey journey = getJourney(userName, journeyId);
    journeyRepo.deleteById(journey.getId());
  }
}
