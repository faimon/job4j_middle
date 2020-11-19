package hibernate.auto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "automodels")
public class AutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static AutoModel of(String name) {
        AutoModel autoModel = new AutoModel();
        autoModel.name = name;
        return autoModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutoModel autoModel = (AutoModel) o;
        return id == autoModel.id &&
                Objects.equals(name, autoModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
