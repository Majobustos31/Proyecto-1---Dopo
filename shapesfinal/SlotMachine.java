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
    private Rectangle screen;
    private Circle lever;
    private Rectangle leverBar;
    private Rectangle base;
    private Rectangle leverArm;
    private ArrayList<Rectangle> windowsVista;

    // Constantes de geometría
    private static final int WIN_W = 160;
    private static final int WIN_H = 260;
    private static final int SEP = 25;
    private static final int MARGEN = 50;
    private static final int BORDE = 75;
    private static final int ALTO_CUERPO = 450;

    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        windowsVista = new ArrayList<Rectangle>();
        visible = true;
        wheels.add(new Wheel());
        wheels.add(new Wheel());
        wheels.add(new Wheel());

        redraw();
    }

    private void redraw()
    {
        // 1) Ocultar todo
        if (machine != null)   machine.makeInvisible();
        if (screen != null)    screen.makeInvisible();
        if (base != null)      base.makeInvisible();
        if (lever != null)     lever.makeInvisible();
        if (leverBar != null)  leverBar.makeInvisible();
        if (leverArm != null)  leverArm.makeInvisible();

        if (windowsVista != null)
        {
            for (Rectangle w : windowsVista) w.makeInvisible();
            windowsVista.clear();
        }

        // 2) Dibujar en orden de capas
        //System.out.println("numRuedas=" + wheels.size() + " anchoCuerpo=" + (wheels.size() * 160 + Math.max(0, wheels.size()-1) * 25 + 2*50 + 2*75));
        drawCuerpo();      // capa 1: rojo (fondo)
        drawPantalla();    // capa 2: negro encima del rojo
        drawWindows();     // capa 3: ventanas blancas + símbolos
        drawBase();  
        drawPalanca(); 
    }

    // ============================================================
    // CAPA 1: cuerpo rojo
    // ============================================================
    private void drawCuerpo()
    {
        int numRuedas = wheels.size();
        int anchoPantalla = numRuedas * WIN_W + Math.max(0, numRuedas - 1) * SEP + 2 * MARGEN;
        int anchoCuerpo = anchoPantalla + 2 * BORDE;

        machine = new Rectangle();
        machine.changeSize(ALTO_CUERPO, anchoCuerpo);
        machine.changeColor("red");
        machine.moveHorizontal(400);
        machine.moveVertical(200);
        machine.makeVisible();
    }

    // ============================================================
    // CAPA 2: pantalla negra
    // ============================================================
    private void drawPantalla()
    {
        int numRuedas = wheels.size();
        int anchoPantalla = numRuedas * WIN_W + Math.max(0, numRuedas - 1) * SEP + 2 * MARGEN;
        int altoPantalla = WIN_H + 40;
        int anchoCuerpo = anchoPantalla + 2 * BORDE;

        int pantallaX = 400 + (anchoCuerpo - anchoPantalla) / 2;
        int pantallaY = 200 + (ALTO_CUERPO - altoPantalla) / 2;

        screen = new Rectangle();
        screen.changeSize(altoPantalla, anchoPantalla);
        screen.changeColor("black");
        screen.moveHorizontal(pantallaX);
        screen.moveVertical(pantallaY);
        screen.makeVisible();
    }

    // ============================================================
    // CAPA 3: ventanas blancas + símbolos dentro
    // ============================================================
    private void drawWindows()
    {
        int numRuedas = wheels.size();
        if (numRuedas == 0) return;

        int anchoPantalla = numRuedas * WIN_W + (numRuedas - 1) * SEP + 2 * MARGEN;
        int altoPantalla = WIN_H + 40;
        int anchoCuerpo = anchoPantalla + 2 * BORDE;

        int pantallaX = 400 + (anchoCuerpo - anchoPantalla) / 2;
        int pantallaY = 200 + (ALTO_CUERPO - altoPantalla) / 2;

        for (int i = 0; i < numRuedas; i++)
        {
            int x = pantallaX + MARGEN + i * (WIN_W + SEP);
            int y = pantallaY + 20;

            Rectangle w = new Rectangle();
            w.changeSize(WIN_H, WIN_W);
            w.changeColor("white");
            w.moveHorizontal(x);
            w.moveVertical(y);
            w.makeVisible();

            windowsVista.add(w);

            wheels.get(i).drawInWindow(x, y, WIN_W+130, WIN_H);
        }
    }

    // ============================================================
    // CAPA 4: base negra
    // ============================================================
    private void drawBase()
    {
        int numRuedas = wheels.size();
        int anchoPantalla = numRuedas * WIN_W + Math.max(0, numRuedas - 1) * SEP + 2 * MARGEN;
        int anchoCuerpo = anchoPantalla + 2 * BORDE;

        base = new Rectangle();
        base.changeSize(30, Math.max(200, anchoCuerpo - 100));
        base.changeColor("black");
        base.moveHorizontal(450);
        base.moveVertical(200 + ALTO_CUERPO);
        base.makeVisible();
    }

    // ============================================================
    // CAPA 5: palanca
    // ============================================================
    private void drawPalanca()
    {
        int numRuedas = wheels.size();
        int anchoPantalla = numRuedas * WIN_W + Math.max(0, numRuedas - 1) * SEP + 2 * MARGEN;
        int anchoCuerpo = anchoPantalla + 2 * BORDE;

        int codoX = 400 + anchoCuerpo;
        int codoY = 200 + ALTO_CUERPO / 2;

        leverArm = new Rectangle();
        leverArm.changeSize(8, 80);
        leverArm.changeColor("black");
        leverArm.moveHorizontal(codoX);
        leverArm.moveVertical(codoY);
        leverArm.makeVisible();

        leverBar = new Rectangle();
        leverBar.changeSize(180, 8);
        leverBar.changeColor("black");
        leverBar.moveHorizontal(codoX + 72);
        leverBar.moveVertical(codoY - 172);
        leverBar.makeVisible();

        lever = new Circle();
        lever.changeSize(50);
        lever.changeColor("yellow");
        lever.moveHorizontal(codoX + 101);
        lever.moveVertical(codoY - 210);
        lever.makeVisible();
    }

    // ============================================================
    // AGREGAR / ELIMINAR RUEDAS
    // ============================================================
    public void addWheel(int pos)
    {
        if (pos < 0 || pos > wheels.size())
        {
            showError("La posición de la rueda no es válida.");
            return;
        }
        wheels.add(pos, new Wheel());
        redraw();
    }

    public void delWheel(int pos)
    {
        if (pos < 0 || pos >= wheels.size())
        {
            showError("La posición de la rueda no es válida.");
            return;
        }
        wheels.remove(pos);
        redraw();
    }

    // ============================================================
    // SÍMBOLOS
    // ============================================================
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
        redraw();
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
            if (wheel.removeSymbol(symbol)) deleted = true;
        }

        if (!deleted)
        {
            showError("El símbolo no existe.");
        }
        redraw();
    }

    // ============================================================
    // GIRO
    // ============================================================
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

        int steps = (int)(Math.random() * selectedWheel.getNumberSymbols());
        spin(wheel, steps);
        makeVisible();
    }

    public void spin(int wheel, int steps)
    {
        if (wheel < 0 || wheel >= wheels.size())
        {
            showError("La rueda indicada no existe.");
            return;
        }

        Wheel selectedWheel = wheels.get(wheel);
        if (selectedWheel.getNumberSymbols() == 0) return;
        if (selectedWheel.isFixed()) return;
        if (steps < 0) return;

        for (int i = 0; i < steps; i++)
        {
            selectedWheel.spin(1);
            redraw();
            pause();
        }
        makeVisible();
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
            makeVisible();
        }
        
    }

    // ============================================================
    // CONSULTAS
    // ============================================================
    public String[] symbols()
    {
        ArrayList<String> result = new ArrayList<String>();
        for (Wheel wheel : wheels)
        {
            int n = wheel.getNumberSymbols();
            if (n > 0)
            {
                Symbol current = wheel.getVisibleSymbol();
                for (int i = 0; i < n; i++)
                {
                    if (current != null && !result.contains(current.getName()))
                        result.add(current.getName());
                    wheel.spin(1);
                    current = wheel.getVisibleSymbol();
                }
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public int distinctSymbols() { return symbols().length; }

    public String[] configuration()
    {
        String[] result = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++)
        {
            Symbol s = wheels.get(i).getVisibleSymbol();
            result[i] = (s == null) ? null : s.getName();
        }
        return result;
    }

    public void setConfiguration(String[] configuration)
    {
        if (configuration == null) { showError("La configuración no es válida."); return; }
        if (configuration.length != wheels.size()) { showError("La configuración no corresponde al número de ruedas."); return; }

        for (int i = 0; i < configuration.length; i++)
        {
            if (configuration[i] == null) { showError("La configuración no es válida."); return; }

            Wheel wheel = wheels.get(i);
            if (wheel.getNumberSymbols() == 0) { showError("Una rueda no tiene símbolos."); return; }

            int n = wheel.getNumberSymbols();
            boolean found = false;
            for (int j = 0; j < n; j++)
            {
                Symbol current = wheel.getVisibleSymbol();
                if (current != null && current.getName().equals(configuration[i]))
                {
                    found = true;
                    break;
                }
                wheel.spin(1);
            }
            if (!found) { showError("El símbolo no existe en la rueda."); return; }
        }
        redraw();
    }

    public boolean isJackpot()
    {
        if (wheels.size() == 0) return false;
        Symbol first = wheels.get(0).getVisibleSymbol();
        if (first == null) return false;

        String firstName = first.getName();
        for (int i = 1; i < wheels.size(); i++)
        {
            Symbol current = wheels.get(i).getVisibleSymbol();
            if (current == null) return false;
            if (!firstName.equals(current.getName())) return false;
        }
        return true;
    }

    // ============================================================
    // VISIBILIDAD
    // ============================================================
    public void makeVisible()
    {
        visible = true;
        if (machine != null)   machine.makeVisible();
        if (base != null)      base.makeVisible();
        if (screen != null)    screen.makeVisible();
        if (leverArm != null)  leverArm.makeVisible();
        if (leverBar != null)  leverBar.makeVisible();
        if (lever != null)     lever.makeVisible();
        if (windowsVista != null)
            for (Rectangle w : windowsVista) w.makeVisible();

        for (int i = 0; i < wheels.size(); i++)
        {
            if (i < windowsVista.size())
            {
                Rectangle w = windowsVista.get(i);
                // No tenemos getX/getY en Rectangle, así que no podemos recalcular
                // En su lugar, basta con redibujar:
            }
        }
        redraw();
    }

    public void makeInvisible()
    {
        visible = false;
        if (machine != null)   machine.makeInvisible();
        if (base != null)      base.makeInvisible();
        if (screen != null)    screen.makeInvisible();
        if (leverArm != null)  leverArm.makeInvisible();
        if (leverBar != null)  leverBar.makeInvisible();
        if (lever != null)     lever.makeInvisible();
        if (windowsVista != null)
            for (Rectangle w : windowsVista) w.makeInvisible();

        for (Wheel wheel : wheels)
        {
            for (int i = 0; i < wheel.getNumberSymbols(); i++)
            {
                Symbol s = wheel.getVisibleSymbol();
                if (s != null) s.makeInvisible();
            }
        }
    }

    // ============================================================
    // RUEDAS: INTERCAMBIAR, FIJAR, SOLTAR
    // ============================================================
    public void exchangeWheels(int wheel1, int wheel2)
    {
        if (!validWheel(wheel1) || !validWheel(wheel2))
        {
            showError("Una de las ruedas no existe.");
            return;
        }
        if (wheel1 == wheel2) return;

        Wheel temporary = wheels.get(wheel1);
        wheels.set(wheel1, wheels.get(wheel2));
        wheels.set(wheel2, temporary);
        redraw();
    }

    public void fixWheel(int wheel)
    {
        if (!validWheel(wheel)) { showError("La rueda indicada no existe."); return; }
        wheels.get(wheel).fix();
    }

    public void releaseWheel(int wheel)
    {
        if (!validWheel(wheel)) { showError("La rueda indicada no existe."); return; }
        wheels.get(wheel).release();
    }

    // ============================================================
    // UTILIDADES
    // ============================================================
    public void exit()
    {
        makeInvisible();
        wheels.clear();
    }

    public boolean ok() { return wheels.size() > 0; }

    private boolean validWheel(int wheel) { return wheel >= 0 && wheel < wheels.size(); }

    private void pause()
    {
        try { Thread.sleep(100); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    private void showError(String message)
    {
        if (visible)
            JOptionPane.showMessageDialog(null, message);
    }
    public SlotMachine(int n)
    {
        wheels = new ArrayList<Wheel>();
        windowsVista = new ArrayList<Rectangle>();
        visible = true;
    
        for (int i = 0; i < n; i++)
        {
            wheels.add(new Wheel());
        }
    
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                addSymbol(i, "symbol" + j);
            }
        }
    
        redraw();
    }
}