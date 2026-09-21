import static org.junit.Assert.*;
import org.junit.Test;

/**

* Pruebas unitarias del segundo ciclo de SlotMachine.
  */
  public class SlotMachineC2Test
  {
  /**

  * Crea una maquina con las tres ruedas que
  * ya vienen en el constructor de SlotMachine.
    */
    private SlotMachine createMachine()
    {
    SlotMachine machine = new SlotMachine();

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

  * Prueba que una rueda gira exactamente
  * el numero de pasos solicitado.
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

  * Prueba que la maquina puede quedar
  * en una configuracion especifica.
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

  * Prueba que una configuracion invalida
  * no modifica la configuracion anterior.
    */
    @Test
    public void invalidConfigurationShouldNotBeAccepted()
    {
    SlotMachine machine = createMachine();

    machine.setConfiguration(
    new String[] {"red", "blue", "green"});

    String[] before = machine.configuration();

    machine.setConfiguration(
    new String[] {"red", "purple", "green"});

    String[] after = machine.configuration();

    assertArrayEquals(before, after);
    }
}
