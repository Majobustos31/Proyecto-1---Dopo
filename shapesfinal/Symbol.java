public class Symbol
{
    private String name;
    private String color;
    private Circle figure;
    private static final int SIZE = 60;

    public Symbol(String name, String color)
    {
        this.name = name;
        this.color = color;

        figure = new Circle();
        figure.changeSize(SIZE);
        figure.changeColor(color);
        figure.makeInvisible();
    }

    public String getName()  { return name; }
    public String getColor() { return color; }
    public int getSize()     { return SIZE; }

    public void makeVisible()   { figure.makeVisible(); }
    public void makeInvisible() { figure.makeInvisible(); }

    /**
     * Mueve el símbolo a una posición absoluta (x, y).
     * Consulta la posición real del Circle antes de mover.
     */
    public void moveTo(int x, int y)
    {
        figure.moveHorizontal(x - figure.getX());
        figure.moveVertical(y - figure.getY());
        figure.makeInvisible();
        figure.makeVisible();
    }
}