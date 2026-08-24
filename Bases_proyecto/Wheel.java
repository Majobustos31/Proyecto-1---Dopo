import java.util.ArrayList;


public class Wheel
{
    private ArrayList<Symbol> symbols;
    private int position;

    public Wheel()
    {
        symbols = new ArrayList<Symbol>();
        position = 0;
    }

    public void addSymbol(Symbol symbol)
    {
        if (symbol != null)
        {
            symbols.add(symbol);
        }
    }

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

    public void spin(int steps)
    {
        if (symbols.size() == 0)
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

    public Symbol getVisibleSymbol()
    {
        if (symbols.size() == 0)
        {
            return null;
        }

        return symbols.get(position);
    }

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

    public int getNumberSymbols()
    {
        return symbols.size();
    }
}