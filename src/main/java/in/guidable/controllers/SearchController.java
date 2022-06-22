package in.guidable.controllers;

import in.guidable.api.SearchApi;
import in.guidable.model.SharableResourceResponse;
import in.guidable.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@RestController
public class SearchController implements SearchApi {

    private final SearchService searchService;

    @Override
    public ResponseEntity<SharableResourceResponse> findByLinkKey(String linkKey) {
        try {
            SharableResourceResponse sharableResourceResponse = searchService.findByLinkKey(linkKey);
            return ResponseEntity.ok(sharableResourceResponse);
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }

    }
}
