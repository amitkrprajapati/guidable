package in.guidable.converters;

import in.guidable.entities.Checkpoints;
import in.guidable.model.CheckpointResponse;
import in.guidable.model.CreateCheckpointDetail;

public class CheckpointConverter {
  public static Checkpoints toCheckPointEntity(CreateCheckpointDetail checkpoints) {
    return in.guidable.entities.Checkpoints.builder()
        .name(checkpoints.getName())
        .shortDescription(checkpoints.getShortDescription())
        .detailedDescription(checkpoints.getDetailedDescription())
        .quickLink(checkpoints.getQuickLink())
        .isComplete(checkpoints.getIsComplete())
        .build();
  }

  public static CheckpointResponse toCheckPointModel(in.guidable.entities.Checkpoints checkpoints) {
    return new CheckpointResponse()
        .id(checkpoints.getId())
        .name(checkpoints.getName())
        .shortDescription(checkpoints.getShortDescription())
        .detailedDescription(checkpoints.getDetailedDescription())
        .quickLink(checkpoints.getQuickLink())
        .isComplete(checkpoints.isComplete());
  }
}
