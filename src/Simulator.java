public class Simulator {

  public static void main(String[] args) {
    Container canvas = new Container();
    Circuit wire = new UTWire(true);
    Circuit wire2 = new A1gate('a');
    Circuit wire3 = new A1gate('r');
    Circuit wire4 = new UTWire(true);
    Circuit wire5 = new UTWire();
    Circuit wire6 = new UTWire();
    wire4.rot += 1;
    wire5.rot += 1;

    canvas.addComponent(wire, 1, 0);
    canvas.addComponent(wire2, 1, 1);
    canvas.addComponent(wire3, 1, 2);
    canvas.addComponent(wire6, 1, 3);
    canvas.addComponent(wire4, 0, 1);
    canvas.addComponent(wire5, 0, 2);
    int scale = 1;
    display(canvas.print(scale));
    canvas.post();
    canvas.calc();
    canvas.clearElectrons();
    display(canvas.print(scale));
    canvas.post();
    canvas.calc();
    canvas.clearElectrons();
    display(canvas.print(scale));
    canvas.post();
    canvas.calc();
    canvas.clearElectrons();
    display(canvas.print(scale));
    canvas.post();
    canvas.calc();
    canvas.clearElectrons();
    display(canvas.print(scale));
    System.out.println(canvas.toTokens());
  }

  public static void display(char[][] data) {
    System.out.println();
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        System.out.print(data[i][j]);
      }
      System.out.println();
    }
  }

}
