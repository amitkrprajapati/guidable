package in.guidable.controllers;

import in.guidable.api.CheckpointsApi;
import in.guidable.converters.CheckpointConverter;
import in.guidable.entities.Checkpoints;
import in.guidable.model.CheckpointResponse;
import in.guidable.model.CreateCheckpointDetail;
import in.guidable.model.UpdateCheckpointDetail;
import in.guidable.models.CustomerModel;
import in.guidable.services.CheckpointService;
import in.guidable.util.AuthenticationUtil;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<List<CheckpointResponse>> listCheckpoint(
      String authorization, UUID roadmapId, Integer limit, Integer page) {
    return CheckpointsApi.super.listCheckpoint(authorization, roadmapId, limit, page);
  }

  @Override
  public ResponseEntity<Void> deleteCheckpoint(String authorization, UUID checkpointId) {
    return CheckpointsApi.super.deleteCheckpoint(authorization, checkpointId);
  }

  @Override
  public ResponseEntity<CheckpointResponse> getCheckpoint(String authorization, UUID checkpointId) {
    return CheckpointsApi.super.getCheckpoint(authorization, checkpointId);
  }

  @Override
  public ResponseEntity<CheckpointResponse> updateCheckpoint(
      String authorization, UUID checkpointId, UpdateCheckpointDetail updateCheckpointDetail) {
    return CheckpointsApi.super.updateCheckpoint(
        authorization, checkpointId, updateCheckpointDetail);
  }
}
