package vo;

public class Item {
  String name;
  int price;
  String category; // 카테고리 // 육류 , 과자 , 어류 , 과일 등등

  public Item() {

  }

  public Item(String category) {
    this.category = category;
  }

  public Item(String name, int price, String category) {
    this.name = name;
    this.price = price;
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }

  public String getCategory() {
    return category;
  }

  public String toString() {
    return name+" \t"+price+"원 \t"+category;
  }
}
