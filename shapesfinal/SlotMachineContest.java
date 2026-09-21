public class SlotMachineContest
{
    /**
     * Resuelve el problema de la maraton.
     *
     * No utiliza SlotMachine.
     *
     * @param machine configuracion de las ruedas.
     * @return numero de pasos que debe girar cada rueda.
     */
    public static int[] solve(String[][] machine)
    {
        int n = machine.length;
        int[] solution = new int[n];

        if (n == 0)
        {
            return solution;
        }

        /*
         * Primero buscamos una configuracion en la que
         * todos los simbolos sean diferentes.
         */

        for (int i = 0; i < n; i++)
        {
            solution[i] = i;
        }

        return solution;
    }

    /**
     * Simula una solucion usando SlotMachine.
     *
     * @param machine configuracion inicial.
     * @param solution pasos que debe girar cada rueda.
     * @return true si se consigue el jackpot.
     */
    public static boolean simulate(String[][] machine, int[] solution)
    {
        int n = machine.length;

        SlotMachine slotMachine = new SlotMachine(n);

        slotMachine.makeVisible();

        for (int i = 0; i < n; i++)
        {
            slotMachine.spin(i, solution[i]);
        }

        return slotMachine.distinctSymbols() == 1;
    }
}