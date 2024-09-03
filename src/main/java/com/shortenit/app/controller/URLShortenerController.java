package com.shortenit.app.controller;

import com.shortenit.app.model.CreateShortURLRequest;
import com.shortenit.app.model.CreateShortURLResponse;
import com.shortenit.app.model.ExpandShortURLResponse;
import com.shortenit.app.services.URLShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/url-shortener")
public class URLShortenerController {

  @Autowired
  private final URLShortenerService urlShortenerService;

  public URLShortenerController(URLShortenerService urlShortenerService) {
    this.urlShortenerService = urlShortenerService;
  }

  @Operation(
          summary = "Create a short URL",
          description =
                  "Shortens a given URL and returns the shortened URL along with its validity period.")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "Short URL created successfully"),
                  @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
                  @ApiResponse(responseCode = "409", description = "URL already shortened")
          })
  @PostMapping("/create")
  public ResponseEntity<CreateShortURLResponse> createShortURL(
          @RequestBody CreateShortURLRequest request) {
    CreateShortURLResponse response = urlShortenerService.create(request);
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Expand a short URL",
          description = "Expands a given short URL key and returns the original long URL.")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "Short URL expanded successfully"),
                  @ApiResponse(responseCode = "400", description = "Invalid URL key"),
                  @ApiResponse(responseCode = "404", description = "Short URL not found or expired")
          })
  @GetMapping("/expand/{shortURLKey}")
  public ResponseEntity<ExpandShortURLResponse> expandShortURL(@PathVariable String shortURLKey)
      throws URISyntaxException {
    ExpandShortURLResponse response = urlShortenerService.expand(shortURLKey);
    return ResponseEntity.ok(response);
  }
}