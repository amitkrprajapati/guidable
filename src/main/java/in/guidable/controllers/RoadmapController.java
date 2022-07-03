package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Roadmap;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.RoadmapResponse;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.services.RoadmapService;
import in.guidable.util.AuthenticationUtil;
import in.guidable.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        Roadmap roadmap = roadmapService.createRoadmap(userName, createRoadmapDetail);
        return new ResponseEntity<>(
                RoadmapConverter.toRoadmapResponse(roadmap),
                HttpStatus.CREATED);

    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<RoadmapResponse>> listRoadmap(String authorization, String journeyId, Integer limit, Integer page) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        ValidationUtil.validateId("JourneyId", journeyId);
        Page<Roadmap> roadmapList = roadmapService.listRoadmap(userName, journeyId, limit, page);
        return ResponseEntity.ok(roadmapList
                .stream()
                .map(RoadmapConverter::toRoadmapResponse)
                .collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<RoadmapResponse> getRoadmap(String authorization, String roadmapId) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        ValidationUtil.validateId("RoadmapId", roadmapId);
        Roadmap roadmap = roadmapService.getRoadmap(userName, roadmapId);
        return ResponseEntity.ok(RoadmapConverter.toRoadmapResponse(roadmap));
    }
    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<RoadmapResponse> updateRoadmap(String authorization, String roadmapId, UpdateRoadmapDetail updateRoadmapDetail) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        ValidationUtil.validateId("RoadmapId", roadmapId);
        Roadmap roadmap = roadmapService.updateRoadmap(userName, roadmapId, updateRoadmapDetail);
        return ResponseEntity.ok(RoadmapConverter.toRoadmapResponse(roadmap));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteRoadmap(String authorization, String roadmapId) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        ValidationUtil.validateId("RoadmapId", roadmapId);
        roadmapService.deleteRoadmap(userName, roadmapId);
        return ResponseEntity.noContent().build();
    }
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
