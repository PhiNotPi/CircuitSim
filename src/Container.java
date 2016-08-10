import java.util.HashSet;
import java.util.Set;

public class Container extends Circuit {

  Set<Circuit> components = new HashSet<Circuit>();

  public Container(Circuit par) {
    super(par);
  }

  @Override
  int width() {
    int max = 0;
    for (Circuit comp : components) {
      int cur = comp.col + comp.width();
      if (cur > max) {
        max = cur;
      }
    }
    return max;
  }

  @Override
  int height() {
    int max = 0;
    for (Circuit comp : components) {
      int cur = comp.row + comp.height();
      if (cur > max) {
        max = cur;
      }
    }
    return max;
  }

  @Override
  void calc() {
    for (Circuit comp : components) {
      comp.calc();
    }
  }

  @Override
  void post() {
    for (Circuit comp : components) {
      comp.post();
    }
  }

  @Override
  char[][] print(int scale) {
    if (height() < 1 || width() < 1) {
      return null;
    }
    char[][] res = new char[height() * scale][width() * scale];
    for (int i = 0; i < res.length; i++) {
      for (int j = 0; j < res[0].length; j++) {
        res[i][j] = ' ';
      }
    }
    for (Circuit c : components) {
      char[][] sub = c.print(scale);
      for (int i = 0; i < sub.length; i++) {
        for (int j = 0; j < sub[0].length; j++) {
          res[c.row * scale + i][c.col * scale + j] = sub[i][j];
        }
      }
    }
    return res;
  }

  boolean addComponent(Circuit c, int row, int col) {
    for (int i = row; i < row + c.height(); i++) {
      for (int j = col; j < col + c.width(); j++) {
        if (contains(i, j) > 0) {
          return false;
        }
      }
    }
    components.add(c);
    c.row = row;
    c.col = col;
    return true;
  }

  @Override
  int contains(int r, int c) {
    for (Circuit circ : components) {
      int res = circ.contains(r - circ.row, c - circ.col);
      if (res > 0 && par != null) {
        return res + 1;
      }
    }
    return 0;
  }

}
