package br.com.gsti.events.usecase.gateway;

import br.com.gsti.events.usecase.model.RoomModel;

import java.time.LocalDateTime;

public interface RoomGateway {

    RoomModel findBy(Integer id, LocalDateTime requestUseDateTime);

    boolean allocate(Integer roomId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
