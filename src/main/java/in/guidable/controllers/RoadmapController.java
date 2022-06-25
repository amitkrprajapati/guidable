package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.exceptions.RenderableException;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.RoadmapResponse;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.services.RoadmapService;
import in.guidable.util.AuthenticationUtil;
import in.guidable.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoadmapController implements RoadmapsApi {

    private final RoadmapService roadmapService;
    private  final AuthenticationUtil authenticationUtil;

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<RoadmapResponse> createRoadmap(String authorization, CreateRoadmapDetail createRoadmapDetail) {

        String userName= authenticationUtil.getUserFromToken(authorization);
        ValidationUtil.validateId("JourneyId", createRoadmapDetail.getJourneyId());
        return ResponseEntity.ok(roadmapService.createRoadmap(userName, createRoadmapDetail));

    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<RoadmapResponse>> listRoadmap(String authorization, String journeyId, Integer limit, Integer page) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        ValidationUtil.validateId("JourneyId", journeyId);
        return ResponseEntity.ok(roadmapService.listRoadmap(userName, journeyId, limit, page));
    }

    @Override
    public ResponseEntity<RoadmapResponse> getRoadmap(String authorization, String roadmapId) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        ValidationUtil.validateId("RoadmapId", roadmapId);
        return ResponseEntity.ok(roadmapService.getRoadMap(userName, roadmapId));
    }
//    @Override
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<RoadmapResponse> updateRoadmap(String roadmapId, UpdateRoadmapDetail updataRoadmapDetail) {
//        return ResponseEntity.ok(roadmapService.updateRoadmap(roadmapId, updataRoadmapDetail));
//    }
//
//    @Override
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<RoadmapResponse> enableShareLink(String roadmapId) {
//        return ResponseEntity.ok(roadmapService.enableShareLink(roadmapId));
//    }
//
//    @Override
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<RoadmapResponse> disableShareLink(String roadmapId) {
//        return ResponseEntity.ok(roadmapService.disableShareLink(roadmapId));
//    }
}
