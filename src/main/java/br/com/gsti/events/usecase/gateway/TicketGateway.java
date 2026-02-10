package br.com.gsti.events.usecase.gateway;

import br.com.gsti.events.usecase.model.TicketModel;

public interface TicketGateway {

    TicketModel findBy(Integer id);
}
