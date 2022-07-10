package in.guidable.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import in.guidable.constants.TestConstant;
import in.guidable.entities.Journey;
import in.guidable.entities.Roadmap;
import in.guidable.exceptions.RenderableException;
import in.guidable.model.CreateRoadmapDetail;
import in.guidable.model.UpdateRoadmapDetail;
import in.guidable.models.CustomerModel;
import in.guidable.repositories.JourneyRepo;
import in.guidable.repositories.RoadmapRepo;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@SpringBootTest
class RoadmapServiceTest {
  @Autowired private RoadmapService roadmapService;
  @MockBean private JourneyRepo journeyRepo;
  @MockBean private RoadmapRepo roadmapRepo;

  private Roadmap roadmapOnSuccess;
  private CustomerModel customerModel;

  @BeforeEach
  void setUp() {
    customerModel =
        CustomerModel.builder()
            .userName(TestConstant.USER_NAME)
            .userId(TestConstant.USER_ID)
            .build();

    roadmapOnSuccess =
        Roadmap.builder()
            .name(TestConstant.ROADMAP_NAME)
            .description(TestConstant.ROADMAP_DESCRIPTION)
            .originalAuthor(TestConstant.USER_NAME)
            .build();
  }

  @Test
  void testCreateRoadmap_success() {
    CreateRoadmapDetail createRoadmapDetail = new CreateRoadmapDetail();

    Optional<Journey> journey = Optional.ofNullable(mock(Journey.class));

    when(journeyRepo.findByCustomer_IdAndId(any(), any())).thenReturn(journey);
    when(roadmapRepo.save(any())).thenReturn(roadmapOnSuccess);
    Roadmap roadmap = roadmapService.createRoadmap(customerModel, createRoadmapDetail);

    assertAll(
        () -> assertEquals(TestConstant.ROADMAP_NAME, roadmap.getName()),
        () -> assertEquals(TestConstant.ROADMAP_DESCRIPTION, roadmap.getDescription()),
        () -> assertEquals(TestConstant.USER_NAME, roadmap.getOriginalAuthor()));
    verify(journeyRepo, times(1)).findByCustomer_IdAndId(any(), any());
    verify(roadmapRepo, times(1)).save(any());
  }

  @Test
  void testCreateRoadmap_entityNotFoundException() {

    CreateRoadmapDetail createRoadmapDetail =
        new CreateRoadmapDetail().journeyId(TestConstant.JOURNEY_ID);
    when(journeyRepo.findByCustomer_IdAndId(any(), any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            RenderableException.class,
            () -> roadmapService.createRoadmap(customerModel, createRoadmapDetail));

    assertEquals(
        "Journey with id afd351a2-faf4-11ec-b939-0242ac120002, not found or not authorized",
        exception.getMessage());
    verify(journeyRepo, times(1)).findByCustomer_IdAndId(any(), any());
    verify(journeyRepo, never()).save(any());
  }

  @Test
  void testListRoadmap_success() {
    Optional<Journey> journey = Optional.ofNullable(mock(Journey.class));
    when(journeyRepo.findByCustomer_IdAndId(any(), any())).thenReturn(journey);
    Page<Roadmap> page = new PageImpl<>(List.of(roadmapOnSuccess));
    when(roadmapRepo.findAllByJourney(any(), any())).thenReturn(page);

    Page<Roadmap> pageResponse =
        roadmapService.listRoadmap(customerModel, TestConstant.JOURNEY_ID, 1, 0);
    assertEquals(1, pageResponse.getTotalElements());
    verify(journeyRepo, times(1)).findByCustomer_IdAndId(any(), any());
    verify(roadmapRepo, times(1)).findAllByJourney(any(), any());
  }

  @Test
  void testListRoadmap_entityNotFoundException() {

    when(journeyRepo.findByCustomer_IdAndId(any(), any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            RenderableException.class,
            () -> roadmapService.listRoadmap(customerModel, TestConstant.JOURNEY_ID, 1, 0));

    assertEquals(
        "Journey with id afd351a2-faf4-11ec-b939-0242ac120002, not found or not authorized",
        exception.getMessage());
    verify(journeyRepo, times(1)).findByCustomer_IdAndId(any(), any());
    verify(journeyRepo, never()).save(any());
  }

  @Test
  void testGetRoadmap_success() {
    when(roadmapRepo.findByCustomerIdAndId(any(), any())).thenReturn(Optional.of(roadmapOnSuccess));
    Roadmap roadmap = roadmapService.getRoadmap(customerModel, TestConstant.ROADMAP_ID);

    assertAll(
        () -> assertEquals(TestConstant.ROADMAP_NAME, roadmap.getName()),
        () -> assertEquals(TestConstant.ROADMAP_DESCRIPTION, roadmap.getDescription()),
        () -> assertEquals(TestConstant.USER_NAME, roadmap.getOriginalAuthor()));
    verify(roadmapRepo, times(1)).findByCustomerIdAndId(any(), any());
    verifyNoInteractions(journeyRepo);
  }

  @Test
  void testGetRoadmap_entityNotFoundException() {
    when(roadmapRepo.findByCustomerIdAndId(any(), any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            RenderableException.class,
            () -> roadmapService.getRoadmap(customerModel, TestConstant.ROADMAP_ID));
    assertEquals(
        "Roadmap with id 53a04948-faf5-11ec-b939-0242ac120002, not found or not authorized",
        exception.getMessage());
    verify(roadmapRepo, times(1)).findByCustomerIdAndId(any(), any());
    verifyNoInteractions(journeyRepo);
  }

  @Test
  void testUpdateRoadmap_success() {
    UpdateRoadmapDetail updateRoadmapDetail = new UpdateRoadmapDetail();

    Optional<Roadmap> roadmapWhenQueried = Optional.ofNullable(mock(Roadmap.class));

    when(roadmapRepo.findByCustomerIdAndId(any(), any())).thenReturn(roadmapWhenQueried);
    when(roadmapRepo.save(any())).thenReturn(roadmapOnSuccess);
    Roadmap roadmap =
        roadmapService.updateRoadmap(customerModel, TestConstant.ROADMAP_ID, updateRoadmapDetail);

    assertAll(
        () -> assertEquals(TestConstant.ROADMAP_NAME, roadmap.getName()),
        () -> assertEquals(TestConstant.ROADMAP_DESCRIPTION, roadmap.getDescription()),
        () -> assertEquals(TestConstant.USER_NAME, roadmap.getOriginalAuthor()));
    verify(roadmapRepo, times(1)).findByCustomerIdAndId(any(), any());
    verify(roadmapRepo, times(1)).save(any());
    verifyNoInteractions(journeyRepo);
  }

  @Test
  void testUpdateRoadmap_entityNotFoundException() {

    UpdateRoadmapDetail updateRoadmapDetail = new UpdateRoadmapDetail();
    when(roadmapRepo.findByCustomerIdAndId(any(), any())).thenReturn(Optional.empty());
    Exception exception =
        assertThrows(
            RenderableException.class,
            () ->
                roadmapService.updateRoadmap(
                    customerModel, TestConstant.ROADMAP_ID, updateRoadmapDetail));

    assertEquals(
        "Roadmap with id 53a04948-faf5-11ec-b939-0242ac120002, not found or not authorized",
        exception.getMessage());
    verify(roadmapRepo, times(1)).findByCustomerIdAndId(any(), any());
    verify(roadmapRepo, never()).save(any());
    verifyNoInteractions(journeyRepo);
  }

  @Test
  void testDeleteRoadmap_success() {
    when(roadmapRepo.deleteByCustomerIdAndId(any(), any())).thenReturn(1L);
    roadmapService.deleteRoadmap(customerModel, TestConstant.ROADMAP_ID);

    verify(roadmapRepo, times(1)).deleteByCustomerIdAndId(any(), any());
    verifyNoInteractions(journeyRepo);
  }

  @Test
  void testDeleteRoadmap_entityNotFoundException() {
    when(roadmapRepo.deleteByCustomerIdAndId(any(), any())).thenReturn(0L);
    Exception exception =
        assertThrows(
            RenderableException.class,
            () -> roadmapService.deleteRoadmap(customerModel, TestConstant.ROADMAP_ID));
    assertEquals(
        "Roadmap with id 53a04948-faf5-11ec-b939-0242ac120002, not found or not authorized",
        exception.getMessage());
    verify(roadmapRepo, times(1)).deleteByCustomerIdAndId(any(), any());
    verifyNoInteractions(journeyRepo);
  }
}
