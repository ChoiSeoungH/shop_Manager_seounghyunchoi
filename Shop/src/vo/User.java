package vo;

public class User {
  String id;
  String pw;
  String name;

  public User() {

  }

  public User(String id, String pw, String name) {
    this.id = id;
    this.pw = pw;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getPw() {
    return pw;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return id+" \t"+pw+" \t"+name;
  }
}
