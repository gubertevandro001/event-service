package br.com.gsti.events.usecase.model;

public record RoomModel(String name, Integer totalSeats, Boolean exists, Boolean isAvailable) {
}
