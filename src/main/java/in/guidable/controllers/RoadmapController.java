package in.guidable.controllers;

import in.guidable.api.RoadmapsApi;
import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Roadmap;
import in.guidable.event.services.RoadmapService;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.RoadmapResponse;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.models.CustomerModel;
import in.guidable.util.AuthenticationUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoadmapController implements RoadmapsApi {

  private final RoadmapService roadmapService;
  private final AuthenticationUtil authenticationUtil;

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<RoadmapResponse> createRoadmap(
      String authorization, CreateRoadmapDetail createRoadmapDetail) {

    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    Roadmap roadmap = roadmapService.createRoadmap(customerModel, createRoadmapDetail);
    return new ResponseEntity<>(RoadmapConverter.toRoadmapResponse(roadmap), HttpStatus.CREATED);
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<List<RoadmapResponse>> listRoadmap(
      String authorization, UUID journeyId, Integer limit, Integer page) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    Page<Roadmap> roadmapList = roadmapService.listRoadmap(customerModel, journeyId, limit, page);
    return ResponseEntity.ok(
        roadmapList.stream().map(RoadmapConverter::toRoadmapResponse).collect(Collectors.toList()));
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<RoadmapResponse> getRoadmap(String authorization, UUID roadmapId) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    Roadmap roadmap = roadmapService.getRoadmap(customerModel, roadmapId);
    return ResponseEntity.ok(RoadmapConverter.toRoadmapResponse(roadmap));
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<RoadmapResponse> updateRoadmap(
      String authorization, UUID roadmapId, UpdateRoadmapDetail updateRoadmapDetail) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    ;
    Roadmap roadmap = roadmapService.updateRoadmap(customerModel, roadmapId, updateRoadmapDetail);
    return ResponseEntity.ok(RoadmapConverter.toRoadmapResponse(roadmap));
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<Void> deleteRoadmap(String authorization, UUID roadmapId) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    roadmapService.deleteRoadmap(customerModel, roadmapId);
    return ResponseEntity.noContent().build();
  }
}
