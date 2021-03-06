import java.util.ArrayList;

public class UTWire extends UnitTile {

  private boolean[] startstate = { false, false };

  UTWire() {
    state = startstate;
  }

  UTWire(boolean... startstate) {
    for (int i = 0; i < startstate.length && i < this.startstate.length; i++) {
      this.startstate[i] = startstate[i];
    }
    state = this.startstate;
  }

  @Override
  int[] inputs() {
    return new int[] { 0, 2 };
  }

  @Override
  int[] outputs() {
    return new int[] { 2, 0 };
  }

  @Override
  boolean[] updatestate(boolean[] input, boolean[] state) {
    return input;
  }

  @Override
  boolean[] output(boolean[] state) {
    if (state[0] && state[1]) {
      return new boolean[] { false, false };
    }
    return state;
  }

  @Override
  char print1x1(boolean[] state) {
    if (state[0]) {
      if (state[1]) {
        return 'X';
      }
      return '>';
    }
    if (state[1]) {
      return '<';
    }
    return '-';
  }

  @Override
  char[][] print3x3(boolean[] state) {
    //@formatter:off
    char[][] res = {
      "   ".toCharArray(),
      "---".toCharArray(),
      "   ".toCharArray()
    };
    //@formatter:on
    if (state[0]) {
      if (state[1]) {
        res[1][1] = 'X';
      } else {
        res[1][1] = '>';
      }
    } else {
      if (state[1]) {
        res[1][1] = '<';
      }
    }
    return res;
  }

  @Override
  ArrayList<String> toTokens(){
    ArrayList<String> res = new ArrayList<String>();
    res.add("wire");
    res.add(""+startstate.length);
    for(int i = 0; i < startstate.length; i++){
      res.add(boolToStr(startstate[i]));
    }
    res.addAll(super.toTokens());
    return res;
  }

}
