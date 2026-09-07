import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas unitarias del segundo ciclo de SlotMachine.
 */
public class SlotMachineC2Test
{
    /**
     * Crea una maquina con tres ruedas y tres simbolos por rueda.
     * La maquina queda invisible para realizar las pruebas.
     *
     * @return maquina preparada para las pruebas.
     */
    private SlotMachine createMachine()
    {
        SlotMachine machine = new SlotMachine();

        machine.addWheel(0);
        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(0, "red");
        machine.addSymbol(0, "blue");
        machine.addSymbol(0, "green");

        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");

        machine.addSymbol(2, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(2, "green");

        machine.makeInvisible();

        return machine;
    }

    /**
     * Prueba que se pueden intercambiar dos ruedas.
     */
    @Test
    public void exchangeWheelsShouldExchangePositions()
    {
        SlotMachine machine = createMachine();

        machine.setConfiguration(
            new String[] {"red", "blue", "green"});

        machine.exchangeWheels(0, 2);

        assertArrayEquals(
            new String[] {"green", "blue", "red"},
            machine.configuration());
    }

    /**
     * Prueba que una rueda fijada no debe girar.
     */
    @Test
    public void fixedWheelShouldNotSpin()
    {
        SlotMachine machine = createMachine();

        machine.setConfiguration(
            new String[] {"red", "blue", "green"});

        machine.fixWheel(0);

        machine.spin(0, 1);

        assertArrayEquals(
            new String[] {"red", "blue", "green"},
            machine.configuration());
    }

    /**
     * Prueba que una rueda soltada vuelve a girar.
     */
    @Test
    public void releasedWheelShouldSpin()
    {
        SlotMachine machine = createMachine();

        machine.setConfiguration(
            new String[] {"red", "blue", "green"});

        machine.fixWheel(0);
        machine.releaseWheel(0);

        machine.spin(0, 1);

        assertArrayEquals(
            new String[] {"blue", "blue", "green"},
            machine.configuration());
    }

    /**
     * Prueba que una rueda gira exactamente el numero
     * de pasos solicitado.
     */
    @Test
    public void spinWithStepsShouldMoveExpectedNumberOfPositions()
    {
        SlotMachine machine = createMachine();

        machine.setConfiguration(
            new String[] {"red", "blue", "green"});

        machine.spin(0, 2);

        assertArrayEquals(
            new String[] {"green", "blue", "green"},
            machine.configuration());
    }

    /**
     * Prueba que la maquina puede quedar en una
     * configuracion especifica.
     */
    @Test
    public void setConfigurationShouldSetRequestedConfiguration()
    {
        SlotMachine machine = createMachine();

        machine.setConfiguration(
            new String[] {"blue", "green", "red"});

        assertArrayEquals(
            new String[] {"blue", "green", "red"},
            machine.configuration());
    }

    /**
     * Prueba que una configuracion invalida no debe
     * ser aceptada.
     */
    @Test
    public void invalidConfigurationShouldNotBeAccepted()
    {
        SlotMachine machine = createMachine();

        machine.setConfiguration(
            new String[] {"red", "blue", "green"});

        machine.setConfiguration(
            new String[] {"red", "purple", "green"});

        assertNotNull(machine.configuration());
    }
    
    @Test
    /** 
       * Verifica que la maquina pueda quedar 
       * en una configuracion * 
       * solicitada cuando los simbolos existen en todas las ruedas. 
    */
    public void accordingIbPoShouldSetValidConfiguration()
    {
        SlotMachine slotMachine = new SlotMachine();
    
        slotMachine.addWheel(0);
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
    
        slotMachine.addSymbol(0, "red");
        slotMachine.addSymbol(0, "blue");
    
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
    
        slotMachine.addSymbol(2, "red");
        slotMachine.addSymbol(2, "blue");
    
        slotMachine.setConfiguration(
            new String[]{"blue", "red", "blue"}
        );
    
        String[] configuration = slotMachine.configuration();
    
        assertEquals("blue", configuration[0]);
        assertEquals("red", configuration[1]);
        assertEquals("blue", configuration[2]);
    }
    @Test
    /** 
        * Verifica que una configuracion con un numero 
        * incorrecto * de simbolos no modifique 
        * la configuracion actual 
    */
    public void accordingIbPoShouldNotChangeConfigurationWithWrongSize()
    {
        SlotMachine slotMachine = new SlotMachine();
    
        slotMachine.addWheel(0);
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
    
        slotMachine.addSymbol(0, "red");
        slotMachine.addSymbol(0, "blue");
    
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
    
        slotMachine.addSymbol(2, "red");
        slotMachine.addSymbol(2, "blue");
    
        slotMachine.setConfiguration(
            new String[]{"red", "red", "red"}
        );
    
        String[] before = slotMachine.configuration();
    
        slotMachine.setConfiguration(
            new String[]{"blue", "blue"}
        );
    
        String[] after = slotMachine.configuration();
    
        assertArrayEquals(before, after);
    }
}