package invisibleuniversity.domain;

public class Creator {
    private Long id;
    private String name;
    private String surname;

    public Long getId() {
        return id;
    }

    public Creator(){

    }

    public Creator(Long id, String name, String surname){
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Creator(String name, String surname){
        this.name = name;
        this.surname = surname;
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

    @Override
    public boolean equals(Object o){
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
