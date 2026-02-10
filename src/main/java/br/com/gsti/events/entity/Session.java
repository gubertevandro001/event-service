package br.com.gsti.events.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private Integer id;
    private String name;
    private LocalDateTime dateTime;
    private Integer roomId;
    private Map<Integer, Integer> ticketTypeIdsByQtd;

    public boolean isValid() {
        return name != null
                && !name.isEmpty()
                && dateTime != null
                && roomId != null
                && ticketTypeIdsByQtd != null
                && !ticketTypeIdsByQtd.isEmpty();
    }
}

