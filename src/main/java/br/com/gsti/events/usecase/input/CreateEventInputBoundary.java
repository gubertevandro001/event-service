package br.com.gsti.events.usecase.input;

import br.com.gsti.events.entity.Event;
import br.com.gsti.events.usecase.model.EventCreatedResponseModel;

public interface CreateEventInputBoundary {

    EventCreatedResponseModel execute(Event toCreate);
}
