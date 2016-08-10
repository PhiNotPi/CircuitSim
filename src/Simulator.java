public class Simulator {

  public static void main(String[] args) {
    Container canvas = new Container(null);
    Circuit wire = new UnitTile(canvas, new UTWire(true));
    Circuit wire2 = new UnitTile(canvas, new UTWire());
    Circuit wire3 = new UnitTile(canvas, new UTWire(false, true));
    wire.rot += 0;

    canvas.addComponent(wire, 0, 0);
    canvas.addComponent(wire2, 0, 1);
    canvas.addComponent(wire3, 0, 2);
    display(canvas.print(3));
    canvas.post();
    canvas.calc();
    display(canvas.print(3));
    canvas.post();
    canvas.calc();
    display(canvas.print(3));
  }

  public static void display(char[][] data) {
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        System.out.print(data[i][j]);
      }
      System.out.println();
    }
  }

}
