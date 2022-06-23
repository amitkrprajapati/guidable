package in.guidable.converters;

public class CheckpointConverter {
    public static in.guidable.entities.Checkpoints toCheckPointEntity(in.guidable.model.Checkpoints checkpoints)
    {
        return in.guidable.entities.Checkpoints.builder()
                .name(checkpoints.getName())
                .shortDescription(checkpoints.getShortDescription())
                .detailedDescription(checkpoints.getDetailedDescription())
                .quickLink(checkpoints.getQuickLink())
                .isComplete(checkpoints.getIsComplete())
                .build();
    }

    public static in.guidable.model.Checkpoints toCheckPointModel(in.guidable.entities.Checkpoints checkpoints)
    {
        return  new in.guidable.model.Checkpoints()
                .checkpointKey(checkpoints.getId().toString())
                .name(checkpoints.getName())
                .shortDescription(checkpoints.getShortDescription())
                .detailedDescription(checkpoints.getDetailedDescription())
                .quickLink(checkpoints.getQuickLink())
                .isComplete(checkpoints.isComplete());
    }
}
