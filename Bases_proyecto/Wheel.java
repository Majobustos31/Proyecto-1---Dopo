import java.util.ArrayList;


/**
 * Representa una rueda de la maquina tragamonedas.
 */
public class Wheel
{
    private ArrayList<Symbol> symbols;
    private int position;
    private boolean fixed;

    /**
     * Crea una rueda vacia.
     */
    public Wheel()
    {
        symbols = new ArrayList<Symbol>();
        position = 0;
        fixed = false;
    }

    /**
     * Agrega un simbolo a la rueda.
     * @param symbol simbolo que se desea agregar.
     */
    public void addSymbol(Symbol symbol)
    {
        if (symbol != null)
        {
            symbols.add(symbol);

            if (symbols.size() == 1)
            {
                position = 0;
                symbol.makeVisible();
            }
        }
    }

    /**
     * Elimina un simbolo de la rueda.
     * @param name nombre del simbolo.
     * @return true si se elimino, false si no existia.
     */
    public boolean removeSymbol(String name)
    {
        int index = findSymbol(name);

        if (index == -1)
        {
            return false;
        }

        symbols.get(index).makeInvisible();
        symbols.remove(index);

        if (symbols.size() == 0)
        {
            position = 0;
        }
        else if (position >= symbols.size())
        {
            position = 0;
        }

        if (symbols.size() > 0)
        {
            symbols.get(position).makeVisible();
        }

        return true;
    }

    /**
     * Gira la rueda el numero de pasos indicado.
     * Si la rueda esta fijada, no gira.
     * @param steps numero de pasos.
     */
    public void spin(int steps)
    {
        if (symbols.size() == 0 || fixed)
        {
            return;
        }

        symbols.get(position).makeInvisible();

        position = position + steps;
        position = position % symbols.size();

        if (position < 0)
        {
            position = position + symbols.size();
        }

        symbols.get(position).makeVisible();
    }

    /**
     * Retorna el simbolo actualmente visible.
     * @return simbolo visible o null si la rueda esta vacia.
     */
    public Symbol getVisibleSymbol()
    {
        if (symbols.size() == 0)
        {
            return null;
        }

        return symbols.get(position);
    }

    /**
     * Busca un simbolo por nombre.
     * @param name nombre que se busca.
     * @return posicion del simbolo o -1 si no existe.
     */
    private int findSymbol(String name)
    {
        for (int i = 0; i < symbols.size(); i++)
        {
            if (symbols.get(i).getName().equals(name))
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * Retorna la cantidad de simbolos.
     * @return numero de simbolos.
     */
    public int getNumberSymbols()
    {
        return symbols.size();
    }

    /**
     * Fija la rueda.
     */
    public void fix()
    {
        fixed = true;
    }

    /**
     * Suelta la rueda.
     */
    public void release()
    {
        fixed = false;
    }

    /**
     * Indica si la rueda esta fijada.
     * @return true si esta fijada.
     */
    public boolean isFixed()
    {
        return fixed;
    }
}