package invisibleUniveristy.invention;

public class Invention{
    private int id;
    private String name;
    private boolean deadly;
    private String description;

    public Invention(){

    }

    public Invention(String name, String description, boolean deadly){
        this.name = name;
        this.description = description;
        this.deadly = deadly;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean isDeadly(){
        return this.deadly;
    }

    public void setDeadly(boolean deadly){
        this.deadly = deadly;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}