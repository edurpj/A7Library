package br.com.biblioteca.enums;

public enum DataEnum {

    JANUARY("Janu", 1),
    FEBRUARY("Febr", 2),
    MARCH("Marc", 3),
    APRIL("Apri", 4),
    MAY("May", 5),
    JUNE("June", 6),
    JULY("July", 7),
    AUGUST("Augu", 8),
    SEPTEMBER("Sept", 9),
    OCTOBER("Octo", 10),
    NOVEMBER("Nove", 11),
    DECEMBER("Dece", 12);

    private final String mesAbreviado;
    private final int numMes;

    DataEnum(String mesAbreviado, int numMes) {
        this.mesAbreviado = mesAbreviado;
        this.numMes = numMes;
    }

    public static int getNumMes(String abreviacao) {
        for (DataEnum mes : DataEnum.values()) {
            if (mes.mesAbreviado.startsWith(abreviacao)) {
                return mes.numMes;
            }
        }
        return -1;
    }
}
