package com.handtruth.bot.adapter.utils.timers;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

@SuppressWarnings("WeakerAccess")
public class BotTimer {
    public static void setTimer(Calendar calendar, Long period, BotTimerTask task) {
        setTimer(calendar.getTime(), period, task);
    }

    public static void setTimer(Calendar calendar, BotTimerTask task) {
        setTimer(calendar, (long) 0, task);
    }

    public static void setTimer(Date date, Long period, BotTimerTask task) {
        Timer timer = new Timer();
        timer.schedule(task, date, period);
    }

    public static void setTimer(Date date, BotTimerTask task) {
        setTimer(date, (long) 0, task);
    }

    enum Period {
        EVERY_HOUR (3600000L),
        EVERY_DAY  (86400000L),
        EVERY_WEEK (604800017L),
        EVERY_MONTH(2629800000L),
        EVERY_YEAR (31557600000L);
        long time;

        Period(long time) {
            this.time = time;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}
