package com.shortenit.app.services;

import com.shortenit.app.model.CreateShortURLRequest;
import com.shortenit.app.model.CreateShortURLResponse;
import com.shortenit.app.model.ExpandShortURLResponse;
import com.shortenit.app.persistence.URLShortened;
import com.shortenit.app.repository.URLShortenerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Service
public class URLShortenerService {

  @Autowired private final URLShortenerRepository urlShortenerRepository;

  public URLShortenerService(URLShortenerRepository urlShortenerRepository) {
    this.urlShortenerRepository = urlShortenerRepository;
  }

  public CreateShortURLResponse create(final CreateShortURLRequest request) {

    if (!urlShortenerRepository.getURLShortenedByUrl(request.url().toString()).isEmpty()) {
      throw new RuntimeException("This URL already shortened");
    }
    final String domainName = "http://localhost:8080/";
    final String identifier = RandomStringUtils.randomAlphanumeric(7);
    final URI shortURL = URI.create(domainName + identifier);
    final LocalDateTime validUntil = LocalDateTime.now().plusDays(3);
    //    final LocalDateTime validUntil = LocalDateTime.now().plusSeconds(60); // **validate to minute**
    final URLShortened urlShortened = new URLShortened();
    urlShortened.setUrl(request.url().toString());
    urlShortened.setKey(identifier);
    urlShortened.setValidUntil(validUntil);
    urlShortenerRepository.save(urlShortened);

    return new CreateShortURLResponse(shortURL, validUntil);
  }

  public ExpandShortURLResponse expand(final String shortURLKey) throws URISyntaxException {

    final URLShortened longURL = urlShortenerRepository.getURLShortenedByKey(shortURLKey);
    LocalDateTime validUntil = longURL.getValidUntil();
    if (validUntil.isBefore(LocalDateTime.now())) {
      throw new RuntimeException("This Short URL has expired");
    }
    final URI response = new URI(longURL.getUrl());
    return new ExpandShortURLResponse(response, validUntil);
  }
}

  //  **********the following code would help to redirect according to URL HTTP response*************

  //  public ExpandShortURLResponse expand(final String shortURLKey) throws URISyntaxException {
  //    // Retrieve the URLShortened object using the key
  //    final URLShortened urlShortened = urlShortenerRepository.getURLShortenedByKey(shortURLKey);
  //
  //    // Check if the URL exists
  //    if (urlShortened == null) {
  //      throw new RuntimeException("Short URL does not exist");
  //    }
  //
  //    // Check if the URL is still valid
  //    LocalDateTime validUntil = urlShortened.getValidUntil();
  //    if (validUntil.isBefore(LocalDateTime.now())) {
  //      throw new RuntimeException("This Short URL has expired");
  //    }
  //
  //    // Convert the stored URL to a URI
  //    final URI longURL = new URI(urlShortened.getUrl());
  //
  //    // Return the expanded URL and its validity
  //    return new ExpandShortURLResponse(longURL, validUntil);
  //  }
  // }