package in.guidable.controllers;

import in.guidable.api.JourneysApi;
import in.guidable.converters.JourneyConverter;
import in.guidable.entities.Journey;
import in.guidable.model.CreateJourneyDetail;
import in.guidable.model.JourneyResponse;
import in.guidable.model.UpdateJourneyDetail;
import in.guidable.services.JourneyService;
import in.guidable.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class JourneyController implements JourneysApi {

    private final AuthenticationUtil authenticationUtil;
    private final JourneyService journeyService;
    @Override
    public ResponseEntity<JourneyResponse> createJourney(String authorization, CreateJourneyDetail createJourneyDetail) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        return ResponseEntity.ok(JourneyConverter.toJourneyResponse(journeyService.createJourney(userName, createJourneyDetail)));
    }

    @Override
    public ResponseEntity<List<JourneyResponse>> listJourney(String authorization, Integer limit, Integer page) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        Page<Journey> journeyList = journeyService.getJourneyList(userName,limit,page);
        return ResponseEntity.ok(journeyList.getContent()
                .stream()
                .map(JourneyConverter::toJourneyResponse)
                .collect(Collectors.toList()));
    }
    @Override
    public ResponseEntity<JourneyResponse> getJourney(String authorization , String journeyId){
        String userName= authenticationUtil.getUserFromToken(authorization);
        return ResponseEntity.ok(JourneyConverter.toJourneyResponse(journeyService.getJourney(userName,journeyId)));
    }

    @Override
    public ResponseEntity<JourneyResponse> updateJourney(String authorization , String journeyId, UpdateJourneyDetail updateJourneyDetail){
        String userName= authenticationUtil.getUserFromToken(authorization);
        return ResponseEntity.ok(JourneyConverter.toJourneyResponse(journeyService.updateJourney(userName,journeyId,updateJourneyDetail)));
    }

    @Override
    public ResponseEntity<Void> deleteJourney(String authorization, String journeyId) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        journeyService.deleteJourney(userName,journeyId);
        return ResponseEntity.noContent().build();
    }

}
