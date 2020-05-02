package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @Lob
    @Getter @Setter
    private byte[] E;
    @Getter @Setter
    private String F;
    @Getter @Setter
    private String G;
    @Getter @Setter
    private boolean H;
    @Getter @Setter
    private boolean I;
    @Getter @Setter
    private String J;

}
