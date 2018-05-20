package invisibleuniversity.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Creator.findAll", query = "SELECT c FROM Creator c"),
        @NamedQuery(name = "Creator.findByNickname", query = "SELECT c FROM Creator c WHERE c.nickname = :nickname")
})
public class Creator {
    private Long id;
    private String name;
    private String surname;
    private String nickname;
    private List<Invention> inventions;

    public Creator() {

    }

    public Creator(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Creator(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Creator(String name, String surname, String nickname) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(unique = true, nullable = false)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Invention> getInventions() {
        return inventions;
    }

    public void setInventions(List<Invention> inventions) {
        this.inventions = inventions;
    }

    @Override
    public boolean equals(Object o) {
        Creator creator = (Creator) o;
        boolean ret = creator.getName().equals(this.name) &&
                creator.getSurname().equals(this.surname) &&
                (creator.getId() == this.getId());

        return ret;
    }

    @Override
    public String toString() {
        return this.id + " " + this.name + " " + this.surname;
    }
}
