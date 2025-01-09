package dev.Deyvid.misc;

import java.time.DayOfWeek;

public class WeekDays {

    public enum Days {
        Lunedi(DayOfWeek.MONDAY),
        Martedi(DayOfWeek.TUESDAY),
        Mercoledi(DayOfWeek.WEDNESDAY),
        Giovedi(DayOfWeek.THURSDAY),
        Venerdi(DayOfWeek.FRIDAY),
        Sabato(DayOfWeek.SATURDAY),
        Domenica(DayOfWeek.SUNDAY)


        ;

        public final DayOfWeek dayOfWeek;

        private Days(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }
    }
}
