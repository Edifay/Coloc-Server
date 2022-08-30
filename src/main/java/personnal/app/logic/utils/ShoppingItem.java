package personnal.app.logic.utils;

import static personnal.app.logic.Utils.IDgenerator;

public class ShoppingItem {

    protected String name;
    protected String description;

    protected int count;
    protected ItemType type;

    protected String id;

    public ShoppingItem() {

    }

    public ShoppingItem(final String name, final String description, final int count, final ItemType type) {
        this.id = IDgenerator.generate(10);
        this.name = name;
        this.description = description;
        this.count = count;
        this.type = type;
    }

    public ShoppingItem(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void generateId() {
        if (this.id == null)
            this.id = IDgenerator.generate(20);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ShoppingItem trans))
            return false;
        return trans.getId().equals(this.id);
    }

}
