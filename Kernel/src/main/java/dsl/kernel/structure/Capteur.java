package dsl.kernel.structure;

import dsl.kernel.NamedElement;
import dsl.kernel.definition.Comportement;
import dsl.kernel.definition.Tuple;
import java.util.List;

/**
 * Cette classe représente un ensemble (1 a n) de capteurs qui utiliseront la
 * même loi.
 *
 * @author Maxime
 */
public class Capteur implements NamedElement {

    private String name;
    private Comportement loi;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the loi
     */
    public Comportement getLoi() {
        return loi;
    }

    /**
     * @param loi the loi to set
     */
    public void setLoi(Comportement loi) {
        this.loi = loi;
    }

    public List<Tuple> ggenerationDonnees() {
        return loi.createData();
    }

}
