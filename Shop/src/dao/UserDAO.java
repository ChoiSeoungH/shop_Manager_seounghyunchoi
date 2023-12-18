package dao;

import Utils.InputManger;
import vo.Cart;
import vo.Item;
import vo.User;

import java.util.ArrayList;

public class UserDAO {
  private final ArrayList<Cart> cartList;
  private final ArrayList<User> userList;

  public UserDAO() {
    userList = new ArrayList<>();
    cartList = new ArrayList<>();
  }

  public void addUserFromData(String userData) {
    String[] temp = userData.split("\n");
    for (String s : temp) {
      String[] info = s.split("/");
      userList.add(new User(info[0], info[1], info[2]));
    }
//    printUser(); //저장 test
  }

  public void addCartFromData(String cartData) {
    String[] temp = cartData.split("\n");
    for (String s : temp) {
      String[] info = s.split("/");
      cartList.add(new Cart(info[0], info[1]));
    }
  }

  public String getDataFromUserList() {
    String data = "";
    for (User u : userList) {
      data += u.getId() + "/" + u.getPw() + "/" + u.getName() + "\n";
    }
    return data;
  }

  public String getDataFromCartList() {
    String data = "";
    for (Cart c : cartList) {
      data += c.getUserId() + "/" + c.getItemName() + "\n";
    }
    return data;
  }

  public void joinUser() {
    String id = InputManger.getValue("id >> ");
    if (isExistId(id) != -1) {
      System.out.println("이미 존재하는 아이디입니다.");
      return;
    }
    String pw = InputManger.getValue("pw >> ");
    String name = InputManger.getValue("name >> ");
    userList.add(new User(id, pw, name));
    System.out.println(userList.get(userList.size() - 1));
    System.out.println("회원가입 완료");
  }

  private int isExistId(String id) {
    int idx = 0;
    for (User u : userList) {
      if (u.getId().equals(id)) {
        return idx;
      }
      idx += 1;
    }
    return -1;
  }

  public String quitUser(String log) {
    System.out.println(log+"님 회원탈퇴");
    int delIdx = isExistId(log);
    String pw = InputManger.getValue("pw >> ");
    if (!userList.get(delIdx).getPw().equals(pw)) {
      System.out.println("비밀번호가 일치 하지 않습니다");
      return log;
    }
    System.out.println(userList.get(delIdx));
    deleteAllUsersCartItem(log);
    userList.remove(delIdx);
    System.out.println("회원탈퇴 완료");
    return null;
  }

  public void printUser() {
    for (User u : userList) {
      if (u.getId().equals("admin")) continue;
      System.out.println(u);
    }
  }

  public String login() {
    String id = InputManger.getValue("id >> ");
    int delIdx = isExistId(id);
    if (delIdx == -1) {
      System.out.println("아이디가 존재하지 않습니다.");
      return null;
    }
    String pw = InputManger.getValue("pw >> ");
    if (!userList.get(delIdx).getPw().equals(pw)) {
      System.out.println("비밀번호가 일치 하지 않습니다");
      return null;
    }
    System.out.println("로그인 완료");
    return id;
  }

  public void deleteMyCartItem(String log, ItemDAO idao) {
    printMyCart(log, idao);
    String delName = InputManger.getValue("삭제할 상품 >> ");
    if (!hasItem(log, delName)) {
      System.out.println("상품이 없습니다.");
      return;
    }
    for (int i = cartList.size() - 1; i >= 0; i--) {
      if (cartList.get(i).getUserId().equals(log) && cartList.get(i).getItemName().equals(delName)) {
        cartList.remove(i);
        System.out.println("주문취소 완료");
        return;
      }
    }
  }

  public void deleteAllCartItem(String name) {
    for (int i = cartList.size() - 1; i >= 0; i--) {
      if (cartList.get(i).getItemName().equals(name)) {
        cartList.remove(i);
      }
    }
  }

  private boolean hasItem(String log, String delName) {
    for (Cart c : cartList) {
      if (c.getUserId().equals(log) && c.getItemName().equals(delName)) {
        return true;
      }
    }
    return false;
  }

  public void addItemtoMyCart(String log, String item) {
    cartList.add(new Cart(log, item));
  }

  public void printCart() {
    int i = 0;
    for (Cart c : cartList) {
      System.out.printf("%d\t %s\t %s%n", ++i, c.getUserId(), c.getItemName());
    }
  }

  public void addCart(ItemDAO idao) {
    String id = InputManger.getValue("id >> ");
    if (isExistId(id) == -1) {
      System.out.println("존재하지 않는 아이디입니다.");
      return;
    }
    String itemName = InputManger.getValue("name >> ");
    if (idao.hasItem(itemName) == -1) {
      System.out.println("상품명을 확인해주세요");
      return;
    }
    cartList.add(new Cart(id, itemName));
    System.out.println("장바구니추가 완료");
  }

  public void deleteCart() {
    int delIdx = InputManger.getValue("삭제할 카트[%d~%d] >> ".formatted(1, cartList.size()), 1, cartList.size()) - 1;
    cartList.remove(delIdx);
    System.out.println("장바구니삭제 완료");
  }

  public void addUser() {
    String id = InputManger.getValue("id >> ");
    if (isExistId(id) != -1) {
      System.out.println("이미 존재하는 아이디입니다.");
      return;
    }
    String pw = InputManger.getValue("pw >> ");
    String name = InputManger.getValue("name >> ");
    userList.add(new User(id, pw, name));
    System.out.println(userList.get(userList.size() - 1));
    System.out.println("회원추가 완료");

  }

  public void deleteUser() {
    String id = InputManger.getValue("id >> ");
    if (id.equals("admin")) {
      System.out.println("관리자 삭제 불가");
      return;
    }
    int delIdx = isExistId(id);
    if (delIdx == -1) {
      System.out.println("아이디가 존재하지 않습니다.");
      return;
    }
    deleteAllUsersCartItem(id);
    userList.remove(delIdx);
    System.out.println("회원삭제 완료");
  }

  private void deleteAllUsersCartItem(String id) {
    for (int i = cartList.size() - 1; i >= 0; i--) {
      if (cartList.get(i).getUserId().equals(id)) {
        cartList.remove(i);
      }
    }
  }

  public void printMyCart(String log, ItemDAO idao) {
    if (!isExistCart(log)) {
      System.out.println("장바구니 목록이 없습니다.");
      return;
    }
    System.out.println("아이디\t상품\t가격");
    int total = 0;
    for (Cart c : cartList) {
      if (c.getUserId().equals(log)) {
        System.out.print(c);
        for (ArrayList<Item> i : idao.getItemList()) {
          for (Item item : i) {
            if (c.getItemName().equals(item.getName())) {
              System.out.print("\t" + item.getPrice() + "원\n");
              total += item.getPrice();
            }
          }
        }
      }
    }
    System.out.println("총액 : " + total + "원");
  }

  private boolean isExistCart(String log) {
    for (Cart c : cartList) {
      if (c.getUserId().equals(log)) {
        return true;
      }
    }
    return false;
  }

}

