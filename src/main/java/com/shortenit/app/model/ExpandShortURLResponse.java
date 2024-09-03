package com.shortenit.app.model;

import java.net.URI;
import java.time.LocalDateTime;

public record ExpandShortURLResponse(URI longURL, LocalDateTime validUntil) {}
