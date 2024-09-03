package com.shortenit.app.model;

import java.net.URI;
import java.time.LocalDateTime;

public record CreateShortURLResponse(URI shortURL, LocalDateTime validUntil) {}
