package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Customer;
import in.guidable.entities.Journey;
import in.guidable.entities.Roadmap;
import in.guidable.entities.SharableEntity;
import in.guidable.entities.SharableLinkKeyResourceMap;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.PublicResourceType;
import in.guidable.model.SharableResource;
import in.guidable.repositories.CustomerRepo;
import in.guidable.repositories.JourneyRepo;
import in.guidable.repositories.RoadmapRepo;
import in.guidable.repositories.SharableLinkKeyResourceMapRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SharableResourceService {
    private final CustomerRepo customerRepo;
    private final RoadmapRepo roadmapRepo;
    private final JourneyRepo journeyRepo;
    private final SharableLinkKeyResourceMapRepo sharableLinkKeyResourceMapRepo;

    @Transactional
    public SharableResource enableShareLink(String userName, String resourceId, PublicResourceType resourceType)
    {
        Customer customer = customerRepo
                .findByCustomerUserName(userName)
                .orElseThrow(()-> RenderableExceptionGenerator.generateInvalidUserException(userName));

        SharableEntity sharableEntity = getSharableEntity(customer, resourceId, resourceType);
        if (sharableEntity.getPublicMetadata().getLinkKey()!= null && !sharableEntity.getPublicMetadata().getLinkKey().isEmpty()) {
            sharableEntity.getPublicMetadata().setIsSharable(true);
            sharableLinkKeyResourceMapRepo.changeLinkStatus(sharableEntity.getId(), true);
        } else {
            String linkKey = sharableLinkKeyResourceMapRepo.generateUniqueLinkKey();
            sharableEntity.getPublicMetadata().setLinkKey(linkKey);
            sharableEntity.getPublicMetadata().setIsSharable(true);
            sharableLinkKeyResourceMapRepo.save(SharableLinkKeyResourceMap
                    .builder()
                    .objectType(PublicResourceType.ROADMAP)
                    .customerId(customer.getId())
                    .resourceId(sharableEntity.getId())
                    .isEnabled(sharableEntity.getPublicMetadata().getIsSharable())
                    .linkKey(sharableEntity.getPublicMetadata().getLinkKey())
                    .build());
        }
        sharableEntity = saveSharableEntity(sharableEntity, resourceType);
        return getSharableResourceResponse(sharableEntity, resourceType);

    }
    private SharableEntity getSharableEntity(Customer customer, String resourceId, PublicResourceType resourceType)
    {
        SharableEntity sharableEntity = null;
        switch (resourceType) {
            case ROADMAP:
                sharableEntity =  roadmapRepo
                        .findByIdAndCustomerId(UUID.fromString(resourceId), customer.getId())
                        .orElseThrow(()->
                                RenderableExceptionGenerator
                                .generateEntityNotFoundOrNotAuthorizedException("Roadmap",resourceId));
                break;
            case JOURNEY:
                //TODO
                break;
        }
        return sharableEntity;
    }
    private SharableEntity saveSharableEntity(SharableEntity sharableEntity, PublicResourceType resourceType)
    {
        SharableEntity savedEntity = null;
        switch (resourceType) {
            case ROADMAP:
                savedEntity = roadmapRepo.save((Roadmap) sharableEntity);
                break;
            case JOURNEY:
//                journeyRepo.save((Journey) sharableEntity);
                break;
        }
        return savedEntity;
    }

    private SharableResource getSharableResourceResponse(SharableEntity sharableEntity, PublicResourceType resourceType)
    {
        SharableResource sharableResource = null;
        switch (resourceType) {
            case ROADMAP:
                sharableResource = RoadmapConverter.toRoadmapResponse((Roadmap) sharableEntity);
                break;
            case JOURNEY:
                break;
        }
        return sharableResource;
    }

    @Transactional
    public SharableResource disableShareLink(String userName, String resourceId, PublicResourceType resourceType) {
        Customer customer = customerRepo
                .findByCustomerUserName(userName)
                .orElseThrow(()-> RenderableExceptionGenerator.generateInvalidUserException(userName));
        SharableEntity sharableEntity = getSharableEntity(customer, resourceId, resourceType);
        sharableEntity.getPublicMetadata().setIsSharable(false);

        sharableLinkKeyResourceMapRepo.changeLinkStatus(sharableEntity.getId(), false);
        sharableEntity = saveSharableEntity(sharableEntity, resourceType);
        return getSharableResourceResponse(sharableEntity, resourceType);
    }
}
