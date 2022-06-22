package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.jwt.JwtUtil;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.services.RoadmapService;
import in.guidable.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoadmapController implements RoadmapsApi {

    private  final JwtUtil jwtUtil;
    private final RoadmapService roadmapService;
    private  final AuthenticationUtil authUtil;
    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CreateRoadmapResponse> createRoadmap(CreateRoadmapDetail createRoadmapDetail, String token) {

        String userName= authUtil.getUserFromToken(token);
        return ResponseEntity.ok(roadmapService.createRoadmap(userName, createRoadmapDetail));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<CreateRoadmapResponse>> getRoadMaps(String token) {
        String userName= authUtil.getUserFromToken(token);
         return ResponseEntity.ok(roadmapService.listRoadmap(userName));

    }

    @Override
    public ResponseEntity<CreateRoadmapResponse> getRoadMap(String roadmapId) {
        return ResponseEntity.ok(roadmapService.getRoadMap(roadmapId));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CreateRoadmapResponse> updateRoadmap(String roadmapId, UpdateRoadmapDetail updataRoadmapDetail) {
        return ResponseEntity.ok(roadmapService.updateRoadmap(roadmapId, updataRoadmapDetail));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CreateRoadmapResponse> enableShareLink(String roadmapId) {
        return ResponseEntity.ok(roadmapService.enableShareLink(roadmapId));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CreateRoadmapResponse> disableShareLink(String roadmapId) {
        return ResponseEntity.ok(roadmapService.disableShareLink(roadmapId));
    }
}
