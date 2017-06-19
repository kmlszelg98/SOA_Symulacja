package test;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Kamil on 11.06.2017.
 */
@Entity
@Table(name = "place", schema = "parking")
public class Place {
    private int idTable;
    private Integer id;
    private Integer strefa;
    private String wolne;
    private Timestamp time;

    public Place(Integer id, Integer strefa, String wolne, Timestamp time) {
        this.id = id;
        this.strefa = strefa;
        this.wolne = wolne;
        this.time = time;
    }

    public Place() {
    }

    @Id
    @Column(name = "idTable")
    @GeneratedValue
    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    @Basic
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "strefa")
    public Integer getStrefa() {
        return strefa;
    }

    public void setStrefa(Integer strefa) {
        this.strefa = strefa;
    }

    @Basic
    @Column(name = "wolne")
    public String getWolne() {
        return wolne;
    }

    public void setWolne(String wolne) {
        this.wolne = wolne;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place that = (Place) o;

        if (idTable != that.idTable) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (strefa != null ? !strefa.equals(that.strefa) : that.strefa != null) return false;
        if (wolne != null ? !wolne.equals(that.wolne) : that.wolne != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTable;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (strefa != null ? strefa.hashCode() : 0);
        result = 31 * result + (wolne != null ? wolne.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }


    public String toString(){
        return " ID: "+ id +" Strefa: "+ strefa;
    }

}
