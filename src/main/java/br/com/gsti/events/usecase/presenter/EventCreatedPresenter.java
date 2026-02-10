package br.com.gsti.events.usecase.presenter;

import br.com.gsti.events.entity.Event;
import br.com.gsti.events.usecase.model.EventCreatedResponseModel;

public interface EventCreatedPresenter {

    EventCreatedResponseModel prepareSuccessView(Event event);

    void prepareFailView(String error);
}