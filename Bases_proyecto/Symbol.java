public class Symbol
{
    private String name;
    private String color;
    private Circle figure;

    public Symbol(String name, String color)
    {
        this.name = name;
        this.color = color;

        figure = new Circle();
        figure.changeSize(35);
        figure.changeColor(color);
    }

    public String getName()
    {
        return name;
    }

    public String getColor()
    {
        return color;
    }

    public void makeVisible()
    {
        figure.makeVisible();
    }

    public void makeInvisible()
    {
        figure.makeInvisible();
    }
}