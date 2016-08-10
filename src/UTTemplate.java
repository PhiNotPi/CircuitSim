public abstract class UTTemplate {

  abstract int[] inputs();

  abstract int[] outputs();

  abstract boolean[] startstate();

  abstract boolean[] updatestate(boolean[] input, boolean[] state);

  abstract boolean[] output(boolean[] state);

  abstract char[][] print3x3(boolean[] state);

}
