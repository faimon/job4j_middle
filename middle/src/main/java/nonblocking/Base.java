package nonblocking;

import java.util.Objects;

public class Base {
    private final int id;
    private int version;
    private String desc;

    public Base(int id, int version, String desc) {
        this.id = id;
        this.version = version;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base base = (Base) o;
        return id == base.id &&
                Objects.equals(desc, base.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, desc);
    }
}
