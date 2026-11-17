package entities;

public record Jugador(String equipo, String nombre, String posicion, int numero, String nacionalidad,
        int edad, String division, String imagenJugador, String imagenEquipo) {

    public Jugador {
        equipo = letras(equipo, "nombre del equipo", 2, 30);
        nombre = letras(nombre, "nombre del jugador", 3, 40);
        posicion = texto(posicion, "posición");
        nacionalidad = letras(nacionalidad, "nacionalidad", 3, 30);
        division = texto(division, "división");
        imagenJugador = texto(imagenJugador, "imagen del jugador");
        imagenEquipo = texto(imagenEquipo, "imagen del equipo (escudo)");
        if (numero < 0 || numero > 999) throw new IllegalArgumentException("El número debe estar entre 0 y 999");
        if (edad < 15 || edad > 99) throw new IllegalArgumentException("La edad debe estar entre 15 y 99");
    }

    private static String texto(String valor, String campo) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException("Falta el dato: " + campo);
        return valor.trim();
    }

    private static String letras(String valor, String campo, int minimo, int maximo) {
        valor = texto(valor, campo);
        if (!valor.matches("[\\p{L} ]{" + minimo + "," + maximo + "}"))
            throw new IllegalArgumentException("El dato " + campo + " solo admite letras y espacios, de "
                    + minimo + " a " + maximo + " caracteres");
        return valor;
    }

    public boolean es(String equipo, int numero) {
        return this.equipo.equalsIgnoreCase(equipo.trim()) && this.numero == numero;
    }

    public Jugador conImagenes(String imagenJugador, String imagenEquipo) {
        return new Jugador(equipo, nombre, posicion, numero, nacionalidad, edad, division, imagenJugador, imagenEquipo);
    }
}
