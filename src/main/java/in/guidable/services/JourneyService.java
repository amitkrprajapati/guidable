package in.guidable.services;

import in.guidable.converters.JourneyConverter;
import in.guidable.entities.Customer;
import in.guidable.entities.Journey;
import in.guidable.model.CreateJourneyDetail;
import in.guidable.model.JourneyResponse;
import in.guidable.repositories.CustomerRepo;
import in.guidable.repositories.JourneyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JourneyService {

    private final JourneyRepo journeyRepo;
    private final CustomerRepo customerRepo;
    public JourneyResponse createJourney(String userName, CreateJourneyDetail createJourneyDetail) {
        Customer customer = customerRepo.findByCustomerUserName(userName).orElseThrow();
        Journey journey = JourneyConverter.toJourneyEntity(createJourneyDetail);
        journey.setCustomer(customer);
        return JourneyConverter.toJourneyResponse(journeyRepo.save(journey));
    }
}
