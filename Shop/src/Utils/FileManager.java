package Utils;

import dao.ItemDAO;
import dao.UserDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
  final private static String CUR_PATH = System.getProperty("user.dir") + "\\Shop\\src\\";

  private static void saveData(String fileName, String data) {
    try (FileWriter fw = new FileWriter(CUR_PATH + fileName)) {
      fw.write(data);
      System.out.println(fileName + "저장완료");
    } catch (IOException e) {
      System.out.print("입출력 에러");
    }
  }


  public static void saveFile(ItemDAO idao, UserDAO udao) {
    String itemData = idao.getDataFromList();
    String userData = udao.getDataFromUserList();
    String cartData = udao.getDataFromCartList();

    saveData("item.txt", itemData);
    saveData("user.txt", userData);
    saveData("cart.txt", cartData);
  }

  public static void loadFile(ItemDAO idao, UserDAO udao) {
    String itemData = loadData("item.txt");
    String userData = loadData("user.txt");
    String cartData = loadData("cart.txt");
    idao.addItemFromData(itemData);
    udao.addUserFromData(userData);
    udao.addCartFromData(cartData);
    System.out.println("데이터 로드 완료");
  }

  private static String loadData(String fileName) {
    String data = "";
    try (BufferedReader br = new BufferedReader(new FileReader(CUR_PATH + fileName))) {
      while (true) {
        String line = br.readLine();
        if (line == null) break;
        data += line;
        data += "\n";
      }
      data = data.substring(0, data.length() - 1);
    } catch (Exception e) {
      System.out.println(fileName + "로드 실패");
      e.printStackTrace();
    }
    return data;
  }
}
