package hibernate.auto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "autoBrands")
public class AutoBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "autoBrand")
    private List<AutoModel> models = new ArrayList<>();

    public static AutoBrand of(String name) {
        AutoBrand autoBrand = new AutoBrand();
        autoBrand.name = name;
        return autoBrand;
    }

    public void addAutoModel(AutoModel autoModel) {
        models.add(autoModel);
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

    public List<AutoModel> getModels() {
        return models;
    }

    public void setModels(List<AutoModel> models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutoBrand autoBrand = (AutoBrand) o;
        return id == autoBrand.id &&
                Objects.equals(name, autoBrand.name) &&
                Objects.equals(models, autoBrand.models);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, models);
    }

    @Override
    public String toString() {
        return "AutoBrand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
