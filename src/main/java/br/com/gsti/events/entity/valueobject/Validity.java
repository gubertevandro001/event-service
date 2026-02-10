package br.com.gsti.events.entity.valueobject;

import java.time.LocalDateTime;

public record Validity(LocalDateTime startDate, LocalDateTime endDate) { }
