public class Error {

  String text;
  int row;
  int col;

  public Error(int row, int col, String text) {
    this.col = col;
    this.row = row;
    this.text = text;
  }

  public String toString() {
    return "Error: (" + row + "," + col + ") " + text;
  }

}
