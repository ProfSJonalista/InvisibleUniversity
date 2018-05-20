package invisibleuniversity.domain;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Invention.unsold", query = "SELECT i FROM Invention i WHERE i.sold = false")
})
public class Invention {
    private Long id;
    private String name;
    private boolean deadly;
    private String description;
    private boolean sold = false;

    public Invention() {

    }

    public Invention(String name, String description, boolean deadly) {
        this.name = name;
        this.description = description;
        this.deadly = deadly;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeadly() {
        return this.deadly;
    }

    public void setDeadly(boolean deadly) {
        this.deadly = deadly;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }
}