import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

public class CsvX {

    @CsvBindByName @Getter @Setter
    private String A;
    @CsvBindByName @Getter @Setter
    private String B;
    @CsvBindByName @Getter @Setter
    private String C;
    @CsvBindByName @Getter @Setter
    private String D;
    @CsvBindByName @Getter @Setter
    private String E;
    @CsvBindByName @Getter @Setter
    private String F;
    @CsvBindByName @Getter @Setter
    private String G;
    @CsvBindByName @Getter @Setter
    private String H;
    @CsvBindByName @Getter @Setter
    private String I;
    @CsvBindByName @Getter @Setter
    private String J;

    public boolean verifyEmptyFields(){
        if (this.A.equals(""))
            return true;
        if (this.B.equals(""))
            return true;
        if (this.C.equals(""))
            return true;
        if (this.D.equals(""))
            return true;
        if (this.E.equals(""))
            return true;
        if (this.F.equals(""))
            return true;
        if (this.G.equals(""))
            return true;
        if (this.H.equals(""))
            return true;
        if (this.I.equals(""))
            return true;
        if (this.J.equals(""))
            return true;
        return false;
    }
}
