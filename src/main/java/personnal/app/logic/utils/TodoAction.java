package personnal.app.logic.utils;

import static personnal.app.logic.Utils.IDgenerator;

public class TodoAction {

    protected String name;
    protected String description;

    protected Priority priority;

    protected String id;

    public TodoAction() {
    }

    public TodoAction(final String id) {
        this.id = id;
    }

    public TodoAction(final String name, final String description, final Priority priority) {
        this.id = IDgenerator.generate(15);
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void generateId() {
        if (this.id == null)
            this.id = IDgenerator.generate(20);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TodoAction trans))
            return false;
        return trans.getId().equals(this.id);
    }


}
