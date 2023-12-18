package Controller;

import Utils.FileManager;
import Utils.InputManger;
import dao.ItemDAO;
import dao.UserDAO;
import vo.Cart;

public class ShopController {

  private ItemDAO idao;
  private UserDAO udao;
  private String log;

  private void init() {
    idao = new ItemDAO();
    udao = new UserDAO();
    FileManager.loadFile(idao, udao);
  }

  public void run() {
    init();
    while (true) {
      printMainMenu();
      int menu = InputManger.getValue("메뉴 입력 >> ", 0, 4, 100);
      switch (menu) {
        case 0: //종료
          FileManager.saveFile(idao, udao);
          System.out.println("종료");
          return;
        case 1: //가입
          if (isLogin()) {
            System.out.println("로그아웃 후 이용");
            continue;
          }
          udao.joinUser();
          break;
        case 2: //탈퇴
          if (!isLogin()) {
            System.out.println("로그인 후 이용");
            continue;
          }
          udao.quitUser(log);
          break;
        case 3: //로그인
          if (isLogin()) {
            System.out.println("로그아웃 후 이용");
            continue;
          }
          log = udao.login();
          printMemberMenu();
          break;
        case 4: //로그아웃
          if (!isLogin()) {
            System.out.println("로그인 후 이용");
            continue;
          }
          log = null;
          System.out.println("로그아웃 완료");
          break;
        case 100: // 관리자
          if (isLogin()) {
            System.out.println("로그아웃 후 이용");
            continue;
          }
          printAdminMenu();
          break;
      }//eos

    }//eow
  }

  private boolean isLogin() {
    if (log == null) {
      return false;
    }
    return true;
  }

  private void printMainMenu() {
    System.out.println("[1.가입] [2.탈퇴] [3.로그인] [4.로그아웃]" + "\n[100.관리자] [0.종료] ");
  }

  private void printAdminMenu() {
    while (true) {
      System.out.println("[1.아이템관리] [2.카테고리관리] [3.장바구니관리] [4.유저관리] [0.뒤로가기] ");
      int menu = InputManger.getValue("메뉴 입력 >> ", 0, 4);
      switch (menu) {
        case 0:
          System.out.println("뒤로가기");
          return;
        case 1: //아이템
          printitemManagementMenu();
          break;
        case 2: //카테고리
          idao.categoryManagement(udao);
          break;
        case 3: //장바구니
          printCartManagementMenu();
          break;
        case 4: //유저관리
          printUserManagementMenu();
          break;
      }
    }
  }

  private void printUserManagementMenu() {
    while (true) {
      udao.printUser();
      int menu = InputManger.getValue("[1.추가] [2.삭제] [0.뒤로가기]", 0, 2);
      switch (menu) {
        case 0:
          return;
        case 1:
          udao.addUser();
          break;
        case 2:
          udao.deleteUser();
          break;
      }
    }

  }

  private void printCartManagementMenu() {
    while (true) {
      udao.printCart();
      int menu = InputManger.getValue("[1.추가] [2.삭제] [0.뒤로가기]", 0, 2);
      switch (menu) {
        case 0:
          return;
        case 1:
          udao.addCart(idao);
          break;
        case 2:
          udao.deleteCart();
          break;
      }
    }
  }

  private void printitemManagementMenu() {
    while (true) {
      idao.printItem();
      int menu = InputManger.getValue("[1.추가] [2.삭제] [0.뒤로가기]", 0, 2);
      switch (menu) {
        case 0:
          return;
        case 1:
          idao.addItem();
          break;
        case 2:
          idao.deleteItem(udao);
          break;
      }
    }
  }

  private void printMemberMenu() {
    while (true) {
      System.out.println(log + "님 로그인중");
      System.out.println("[1.쇼핑] [2.장바구니목록] [0.뒤로가기]");
      int menu = InputManger.getValue("메뉴 입력 >> ", 0, 4);
      switch (menu) {
        case 0:
          System.out.println("뒤로가기");
          return;
        case 1://쇼핑
          printCartMenu();
          break;
        case 2://장바구니
          udao.printMyCart(log, idao);
          break;
      }
    }
  }

  private void printCartMenu() {
    while (true) {
      System.out.println("[1.내 장바구니] [2.삭제] [3.구입] [0.뒤로가기]");
      int menu = InputManger.getValue("메뉴 입력 >> ", 0, 4);
      switch (menu) {
        case 0:
          System.out.println("뒤로가기");
          return;
        case 1: //내장바구니
          udao.printMyCart(log, idao);
          break;
        case 2: //최신 상품 삭제
          udao.deleteMyCartItem(log);
          break;
        case 3: //구입
          idao.buyItem(log, udao);
          break;
      }
    }
  }

}
