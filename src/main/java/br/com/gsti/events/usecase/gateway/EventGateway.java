package br.com.gsti.events.usecase.gateway;

import br.com.gsti.events.entity.Event;
import br.com.gsti.events.usecase.model.EventResponseModel;

import java.util.List;

public interface EventGateway {

    Integer create(Event toCreate);

    boolean exists(String name);

    List<EventResponseModel> getAll();

    EventResponseModel getById(Integer eventId);

    void delete(Integer eventId);
}
