package br.com.gsti.events.entity;

import br.com.gsti.events.entity.valueobject.Validity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Integer id;
    private String name;
    private Validity validity;
    private List<Session> sessions;

    public boolean areValidSessionsDateRange() {
        for (Session s : this.sessions) {
            if (s.getDateTime()
                    .isAfter(this.validity.endDate()) ||
                    s.getDateTime()
                            .isBefore(this.validity.startDate())) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        return name != null && !name.isEmpty()
                && validity != null && validity.startDate() != null
                && validity.endDate() != null
                && sessions != null && !sessions.isEmpty()
                && sessions.stream().allMatch(Session::isValid);
    }
}