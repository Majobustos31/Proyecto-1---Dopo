import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private boolean visible;
    private Rectangle machine;

    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        visible = true;

        machine = new Rectangle();
        machine.changeSize(180, 280);
        machine.changeColor("black");
    }

    public void addWheel(int pos)
    {
        if (pos < 0 || pos > wheels.size())
        {
            showError("La posición de la rueda no es válida.");
            return;
        }

        wheels.add(pos, new Wheel());

        if (visible)
        {
            machine.makeVisible();
        }
    }

    public void delWheel(int pos)
    {
        if (pos < 0 || pos >= wheels.size())
        {
            showError("La posición de la rueda no es válida.");
            return;
        }

        wheels.remove(pos);
    }

    public void addSymbol(int pos, String color)
    {
        if (pos < 0 || pos >= wheels.size())
        {
            showError("La rueda indicada no existe.");
            return;
        }

        if (color == null || color.length() == 0)
        {
            showError("El símbolo no es válido.");
            return;
        }

        Symbol symbol = new Symbol(color, color);

        wheels.get(pos).addSymbol(symbol);

        if (visible)
        {
            symbol.makeVisible();
        }
    }

    public void delSymbol(String symbol)
    {
        if (symbol == null)
        {
            showError("El símbolo no es válido.");
            return;
        }

        boolean deleted = false;

        for (Wheel wheel : wheels)
        {
            if (wheel.removeSymbol(symbol))
            {
                deleted = true;
            }
        }

        if (!deleted)
        {
            showError("El símbolo no existe.");
        }
    }

    public void placeSymbol(int wheel, String symbol)
    {
        if (wheel < 0 || wheel >= wheels.size())
        {
            showError("La rueda indicada no existe.");
            return;
        }

        Wheel selectedWheel = wheels.get(wheel);

        if (selectedWheel.getNumberSymbols() == 0)
        {
            showError("La rueda no tiene símbolos.");
            return;
        }

        boolean found = false;

        for (int i = 0; i < selectedWheel.getNumberSymbols(); i++)
        {
            if (selectedWheel.getVisibleSymbol() != null &&
                selectedWheel.getVisibleSymbol().getName().equals(symbol))
            {
                found = true;
                break;
            }

            selectedWheel.spin(1);
        }

        if (!found)
        {
            showError("El símbolo no existe en la rueda.");
        }
    }

    public void spin(int wheel)
    {
        if (wheel < 0 || wheel >= wheels.size())
        {
            showError("La rueda indicada no existe.");
            return;
        }

        Wheel selectedWheel = wheels.get(wheel);

        if (selectedWheel.getNumberSymbols() == 0)
        {
            showError("La rueda no tiene símbolos.");
            return;
        }

        int steps =
            (int)(Math.random() * selectedWheel.getNumberSymbols());

        selectedWheel.spin(steps);
    }

    public void spin()
    {
        if (wheels.size() == 0)
        {
            showError("La máquina no tiene ruedas.");
            return;
        }

        for (int i = 0; i < wheels.size(); i++)
        {
            spin(i);
        }

        if (isJackpot())
        {
            machine.changeColor("yellow");
        }
        else
        {
            machine.changeColor("black");
        }
    }

    public String[] symbols()
    {
        ArrayList<String> result = new ArrayList<String>();

        for (Wheel wheel : wheels)
        {
            int numberSymbols = wheel.getNumberSymbols();

            if (numberSymbols > 0)
            {
                Symbol current = wheel.getVisibleSymbol();

                for (int i = 0; i < numberSymbols; i++)
                {
                    if (current != null &&
                        !result.contains(current.getName()))
                    {
                        result.add(current.getName());
                    }

                    wheel.spin(1);
                    current = wheel.getVisibleSymbol();
                }
            }
        }

        return result.toArray(new String[result.size()]);
    }

    public int distinctSymbols()
    {
        return symbols().length;
    }

    public String[] configuration()
    {
        String[] result = new String[wheels.size()];

        for (int i = 0; i < wheels.size(); i++)
        {
            Symbol symbol = wheels.get(i).getVisibleSymbol();

            if (symbol == null)
            {
                result[i] = null;
            }
            else
            {
                result[i] = symbol.getName();
            }
        }

        return result;
    }

    public boolean isJackpot()
    {
        if (wheels.size() == 0)
        {
            return false;
        }

        Symbol first = wheels.get(0).getVisibleSymbol();

        if (first == null)
        {
            return false;
        }

        String firstName = first.getName();

        for (int i = 1; i < wheels.size(); i++)
        {
            Symbol current = wheels.get(i).getVisibleSymbol();

            if (current == null)
            {
                return false;
            }

            if (!firstName.equals(current.getName()))
            {
                return false;
            }
        }

        machine.changeColor("yellow");

        return true;
    }

    public void makeVisible()
    {
        visible = true;

        machine.makeVisible();

        for (Wheel wheel : wheels)
        {
            Symbol symbol = wheel.getVisibleSymbol();

            if (symbol != null)
            {
                symbol.makeVisible();
            }
        }
    }

    public void makeInvisible()
    {
        visible = false;

        machine.makeInvisible();

        for (Wheel wheel : wheels)
        {
            Symbol symbol = wheel.getVisibleSymbol();

            if (symbol != null)
            {
                symbol.makeInvisible();
            }
        }
    }

    public void exit()
    {
        makeInvisible();

        wheels.clear();
    }

    public boolean ok()
    {
        return wheels.size() > 0;
    }

    private void showError(String message)
    {
        if (visible)
        {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}