package in.guidable.controllers;

import in.guidable.api.SharableResourceApi;
import in.guidable.model.PublicResourceType;
import in.guidable.model.SharableResource;
import in.guidable.model.SharableResourceResponse;
import in.guidable.services.JourneyService;
import in.guidable.services.RoadmapService;
import in.guidable.services.SharableResourceService;
import in.guidable.util.AuthenticationUtil;
import in.guidable.util.ValidationUtil;
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
    public ResponseEntity<SharableResourceResponse> enableShareLink(String authorization, String resourceId, PublicResourceType resourceType) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        ValidationUtil.validateId("ResourceId", resourceId);
        SharableResource sharableResource = sharableResourceService.enableShareLink(userName, resourceId, resourceType);

        return ResponseEntity.ok(new SharableResourceResponse()
                .objectType(resourceType)
                .publicResource(sharableResource));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<SharableResourceResponse> disableShareLink(String authorization, String resourceId, PublicResourceType resourceType) {
        String userName= authenticationUtil.getUserFromToken(authorization);
        ValidationUtil.validateId("ResourceId", resourceId);
        SharableResource sharableResource = sharableResourceService.disableShareLink(userName, resourceId, resourceType);

        return ResponseEntity.ok(new SharableResourceResponse()
                .objectType(resourceType)
                .publicResource(sharableResource));
    }
}
