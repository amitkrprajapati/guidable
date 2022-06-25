package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.RoadmapResponse;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.services.RoadmapService;
import in.guidable.util.AuthenticationUtil;
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
    public ResponseEntity<RoadmapResponse> createRoadmap(String authToken, CreateRoadmapDetail createRoadmapDetail) {

        String userName= authenticationUtil.getUserFromToken(authToken);
        return ResponseEntity.ok(roadmapService.createRoadmap(userName, createRoadmapDetail));
    }

//    @Override
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<List<RoadmapResponse>> getRoadMaps(String authToken) {
//
//        String userName= authenticationUtil.getUserFromToken(authToken);
//        return ResponseEntity.ok(roadmapService.listRoadmap(userName));
//
//    }
//
//    @Override
//    public ResponseEntity<RoadmapResponse> getRoadMap(String roadmapId) {
//        return ResponseEntity.ok(roadmapService.getRoadMap(roadmapId));
//    }
//
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
