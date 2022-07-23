package in.guidable.controllers;

import in.guidable.api.JourneysApi;
import in.guidable.converters.JourneyConverter;
import in.guidable.entities.Journey;
import in.guidable.event.services.JourneyService;
import in.guidable.model.CreateJourneyDetail;
import in.guidable.model.JourneyResponse;
import in.guidable.model.UpdateJourneyDetail;
import in.guidable.models.CustomerModel;
import in.guidable.util.AuthenticationUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JourneyController implements JourneysApi {

  private final AuthenticationUtil authenticationUtil;
  private final JourneyService journeyService;

  @Override
  public ResponseEntity<JourneyResponse> createJourney(
      String authorization, CreateJourneyDetail createJourneyDetail) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    return ResponseEntity.ok(
        JourneyConverter.toJourneyResponse(
            journeyService.createJourney(customerModel, createJourneyDetail)));
  }

  @Override
  public ResponseEntity<List<JourneyResponse>> listJourney(
      String authorization, Integer limit, Integer page) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    Page<Journey> journeyList = journeyService.getJourneyList(customerModel, limit, page);
    return ResponseEntity.ok(
        journeyList.getContent().stream()
            .map(JourneyConverter::toJourneyResponse)
            .collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<JourneyResponse> getJourney(String authorization, UUID journeyId) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    return ResponseEntity.ok(
        JourneyConverter.toJourneyResponse(journeyService.getJourney(customerModel, journeyId)));
  }

  @Override
  public ResponseEntity<JourneyResponse> updateJourney(
      String authorization, UUID journeyId, UpdateJourneyDetail updateJourneyDetail) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    return ResponseEntity.ok(
        JourneyConverter.toJourneyResponse(
            journeyService.updateJourney(customerModel, journeyId, updateJourneyDetail)));
  }

  @Override
  public ResponseEntity<Void> deleteJourney(String authorization, UUID journeyId) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    journeyService.deleteJourney(customerModel, journeyId);
    return ResponseEntity.noContent().build();
  }
}
