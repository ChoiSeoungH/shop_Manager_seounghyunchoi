package vo;

public class Cart {
  private String userId; // 구입한 유저 id
  private String itemName; // 구입한 아이템

  public Cart() {
  }

  public Cart(String userId, String itemName) {
    this.userId = userId;
    this.itemName = itemName;
  }

  public String getUserId() {
    return userId;
  }

  public String getItemName() {
    return itemName;
  }

  public String toString() {
    return userId+" \t"+itemName;
  }
}
