/**
 * Representa un simbolo de la maquina tragamonedas.
 * Cada simbolo tiene un nombre y un color.
 */

public class Symbol
{
    private String name;
    private String color;
    private Circle figure;

    /**
     * Crea un nuevo simbolo.
     * @param name nombre del simbolo.
     * @param color color del simbolo.
     */
    public Symbol(String name, String color)
    {
        this.name = name;
        this.color = color;

        figure = new Circle();
        figure.changeSize(35);
        figure.changeColor(color);
    }

    /**
     * Retorna el nombre del simbolo.
     * @return nombre del simbolo.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Retorna el color del simbolo.
     * @return color del simbolo.
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Hace visible el simbolo.
     */
    public void makeVisible()
    {
        figure.makeVisible();
    }

    /**
     * Hace invisible el simbolo.
     */
    public void makeInvisible()
    {
        figure.makeInvisible();
    }
}