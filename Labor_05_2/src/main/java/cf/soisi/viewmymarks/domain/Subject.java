package cf.soisi.viewmymarks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
