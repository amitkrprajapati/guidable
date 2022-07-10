package in.guidable.services;

import in.guidable.converters.CheckpointConverter;
import in.guidable.entities.Checkpoints;
import in.guidable.entities.Roadmap;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.CreateCheckpointDetail;
import in.guidable.models.CustomerModel;
import in.guidable.repositories.CheckpointRepo;
import in.guidable.repositories.RoadmapRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
