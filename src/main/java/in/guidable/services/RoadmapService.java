package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Roadmap;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.repositories.RoadmapRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoadmapService {

    private RoadmapRepo roadmapRepo;

    @Autowired
    public RoadmapService(RoadmapRepo roadmapRepo) {
        this.roadmapRepo = roadmapRepo;
    }

    public CreateRoadmapResponse createRoadmap(CreateRoadmapDetail createRoadmapDetail)
    {
        Roadmap roadmap =  roadmapRepo.save(RoadmapConverter.toRoadmapEntity(createRoadmapDetail));
        return RoadmapConverter.toRoadmapResponse(roadmap);
    }
}
