import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 * Representa una maquina tragamonedas.
 */
public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private boolean visible;
    private Rectangle machine;

    /**
     * Crea una maquina tragamonedas sin ruedas.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        visible = true;

        machine = new Rectangle();
        machine.changeSize(180, 280);
        machine.changeColor("black");
    }

    /**
     * Agrega una rueda en una posicion.
     * @param pos posicion donde se agrega la rueda.
     */
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

    /**
     * Elimina una rueda.
     * @param pos posicion de la rueda.
     */
    public void delWheel(int pos)
    {
        if (pos < 0 || pos >= wheels.size())
        {
            showError("La posición de la rueda no es válida.");
            return;
        }

        Wheel wheel = wheels.get(pos);
        Symbol symbol = wheel.getVisibleSymbol();

        if (symbol != null)
        {
            symbol.makeInvisible();
        }

        wheels.remove(pos);
    }

    /**
     * Agrega un simbolo a una rueda.
     * @param pos posicion de la rueda.
     * @param color color y nombre del simbolo.
     */
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

    /**
     * Elimina un simbolo de todas las ruedas.
     * @param symbol nombre del simbolo.
     */
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

    /**
     * Coloca una rueda mostrando un simbolo determinado.
     * @param wheel posicion de la rueda.
     * @param symbol nombre del simbolo.
     */
    public void placeSymbol(int wheel, String symbol)
    {
        if (wheel < 0 || wheel >= wheels.size())
        {
            showError("La rueda indicada no existe.");
            return;
        }

        if (symbol == null)
        {
            showError("El símbolo no es válido.");
            return;
        }

        Wheel selectedWheel = wheels.get(wheel);

        if (selectedWheel.getNumberSymbols() == 0)
        {
            showError("La rueda no tiene símbolos.");
            return;
        }

        boolean found = false;
        int numberSymbols = selectedWheel.getNumberSymbols();

        for (int i = 0; i < numberSymbols; i++)
        {
            Symbol current = selectedWheel.getVisibleSymbol();

            if (current != null &&
                current.getName().equals(symbol))
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

    /**
     * Gira una rueda un numero aleatorio de pasos.
     * @param wheel posicion de la rueda.
     */
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

        spin(wheel, steps);
    }

    /**
     * Gira una rueda el numero de pasos indicado.
     * Si la maquina esta visible, se muestra cada paso.
     * @param wheel posicion de la rueda.
     * @param steps numero de pasos.
     */
    public void spin(int wheel, int steps)
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

        if (selectedWheel.isFixed())
        {
            showError("La rueda está fijada.");
            return;
        }

        if (steps < 0)
        {
            showError("El número de pasos no puede ser negativo.");
            return;
        }

        for (int i = 0; i < steps; i++)
        {
            selectedWheel.spin(1);

            if (visible)
            {
                pause();
            }
        }
    }

    /**
     * Gira todas las ruedas.
     */
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

    /**
     * Retorna los nombres de los simbolos de la maquina sin repetir.
     * @return arreglo con los simbolos.
     */
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

    /**
     * Retorna la cantidad de simbolos diferentes.
     * @return numero de simbolos diferentes.
     */
    public int distinctSymbols()
    {
        return symbols().length;
    }

    /**
     * Retorna la configuracion actual de la maquina.
     * @return nombres visibles en cada rueda.
     */
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

    /**
     * Deja la maquina en una configuracion determinada.
     * @param configuration configuracion deseada.
     */
    public void setConfiguration(String[] configuration)
    {
        if (configuration == null)
        {
            showError("La configuración no es válida.");
            return;
        }

        if (configuration.length != wheels.size())
        {
            showError("La configuración no corresponde al número de ruedas.");
            return;
        }

        for (int i = 0; i < configuration.length; i++)
        {
            if (configuration[i] == null)
            {
                showError("La configuración no es válida.");
                return;
            }

            Wheel wheel = wheels.get(i);

            if (wheel.getNumberSymbols() == 0)
            {
                showError("Una rueda no tiene símbolos.");
                return;
            }

            int numberSymbols = wheel.getNumberSymbols();
            boolean found = false;

            for (int j = 0; j < numberSymbols; j++)
            {
                Symbol current = wheel.getVisibleSymbol();

                if (current != null &&
                    current.getName().equals(configuration[i]))
                {
                    found = true;
                    break;
                }

                wheel.spin(1);
            }

            if (!found)
            {
                showError("El símbolo no existe en la rueda.");
                return;
            }
        }
    }

    /**
     * Determina si la configuracion actual es ganadora.
     * @return true si todas las ruedas muestran el mismo simbolo.
     */
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

    /**
     * Hace visible la maquina.
     */
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

    /**
     * Hace invisible la maquina.
     */
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

    /**
     * Intercambia dos ruedas.
     * @param wheel1 primera rueda.
     * @param wheel2 segunda rueda.
     */
    public void exchangeWheels(int wheel1, int wheel2)
    {
        if (!validWheel(wheel1) || !validWheel(wheel2))
        {
            showError("Una de las ruedas no existe.");
            return;
        }

        if (wheel1 == wheel2)
        {
            return;
        }

        Wheel temporary = wheels.get(wheel1);
        wheels.set(wheel1, wheels.get(wheel2));
        wheels.set(wheel2, temporary);
    }

    /**
     * Fija una rueda.
     * @param wheel posicion de la rueda.
     */
    public void fixWheel(int wheel)
    {
        if (!validWheel(wheel))
        {
            showError("La rueda indicada no existe.");
            return;
        }

        wheels.get(wheel).fix();
    }

    /**
     * Suelta una rueda.
     * @param wheel posicion de la rueda.
     */
    public void releaseWheel(int wheel)
    {
        if (!validWheel(wheel))
        {
            showError("La rueda indicada no existe.");
            return;
        }

        wheels.get(wheel).release();
    }

    /**
     * Termina el simulador.
     */
    public void exit()
    {
        makeInvisible();
        wheels.clear();
    }

    /**
     * Indica si la maquina tiene por lo menos una rueda.
     * @return true si tiene ruedas.
     */
    public boolean ok()
    {
        return wheels.size() > 0;
    }

    /**
     * Verifica si una posicion corresponde a una rueda existente.
     * @param wheel posicion.
     * @return true si la rueda existe.
     */
    private boolean validWheel(int wheel)
    {
        return wheel >= 0 && wheel < wheels.size();
    }

    /**
     * Hace una pequena pausa para visualizar cada paso.
     */
    private void pause()
    {
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Muestra un mensaje de error solamente cuando la maquina es visible.
     * @param message mensaje que se mostrara.
     */
    private void showError(String message)
    {
        if (visible)
        {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}