package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Customer;
import in.guidable.entities.Journey;
import in.guidable.entities.Roadmap;
import in.guidable.entities.SharableLinkKeyResourceMap;
import in.guidable.exceptions.RenderableExceptionGenerator;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.PublicResourceType;
import in.guidable.model.RoadmapResponse;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.repositories.CustomerRepo;
import in.guidable.repositories.JourneyRepo;
import in.guidable.repositories.RoadmapRepo;
import in.guidable.repositories.SharableLinkKeyResourceMapRepo;
import in.guidable.repositories.SharableResourceLikeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoadmapService {
    private final CustomerRepo customerRepo;
    private final RoadmapRepo roadmapRepo;
    private final JourneyRepo journeyRepo;
    //    private final SharableResourceViewRepo sharableResourceViewRepo;
    private final SharableResourceLikeRepo sharableResourceLikeRepo;
    private final SharableLinkKeyResourceMapRepo sharableLinkKeyResourceMapRepo;

    public Roadmap createRoadmap(String userName, CreateRoadmapDetail createRoadmapDetail) {
        Customer customer = customerRepo
                .findByCustomerUserName(userName)
                .orElseThrow(()-> RenderableExceptionGenerator.generateInvalidUserException(userName));

        Journey journey = journeyRepo
                .findByIdAndCustomer(UUID.fromString(createRoadmapDetail.getJourneyId()), customer)
                .orElseThrow(()->RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException("Journey", createRoadmapDetail.getJourneyId()));

        Roadmap newRoadmap = RoadmapConverter.toRoadmapEntity(createRoadmapDetail)
                .toBuilder()
                .journey(journey)
                .customer(customer)
                .originalAuthor(customer.getCustomerUserName())
                .updatedBy(customer.getCustomerUserName())
                .build();
        return roadmapRepo.save(newRoadmap);
    }

    public Page<Roadmap> listRoadmap(String userName, String journeyId, Integer limit, Integer page) {

        Customer customer = customerRepo
                .findByCustomerUserName(userName)
                .orElseThrow(()-> RenderableExceptionGenerator.generateInvalidUserException(userName));
        Journey journey = journeyRepo
                .findByIdAndCustomer(UUID.fromString(journeyId), customer)
                .orElseThrow(()->RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException("Journey", journeyId));
        Pageable pageable = PageRequest.of(page, limit);
        return roadmapRepo.findAllByJourney(journey, pageable);

    }

    public Roadmap getRoadmap(String userName, String roadmapId) {
        Customer customer = customerRepo
                .findByCustomerUserName(userName)
                .orElseThrow(()-> RenderableExceptionGenerator.generateInvalidUserException(userName));

        return roadmapRepo
                .findByIdAndCustomer(UUID.fromString(roadmapId), customer)
                .orElseThrow(()->RenderableExceptionGenerator.generateEntityNotFoundOrNotAuthorizedException("Roadmap", roadmapId));
    }

    @Transactional
    public Roadmap updateRoadmap(String userName, String roadmapId, UpdateRoadmapDetail updateRoadmapDetail) {
        Roadmap roadmap = getRoadmap(userName, roadmapId);

        roadmap.setUpdatedBy(userName);
        roadmap.setName(updateRoadmapDetail.getName());
        roadmap.setDescription(updateRoadmapDetail.getDescription());
        return roadmapRepo.save(roadmap);
    }

    @Transactional
    public void deleteRoadmap(String userName, String roadmapId) {
        Customer customer = customerRepo
                .findByCustomerUserName(userName)
                .orElseThrow(()-> RenderableExceptionGenerator.generateInvalidUserException(userName));
        roadmapRepo.deleteByIdAndCustomer(UUID.fromString(roadmapId), customer);
    }
    @Transactional
    public RoadmapResponse enableShareLink(String roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(UUID.fromString(roadmapId)).orElseThrow(EntityNotFoundException::new);
        if (roadmap.getPublicMetadata().getLinkKey() != null) {
            roadmap.getPublicMetadata().setIsSharable(true);
            sharableLinkKeyResourceMapRepo.changeLinkStatus(roadmap.getId().toString(), true);
        } else {
            try {
                String linkKey = sharableLinkKeyResourceMapRepo.generateUniqueLinkKey();
                roadmap.getPublicMetadata().setLinkKey(linkKey);
                roadmap.getPublicMetadata().setIsSharable(true);
                sharableLinkKeyResourceMapRepo.save(SharableLinkKeyResourceMap
                        .builder()
                        .objectType(PublicResourceType.ROADMAP)
                        .resourceId(roadmap.getId())
                        .isEnabled(roadmap.getPublicMetadata().getIsSharable())
//                        .customerId("not-defined-placeholder")
                        .linkKey(roadmap.getPublicMetadata().getLinkKey())
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }

    @Transactional
    public RoadmapResponse disableShareLink(String roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(UUID.fromString(roadmapId)).orElseThrow(EntityNotFoundException::new);
        roadmap.getPublicMetadata().setIsSharable(false);
        sharableLinkKeyResourceMapRepo.changeLinkStatus(roadmap.getId().toString(), false);
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }


}
