package fragmentexample.rahul.com.brandreseller;

public class Item {

    public enum Type{
        BRAND,
        PRODUCT,
        NEW
    }

    private String itemId;
    private String itemName;
    private String itemUrl;
    private Type type;

    Item(String itemName, String itemId, String itemUrl, Type type) {
        this.itemName = itemName;
        this.itemUrl = itemUrl;
        this.itemId = itemId;
        this.type = type;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public Type getItemType() {
        return type;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemType(Type itemType) {
        this.type = itemType;
    }
}