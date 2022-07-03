package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Roadmap;
import in.guidable.entities.SharableEntity;
import in.guidable.entities.SharableLinkKeyResourceMap;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.PublicResourceType;
import in.guidable.model.SharableResource;
import in.guidable.models.CustomerModel;
import in.guidable.repositories.CustomerRepo;
import in.guidable.repositories.JourneyRepo;
import in.guidable.repositories.RoadmapRepo;
import in.guidable.repositories.SharableLinkKeyResourceMapRepo;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharableResourceService {
  private final CustomerRepo customerRepo;
  private final RoadmapRepo roadmapRepo;
  private final JourneyRepo journeyRepo;
  private final SharableLinkKeyResourceMapRepo sharableLinkKeyResourceMapRepo;

  @Transactional
  public SharableResource enableShareLink(
      CustomerModel customerModel, UUID resourceId, PublicResourceType resourceType) {
    SharableEntity sharableEntity = getSharableEntity(customerModel, resourceId, resourceType);
    if (sharableEntity.getPublicMetadata().getLinkKey() != null
        && !sharableEntity.getPublicMetadata().getLinkKey().isEmpty()) {
      sharableEntity.getPublicMetadata().setIsSharable(true);
      sharableLinkKeyResourceMapRepo.changeLinkStatus(sharableEntity.getId(), true);
    } else {
      String linkKey = sharableLinkKeyResourceMapRepo.generateUniqueLinkKey();
      sharableEntity.getPublicMetadata().setLinkKey(linkKey);
      sharableEntity.getPublicMetadata().setIsSharable(true);
      sharableLinkKeyResourceMapRepo.save(
          SharableLinkKeyResourceMap.builder()
              .objectType(PublicResourceType.ROADMAP)
              .customerId(customerModel.getUserId())
              .resourceId(sharableEntity.getId())
              .isEnabled(sharableEntity.getPublicMetadata().getIsSharable())
              .linkKey(sharableEntity.getPublicMetadata().getLinkKey())
              .build());
    }
    sharableEntity = saveSharableEntity(sharableEntity, resourceType);
    return getSharableResourceResponse(sharableEntity, resourceType);
  }

  private SharableEntity getSharableEntity(
      CustomerModel customerModel, UUID resourceId, PublicResourceType resourceType) {
    SharableEntity sharableEntity = null;
    switch (resourceType) {
      case ROADMAP:
        sharableEntity =
            roadmapRepo
                .findByCustomerIdAndId(customerModel.getUserId(), resourceId)
                .orElseThrow(
                    () ->
                        RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException(
                            "Roadmap", resourceId));
        break;
      case JOURNEY:
        // TODO
        break;

      default:
        throw RenderableExceptionGenerator.generateInternalServerError();
    }
    return sharableEntity;
  }

  private SharableEntity saveSharableEntity(
      SharableEntity sharableEntity, PublicResourceType resourceType) {
    SharableEntity savedEntity = null;
    switch (resourceType) {
      case ROADMAP:
        savedEntity = roadmapRepo.save((Roadmap) sharableEntity);
        break;
      case JOURNEY:
        //                journeyRepo.save((Journey) sharableEntity);
        break;
      default:
        throw RenderableExceptionGenerator.generateInternalServerError();
    }
    return savedEntity;
  }

  private SharableResource getSharableResourceResponse(
      SharableEntity sharableEntity, PublicResourceType resourceType) {
    SharableResource sharableResource = null;
    switch (resourceType) {
      case ROADMAP:
        sharableResource = RoadmapConverter.toRoadmapResponse((Roadmap) sharableEntity);
        break;
      case JOURNEY:
        break;
      default:
        throw RenderableExceptionGenerator.generateInternalServerError();
    }
    return sharableResource;
  }

  @Transactional
  public SharableResource disableShareLink(
      CustomerModel customerModel, UUID resourceId, PublicResourceType resourceType) {
    SharableEntity sharableEntity = getSharableEntity(customerModel, resourceId, resourceType);
    sharableEntity.getPublicMetadata().setIsSharable(false);

    sharableLinkKeyResourceMapRepo.changeLinkStatus(sharableEntity.getId(), false);
    sharableEntity = saveSharableEntity(sharableEntity, resourceType);
    return getSharableResourceResponse(sharableEntity, resourceType);
  }
}
