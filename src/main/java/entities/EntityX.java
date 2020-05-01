package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "X")
public class EntityX {

    public EntityX() {
    }

    @Id
    @GeneratedValue
    private int id;

    @Getter @Setter
    private String A;
    @Getter @Setter
    private String B;
    @Getter @Setter
    private String C;
    @Getter @Setter
    private String D;
    @Getter @Setter
    private String E;
    @Getter @Setter
    private String F;
    @Getter @Setter
    private String G;
    @Getter @Setter
    private String H;
    @Getter @Setter
    private String I;
    @Getter @Setter
    private String J;

}
