package in.guidable.services;

import in.guidable.converters.CheckpointConverter;
import in.guidable.entities.Checkpoints;
import in.guidable.entities.Journey;
import in.guidable.entities.Roadmap;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.CreateCheckpointDetail;
import in.guidable.model.UpdateCheckpointDetail;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.models.CustomerModel;
import in.guidable.repositories.CheckpointRepo;
import in.guidable.repositories.RoadmapRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckpointService {

  private final RoadmapRepo roadmapRepo;
  private final CheckpointRepo checkpointRepo;

  public Checkpoints createCheckpoint(
      CustomerModel customerModel, CreateCheckpointDetail createCheckpointDetail) {

    Roadmap roadmap =
        roadmapRepo
            .findByCustomerIdAndId(customerModel.getUserId(), createCheckpointDetail.getRoadmapId())
            .orElseThrow(
                () ->
                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                        "Roadmap", createCheckpointDetail.getRoadmapId()));

    Checkpoints newCheckpoints =
        CheckpointConverter.toCheckPointEntity(createCheckpointDetail)
            .toBuilder()
            .roadmap(roadmap)
            .customerId(customerModel.getUserId())
            .build();

    return checkpointRepo.save(newCheckpoints);
  }

  public Page<Checkpoints> listCheckpoint(CustomerModel customerModel, UUID roadmapId, Integer limit, Integer page) {
    Roadmap roadmap =
            roadmapRepo
                    .findByCustomerIdAndId(customerModel.getUserId(), roadmapId)
                    .orElseThrow(
                            () ->
                                    RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                                            "Roadmap", roadmapId));
    Pageable pageable = PageRequest.of(page, limit);
    return checkpointRepo.findAllByRoadmap(roadmap, pageable);
  }

  public Checkpoints getCheckpoint(CustomerModel customerModel, UUID checkpointId) {
    return checkpointRepo.findByCustomerIdAndId(customerModel.getUserId(), checkpointId)
            .orElseThrow(
                    () ->
                            RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                                    "Roadmap", checkpointId));
  }

  @Transactional
  public void deleteCheckpoint(CustomerModel customerModel, UUID checkpointId) {
    checkpointRepo.deleteByCustomerIdAndId(customerModel.getUserId(), checkpointId);
  }

  @Transactional
  public Checkpoints updateCheckpoint(
          CustomerModel customerModel, UUID checkpointId, UpdateCheckpointDetail updateCheckpointDetail) {
    Checkpoints checkpoint = getCheckpoint(customerModel, checkpointId);

    checkpoint.setName(updateCheckpointDetail.getName());
    checkpoint.setQuickLink(updateCheckpointDetail.getQuickLink());
    checkpoint.setShortDescription(updateCheckpointDetail.getShortDescription());
    checkpoint.setDetailedDescription(updateCheckpointDetail.getDetailedDescription());

    return checkpointRepo.save(checkpoint);
  }
}
