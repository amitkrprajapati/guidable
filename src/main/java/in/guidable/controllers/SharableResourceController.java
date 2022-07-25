package in.guidable.controllers;

import in.guidable.api.SharableResourceApi;
import in.guidable.model.PublicResourceType;
import in.guidable.model.SharableResource;
import in.guidable.model.SharableResourceResponse;
import in.guidable.models.CustomerModel;
import in.guidable.services.SharableResourceService;
import in.guidable.util.AuthenticationUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SharableResourceController implements SharableResourceApi {
  private final AuthenticationUtil authenticationUtil;
  private final SharableResourceService sharableResourceService;
  //    private final JourneyService journeyService;

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<SharableResourceResponse> enableShareLink(
      String authorization, UUID resourceId, PublicResourceType resourceType) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    SharableResource sharableResource =
        sharableResourceService.enableShareLink(customerModel, resourceId, resourceType);

    return ResponseEntity.ok(
        new SharableResourceResponse().objectType(resourceType).publicResource(sharableResource));
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<SharableResourceResponse> disableShareLink(
      String authorization, UUID resourceId, PublicResourceType resourceType) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    SharableResource sharableResource =
        sharableResourceService.disableShareLink(customerModel, resourceId, resourceType);

    return ResponseEntity.ok(
        new SharableResourceResponse().objectType(resourceType).publicResource(sharableResource));
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<SharableResourceResponse> likeResource(
      String authorization, UUID resourceId, PublicResourceType resourceType) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);
    SharableResource sharableResource =
        sharableResourceService.likeResource(customerModel, resourceId, resourceType);

    return ResponseEntity.ok(
        new SharableResourceResponse().objectType(resourceType).publicResource(sharableResource));
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<Void> unlikeResource(
      String authorization, UUID resourceId, PublicResourceType resourceType) {
    CustomerModel customerModel = authenticationUtil.getCustomerModelFromToken(authorization);

    sharableResourceService.unlikeResource(customerModel, resourceId, resourceType);

    return ResponseEntity.noContent().build();
  }
}
