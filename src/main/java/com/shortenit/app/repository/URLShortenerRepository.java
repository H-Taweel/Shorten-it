package com.shortenit.app.repository;

import com.shortenit.app.persistence.URLShortened;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface URLShortenerRepository extends JpaRepository<URLShortened, Long> {
  URLShortened getURLShortenedByKey(String key);

  List<URLShortened> getURLShortenedByUrl(String url);
}
