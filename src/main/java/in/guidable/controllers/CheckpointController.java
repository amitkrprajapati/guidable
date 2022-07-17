package in.guidable.controllers;

import in.guidable.api.CheckpointsApi;
import in.guidable.converters.CheckpointConverter;
import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Checkpoints;
import in.guidable.entities.Roadmap;
import in.guidable.model.CheckpointResponse;
import in.guidable.model.CreateCheckpointDetail;
import in.guidable.model.UpdateCheckpointDetail;
import in.guidable.models.CustomerModel;
import in.guidable.services.CheckpointService;
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
public class CheckpointController implements CheckpointsApi {

  private final AuthenticationUtil authenticationUtil;
  private final CheckpointService checkpointService;

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<CheckpointResponse> createCheckpoint(
      String authorization, CreateCheckpointDetail createCheckpointDetail) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    Checkpoints checkpoints =
        checkpointService.createCheckpoint(customerModel, createCheckpointDetail);
    return new ResponseEntity<>(
        CheckpointConverter.toCheckPointModel(checkpoints), HttpStatus.CREATED);
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<List<CheckpointResponse>> listCheckpoint(
      String authorization, UUID roadmapId, Integer limit, Integer page) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    Page<Checkpoints> checkpointList = checkpointService.listCheckpoint(customerModel, roadmapId, limit, page);
    return ResponseEntity.ok(
            checkpointList.stream().map(checkpoint -> CheckpointConverter.toCheckPointModel(checkpoint)).collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<Void> deleteCheckpoint(String authorization, UUID checkpointId) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    checkpointService.deleteCheckpoint(customerModel, checkpointId);
    return ResponseEntity.noContent().build();
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<CheckpointResponse> getCheckpoint(String authorization, UUID checkpointId) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    Checkpoints checkpoint = checkpointService.getCheckpoint(customerModel, checkpointId);
    return ResponseEntity.ok(CheckpointConverter.toCheckPointModel(checkpoint));
  }

  @Override
  public ResponseEntity<CheckpointResponse> updateCheckpoint(
      String authorization, UUID checkpointId, UpdateCheckpointDetail updateCheckpointDetail) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    ;
    Checkpoints checkpoint = checkpointService.updateCheckpoint(customerModel, checkpointId, updateCheckpointDetail);
    return ResponseEntity.ok(CheckpointConverter.toCheckPointModel(checkpoint));

  }
}
