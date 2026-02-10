package br.com.gsti.events.usecase.input.impl;

import br.com.gsti.events.entity.Event;
import br.com.gsti.events.entity.Session;
import br.com.gsti.events.usecase.gateway.EventGateway;
import br.com.gsti.events.usecase.gateway.RoomGateway;
import br.com.gsti.events.usecase.gateway.TicketGateway;
import br.com.gsti.events.usecase.input.CreateEventInputBoundary;
import br.com.gsti.events.usecase.model.EventCreatedResponseModel;
import br.com.gsti.events.usecase.model.RoomModel;
import br.com.gsti.events.usecase.model.TicketModel;
import br.com.gsti.events.usecase.presenter.EventCreatedPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateEventInteractor implements CreateEventInputBoundary {

    private final RoomGateway roomGateway;
    private final TicketGateway ticketGateway;
    private final EventCreatedPresenter eventCreatedPresenter;
    private final EventGateway eventGateway;

    @Override
    public EventCreatedResponseModel execute(Event toCreate) {
        if (!toCreate.isValid()) {
            eventCreatedPresenter.prepareFailView("Verifique que todas as informações do evento estão preenchidas.");
        }

        boolean areValidSessionsDateRange = toCreate.areValidSessionsDateRange();

        if (!areValidSessionsDateRange) {
            eventCreatedPresenter.prepareFailView("Verifique se todas as sessões estão dentro da vigência do evento.");
        }

        for (Session session : toCreate.getSessions()) {

            RoomModel roomModel = roomGateway.findBy(session.getRoomId(), session.getStartDateTime());

            if (!roomModel.exists())
                eventCreatedPresenter.prepareFailView("A sala '" + session.getRoomId() + "' não existe.");

            if (!roomModel.isAvailable())
                eventCreatedPresenter.prepareFailView("A sala '" + roomModel.name() + "' não está disponível na data/hora da sessão.");

            boolean allocated = roomGateway.allocate(session.getRoomId(), session.getStartDateTime(), session.getEndDateTime());

            if (!allocated)
                eventCreatedPresenter.prepareFailView("Não foi possível alocar a sala '"
                        + roomModel.name() + "' na sessão '" + session.getName() + "' no intervalo de '"
                        + session.getStartDateTime() + " " + session.getEndDateTime() + "'");

            Integer totalTicketsForSession = 0;

            for (Map.Entry<Integer, Integer> ticketEntry : session.getTicketTypeIdsByQtd().entrySet()) {
                TicketModel ticketModel = ticketGateway.findBy(ticketEntry.getKey());

                if (!ticketModel.exists())
                    eventCreatedPresenter.prepareFailView("O tipo de ingresso '" + ticketEntry.getKey() + "' não existe.");

                Integer ticketQuantity = ticketEntry.getValue();
                totalTicketsForSession += ticketQuantity;
            }

            Integer roomSeats = roomModel.totalSeats();
            if (!(roomSeats >= totalTicketsForSession)) {
                eventCreatedPresenter.prepareFailView("A sala '" + roomModel.name() + "' com '"
                        + roomSeats + "' cadeiras" + " não comporta o exigido de '" + totalTicketsForSession
                        + "' para a sessão '" + session.getName() + "'");
            }
        }
        boolean eventAlreadyExists = eventGateway.exists(toCreate.getName());

        if (eventAlreadyExists) {
            eventCreatedPresenter.prepareFailView("Já existe um evento cadastrado com o nome '" + toCreate.getName() + "'");
        }

        Integer createdId = eventGateway.create(toCreate);
        toCreate.setId(createdId);

        return eventCreatedPresenter.prepareSuccessView(toCreate);
    }
}
