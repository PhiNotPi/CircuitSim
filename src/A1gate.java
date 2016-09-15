import java.util.ArrayList;

public class A1gate extends UnitTile {

  private char type;
  private boolean[] startstate = { false };

  A1gate(char type) {
    this.type = Character.toLowerCase(type);
    state = startstate;
  }

  A1gate(boolean... startstate) {
    for (int i = 0; i < startstate.length && i < this.startstate.length; i++) {
      this.startstate[i] = startstate[i];
    }
    state = this.startstate;
  }

  boolean logic(boolean a, boolean b) {
    switch (type) {
    case 'a':
      return a && b;
    case 'r':
      return a || b;
    default:
      return false;
    }
  }

  @Override
  int[] inputs() {
    return new int[] { 0, 1 };
  }

  @Override
  int[] outputs() {
    return new int[] { 2 };
  }

  @Override
  boolean[] updatestate(boolean[] input, boolean[] state) {
    return new boolean[] { logic(input[0], input[1]) };
  }

  @Override
  boolean[] output(boolean[] state) {
    return state;
  }

  @Override
  char print1x1(boolean[] state) {
    if (state[0]) {
      return Character.toUpperCase(type);
    }
    return type;
  }

  @Override
  char[][] print3x3(boolean[] state) {
    //@formatter:off
    char[][] res = {
      " v ".toCharArray(),
      ">?-".toCharArray(),
      "   ".toCharArray()
    };
    //@formatter:on
    res[1][1] = print1x1(state);
    return res;
  }
  
  @Override
  ArrayList<String> toTokens(){
    ArrayList<String> res = new ArrayList<String>();
    res.add("a1");
    res.add(""+type);
    res.add(""+startstate.length);
    for(int i = 0; i < startstate.length; i++){
      res.add(boolToStr(startstate[i]));
    }
    res.addAll(super.toTokens());
    return res;
  }

}
