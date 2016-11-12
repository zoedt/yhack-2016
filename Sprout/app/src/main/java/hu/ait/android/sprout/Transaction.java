package hu.ait.android.sprout;

/**
 * Created by zoetiet on 11/12/16.
 */
public class Transaction {
    private boolean pending;
    private String currency;
    private int id;
    private float amount;
    private String accountId;
    private boolean isBankCc;
    private String category;
    private int date;
    private String name;

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isBankCc() {
        return isBankCc;
    }

    public void setIsBankCc(boolean isBankCc) {
        this.isBankCc = isBankCc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
