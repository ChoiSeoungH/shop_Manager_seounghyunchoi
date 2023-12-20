package Controller;

import Utils.FileManager;
import Utils.InputManger;
import dao.ItemDAO;
import dao.UserDAO;

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
    printMainMenu();
  }//eow

  private void printMainMenu() {
    while (true) {
      System.out.println("[1.로그인] [2.회원가입] [0.종료]");
      int menu = InputManger.getValue("메뉴 입력 >> ", 0, 2);
      switch (menu) {
        case 0: //종료
          System.out.println("종료");
          return;
        case 1: //로그인
          log = udao.login();
          if (log == null) continue;
          if (log.equals("admin")) {
            printAdminMenu();
          } else {
            printMemberMenu();
          }
          break;
        case 2: //회원가입
          udao.joinUser();
          break;
      }//eos
    }
  }

  private boolean isLogin() {
    return log != null;
  }

  private void printAdminMenu() {
    while (true) {
      System.out.println("[1.아이템관리] [2.카테고리관리] [3.장바구니관리] [4.유저관리] [5.파일 저장] [6.파일 로드] [0.로그아웃] ");
      int menu = InputManger.getValue("메뉴 입력 >> ", 0, 6);
      switch (menu) {
        case 0:
          log = null;
          System.out.println("로그아웃");
          return;
        case 1: //아이템
          printItemManagementMenu();
          break;
        case 2: //카테고리
          printCategoryManagementMenu();
          break;
        case 3: //장바구니
          printCartManagementMenu();
          break;
        case 4: //유저관리
          printUserManagementMenu();
          break;
        case 5: //파일저장
          FileManager.saveFile(idao, udao);
          break;
        case 6: //파일로드
          FileManager.loadFile(idao, udao);
          break;
      }
    }
  }

  private void printCategoryManagementMenu() {
    while (true) {
      idao.printCategory();
      int menu = InputManger.getValue("[1.추가] [2.삭제] [0.뒤로가기] >> ", 0, 2);
      switch (menu) {
        case 0:
          return;
        case 1:
          idao.addCategory();
          break;
        case 2:
          idao.deleteCategory();
          break;
      }
    }

  }

  private void printUserManagementMenu() {
    while (true) {
      udao.printUser();
      int menu = InputManger.getValue("[1.추가] [2.삭제] [0.뒤로가기] >> ", 0, 2);
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
      int menu = InputManger.getValue("[1.추가] [2.삭제] [0.뒤로가기] >> ", 0, 2);
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

  private void printItemManagementMenu() {
    while (true) {
      idao.printCategory();
      int menu = InputManger.getValue("[1.추가] [2.삭제] [0.뒤로가기] >> ", 0, 2);
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
    while (log != null) {
      System.out.println(log + "님 로그인중");
      System.out.println("[1.쇼핑] [2.주문확인] [3.탈퇴(주문정보)] [0.로그아웃]");
      int menu = InputManger.getValue("메뉴 입력 >> ", 0, 3);
      switch (menu) {
        case 0:
          log = null;
          System.out.println("로그아웃");
          return;
        case 1://쇼핑
          printCartMenu();
          break;
        case 2://장바구니
          udao.printMyCart(log, idao);
          break;
        case 3://탈퇴
          log = udao.quitUser(log);
          break;
      }
    }
  }

  private void printCartMenu() {
    while (true) {
      System.out.println("[1.구입] [2.주문취소] [0.뒤로가기]");
      int menu = InputManger.getValue("메뉴 입력 >> ", 0, 2);
      switch (menu) {
        case 0:
          System.out.println("뒤로가기");
          return;
        case 1: //구입
          idao.buyItem(log, udao);
          break;
        case 2: //최신 상품 삭제
          udao.deleteMyCartItem(log, idao);
          break;
      }
    }
  }
}
