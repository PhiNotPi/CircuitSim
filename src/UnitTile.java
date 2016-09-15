import java.util.ArrayList;

public abstract class UnitTile extends Circuit {

  // UTTemplate type;
  boolean[] state;

  int width() {
    return 1;
  }

  int height() {
    return 1;
  }

  @Override
  void calc() {
    int[] inputsides = inputs();
    boolean[] invals = new boolean[inputsides.length];
    for (int i = 0; i < inputsides.length; i++) {
      int side = trans(inputsides[i]);
      int elec = getElectron(0, 0, side);
      if (side > 1) {
        elec = -elec;
      }
      if (elec == 1) {
        invals[i] = true;
      } else if (elec == 0) {
        invals[i] = false;
      } else if (elec == -1) {
        invals[i] = false;
      } else {
        throwError(new Error(row, col, "invalid input electron direction "
            + elec));
      }
    }
    state = updatestate(invals, state);
  }

  int trans(int side) {
    if (flip) {
      if (side == 1) {
        side = 3;
      } else if (side == 3) {
        side = 1;
      }
    }
    fixRot();
    return (side + rot) % 4;
  }

  int invtrans(int side) {
    fixRot();
    side = (side + 4 - rot) % 4;
    if (flip) {
      if (side == 1) {
        side = 3;
      } else if (side == 3) {
        side = 1;
      }
    }
    return side;
  }

  @Override
  void post() {
    int[] outputsides = outputs();
    boolean[] outvals = output(state);
    for (int i = 0; i < outputsides.length; i++) {
      int side = trans(outputsides[i]);
      int elec = 0;
      if (outvals[i]) {
        elec = 1;
      }
      if (side < 2) {
        elec = -elec;
      }
      this.setElectron(0, 0, side, elec);
    }
  }

  @Override
  char[][] print(int scale) {
    if (height() < 1 || width() < 1) {
      return null;
    }

    char[][] res = new char[height() * scale][width() * scale];
    if (scale == 1) {
      res = trans(new char[][] { { print1x1(state) } });
    } else if (scale == 3) {
      res = trans(print3x3(state));
    } else {
      for (int i = 0; i < res.length; i++) {
        for (int j = 0; j < res[0].length; j++) {
          res[i][j] = '$';
        }
      }
    }
    return res;
  }

  @Override
  int contains(int r, int c) {
    if (r == 1 && c == 1) {
      return 1;
    }
    return 0;
  }

  char[][] trans(char[][] data) {
    fixRot();
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        data[i][j] = trans(data[i][j]);
      }
    }
    if (flip) {
      char[][] temp = new char[data.length][data[0].length];
      for (int i = 0; i < data.length; i++) {
        temp[i] = data[data.length - 1 - i];
      }
      data = temp;
    }
    for (int q = 0; q < rot; q++) {
      char[][] temp = new char[data[0].length][data.length];
      for (int i = 0; i < data.length; i++) {
        for (int j = 0; j < data[0].length; j++) {
          temp[j][data.length - 1 - i] = data[i][j];
        }
      }
      data = temp;
    }
    return data;
  }

  char trans(char data) {
    if (flip) {
      if (data == '^') {
        data = 'V';
      } else if (data == 'V' || data == 'v') {
        data = '^';
      } else if (data == '\\') {
        data = '/';
      } else if (data == '/') {
        data = '\\';
      }
    }
    String arrows = ">V<^";
    String pipes = "-|";
    String diags = "\\/";
    if (arrows.indexOf(data) > -1) {
      data = arrows.charAt((arrows.indexOf(data) + rot) % 4);
    } else if (pipes.indexOf(data) > -1) {
      data = pipes.charAt((pipes.indexOf(data) + rot) % 2);
    } else if (diags.indexOf(data) > -1) {
      data = diags.charAt((diags.indexOf(data) + rot) % 2);
    }
    return data;
  }

  abstract int[] inputs();

  abstract int[] outputs();

  abstract boolean[] updatestate(boolean[] input, boolean[] state);

  abstract boolean[] output(boolean[] state);

  abstract char print1x1(boolean[] state);

  abstract char[][] print3x3(boolean[] state);
  
  ArrayList<String> toTokens(){
    ArrayList<String> res = new ArrayList<String>();
    res.add(""+state.length);
    for(int i = 0; i < state.length; i++){
      res.add(boolToStr(state[i]));
    }
    res.addAll(super.toTokens());
    return res;
  }
}
