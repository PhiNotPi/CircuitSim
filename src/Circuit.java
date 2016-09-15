import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Circuit {

  abstract int width();

  abstract int height();

  abstract void calc();

  abstract void post();

  abstract char[][] print(int scale);

  abstract int contains(int r, int c);
  
  ArrayList<String> toTokens(){
    ArrayList<String> res = new ArrayList<String>();
    res.add(""+row);
    res.add(""+col);
    res.add(""+rot);
    res.add(""+boolToStr(flip));
    return res;
  }

  Circuit par;
  int row;
  int col;
  int rot = 0;
  boolean flip = false;
  Map<Integer, Map<Integer, Integer>> electrons;

  public Circuit() {
      electrons = new HashMap<Integer, Map<Integer, Integer>>();
  }

  public void setPar(Circuit par) {
    this.par = par;
    electrons = null;
  }

  void fixRot() {
    rot = rot % 4;
    if (rot < 0) {
      rot += 4;
    }
  }

  public int getElectron(int r, int c, int side) {
    if (par != null) {
      return par.getElectron(r + this.row, c + this.col, side);
    }
    int sum = r + c;
    int dif = c - r;
    switch (side) {
    case 0:
      break;
    case 1:
      dif++;
      break;
    case 2:
      dif++;
      sum++;
      break;
    case 3:
      sum++;
    }
    Map<Integer, Integer> diag = electrons.get(sum);
    int val = 0;
    if (diag != null && diag.get(dif) != null) {
      // System.out.println(diag + " " + dif);
      val = diag.get(dif);
    }

    return val;
  }

  public void setElectron(int r, int c, int side, int val) {
    if (par != null) {
      par.setElectron(r + this.row, c + this.col, side, val);
      return;
    }

    int sum = r + c;
    int dif = c - r;
    switch (side) {
    case 0:
      break;
    case 1:
      dif++;
      break;
    case 2:
      dif++;
      sum++;
      break;
    case 3:
      sum++;
    }
    Map<Integer, Integer> diag = electrons.get(sum);
    if (diag == null) {
      diag = new HashMap<Integer, Integer>();
      electrons.put(sum, diag);
    }
    Integer prev = diag.get(dif);
    if (prev == null || prev == 0) {
      diag.put(dif, val);
    } else if (val != 0) {
      throwError(new Error(r, c, "electron overwrite of " + val + " over "
          + prev));
    }
  }

  public void clearElectrons() {
    if (par != null) {
      par.clearElectrons();
      return;
    }
    for (Map<Integer, Integer> col : electrons.values()) {
      for (int key : col.keySet()) {
        col.put(key, 0);
      }
    }
  }

  public void setCoords(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public void throwError(Error e) {
    if (par != null) {
      e.col += col;
      e.row += row;
      par.throwError(e);
      return;
    }
    System.out.println(e);
  }
  
  static public String boolToStr(boolean b){
    if(b){
      return "t";
    }
    return "f";
  }

}
