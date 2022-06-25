package in.guidable.controllers;

import in.guidable.api.JourneysApi;
import in.guidable.model.CreateJourneyDetail;
import in.guidable.model.JourneyResponse;
import in.guidable.services.JourneyService;
import in.guidable.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JourneyController implements JourneysApi {

    private final AuthenticationUtil authenticationUtil;
    private final JourneyService journeyService;
    @Override
    public ResponseEntity<JourneyResponse> createJourney(String authorization, CreateJourneyDetail createJourneyDetail) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        return ResponseEntity.ok(journeyService.createJourney(userName, createJourneyDetail));
    }
}
