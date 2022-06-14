package in.guidable.services;

import in.guidable.converters.RoadmapConverter;
import in.guidable.entities.Roadmap;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.CreateRoadmapResponse;
import in.guidable.repositories.RoadmapRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CreateRoadmapResponse> listRoadmap() {
         return roadmapRepo
                 .findAll()
                 .stream()
                 .map(RoadmapConverter::toRoadmapResponse)
                 .collect(Collectors.toList());

    }
}
