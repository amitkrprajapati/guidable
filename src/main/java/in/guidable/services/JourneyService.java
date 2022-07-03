package in.guidable.services;

import in.guidable.converters.JourneyConverter;
import in.guidable.entities.Customer;
import in.guidable.entities.Journey;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.CreateJourneyDetail;
import in.guidable.model.UpdateJourneyDetail;
import in.guidable.models.CustomerModel;
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
  public Journey createJourney(
      CustomerModel customerModel, CreateJourneyDetail createJourneyDetail) {
    Customer customer =
        customerRepo
            .findById(customerModel.getUserId())
            .orElseThrow(
                () ->
                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                        "Customer", customerModel.getUserId()));
    Journey journey =
        JourneyConverter.toJourneyEntity(createJourneyDetail)
            .toBuilder()
            .originalAuthor(customerModel.getUserName())
            .updatedBy(customerModel.getUserName())
            .customer(customer)
            .build();
    journey.setCreationDate(new Date());
    return journeyRepo.save(journey);
  }

  public Page<Journey> getJourneyList(CustomerModel customerModel, Integer limit, Integer page) {
    Pageable paging = PageRequest.of(page, limit);
    return journeyRepo.findByCustomer_Id(customerModel.getUserId(), paging);
  }

  public Journey getJourney(CustomerModel customerModel, UUID journeyId) {
    return journeyRepo
        .findByCustomer_IdAndId(customerModel.getUserId(), journeyId)
        .orElseThrow(
            () ->
                RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                    "Journey", journeyId));
  }

  @Transactional
  public Journey updateJourney(
      CustomerModel customerModel, UUID journeyId, UpdateJourneyDetail updateJourneyDetail) {
    Journey journey = getJourney(customerModel, journeyId);
    journey.setUpdatedBy(customerModel.getUserName());
    journey.setName(updateJourneyDetail.getName());
    journey.setDescription(updateJourneyDetail.getDescription());
    return journeyRepo.save(journey);
  }

  public void deleteJourney(CustomerModel customerModel, UUID journeyId) {
    journeyRepo.deleteByCustomerIdAndId(customerModel.getUserId(), journeyId);
  }
}
