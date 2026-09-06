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
}