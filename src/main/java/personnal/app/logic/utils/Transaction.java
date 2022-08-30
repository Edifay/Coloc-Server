package personnal.app.logic.utils;

import static personnal.app.logic.Utils.IDgenerator;

public class Transaction {


    protected String object;
    protected Person actor;
    protected Person recipient;

    protected float amount;

    protected String id;
    protected Long creationDate;

    public Transaction() {
    }

    public Transaction(String id) {
        this.id = id;
    }

    public Transaction(final String object, final float amount, final Person actor, final Person recipient) {
        this.object = object;
        this.amount = amount;
        this.actor = actor;
        this.recipient = recipient;
        this.id = IDgenerator.generate(20);
        this.creationDate = System.currentTimeMillis();
    }

    public float getAmount() {
        return amount;
    }


    public Person getActor() {
        return actor;
    }

    public String getObject() {
        return object;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setActor(Person actor) {
        this.actor = actor;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public String getId() {
        return id;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public void generateId() {
        if (this.id == null)
            this.id = IDgenerator.generate(20);
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Transaction trans))
            return false;
        return trans.getId().equals(this.id);
    }
}

