package dao;

import Utils.InputManger;
import vo.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ItemDAO {
  private final ArrayList<ArrayList<Item>> itemList;

  public ItemDAO() {
    itemList = new ArrayList<>();
  }

  public ArrayList<ArrayList<Item>> getItemList() {
    return itemList;
  }

  public void addItemFromData(String itemData) {
    String[] temp = itemData.split("\n");
    int idx=0;
    for (String s : temp) {
      String[] info = s.split(",");
      itemList.add(new ArrayList<>());
      for (String s2 : info[1].split("_")) {
        String[] info2 = s2.split("/");
        itemList.get(idx).add(new Item(info2[0], Integer.parseInt(info2[1]),info[0]));
      }
      idx++;
    }
  }

  public String getDataFromList() {
    String data = "";
    for (ArrayList<Item> i1: itemList) {
      data += i1.get(0).getCategory()+",";
      for (Item item : i1) {
        if (i1.get(0).getCategory().equals(item.getCategory())) {
          data+= item.getName()+"/"+item.getPrice()+"/"+"_" ;
        }
      }
      data = data.substring(0, data.length() - 1);
      data += "\n";
    }
    return data;
  }
  public void buyItem(String log, UserDAO udao) {
    printCategory();
    String item = InputManger.getValue("구매할 상품 >> ");
    if (hasItem(item)==-1) {
      System.out.println("상품명을 확인해주세요");
      return;
    }
    udao.addItemtoMyCart(log,item);
    System.out.println("구매완료");

  }

  public int hasItem(String item) {
    int idx=0;
    for (ArrayList<Item> i : itemList) {
      for (Item item1 : i) {
        if (item1.getName().equals(item)) {
          return idx;
        }
      }
      idx+=1;
    }
    return -1;
  }

  public void deleteCategory() {
    String name = InputManger.getValue("삭제할 카테고리 >> ");
    if (hasCategory(name)==-1) {
      System.out.println("카테고리가 존재하지 않습니다.");
      return;
    }
    for (int i = itemList.size()-1; i >=0; i--) {
      if (itemList.get(i).get(0).getCategory().equals(name)) {
        itemList.remove(i);
      }
    }
    System.out.println("카테고리 삭제완료");
  }

  private int hasCategory(String name) {
    int idx=0;
    for (ArrayList<Item> i : itemList) {
      if (i.get(0).getCategory().equals(name)) {
        return idx;
      }
      idx+=1;
    }
    return -1;
  }

  public void addItem() {
    String item = InputManger.getValue("추가할 상품 >> ");
    if (hasItem(item)!=-1) {
      System.out.println("이미 존재하는 상품입니다.");
      return;
    }
    int price = 0;
    while (true) {
      try {
        price = Integer.parseInt(InputManger.getValue("가격 >> "));
      } catch (NumberFormatException e) {
        System.out.println("정수값을 입력하세요");
        continue;
      }
      break;
    }
    String category = InputManger.getValue("카테고리 >> ");
    int categoryIdx = hasCategory(category);
    if (categoryIdx==-1) {
      System.out.println("카테고리가 존재하지 않습니다");
      return;
    }
    itemList.get(categoryIdx).add(new Item(item, price, category));

  }

  public void deleteItem(UserDAO udao) {
    String item = InputManger.getValue("삭제할 상품 >> ");
    int delIdx=hasItem(item);
    if (delIdx==-1) {
      System.out.println("존재하지 않는 상품입니다.");
      return;
    }
    int idx=0;
    for (Item item1 : itemList.get(delIdx)) {
      if (item1.getName().equals(item)) {
        udao.deleteAllCartItem(itemList.get(delIdx).get(idx).getName());
        itemList.get(delIdx).remove(idx);
      }
      idx += 1;
    }
    System.out.println("삭제완료");
  }

  public void printCategory() {
    for (ArrayList<Item> i : itemList) {
      System.out.printf("[%s] : ",i.get(0).getCategory());
      for (Item item : i) {
        if (item.getName()==null) {
          System.out.print("[no data]");
        }else {
          System.out.print(item.getName() + " " + item.getPrice() + "원 ");
        }
      }
      System.out.println();
    }
  }

  public void addCategory() {
    String name = InputManger.getValue("추가할 카테고리 >> ");
    if (hasCategory(name)!=-1) {
      System.out.println("카테고리가 이미 존재합니다.");
      return;
    }
    itemList.add(new ArrayList<>());
    itemList.get(itemList.size() - 1).add(new Item(name));
    System.out.println("카테고리 삭제완료");
  }
}
