package com.yinuo.admin.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author lvchao1
 * @version 1.0
 * @since 2016-03-09
 */
public class IdUtil {
    private static final long xFFFFFF = 0xFFFFFF;
    private static final int xFF = 0xFF;

    private static final DateFormat SDF_MED = SimpleDateFormat.getDateTimeInstance( //
            SimpleDateFormat.MEDIUM, //
            SimpleDateFormat.MEDIUM);
    private static final IdUtil[] INSTANCES = new IdUtil[xFF + 1];

    private final AtomicLong INC = new AtomicLong();
    private int instanceId = 0; // instanceId for different applications

    static { // initiate 0-255 instances, to avoid duplication
        for (int i = 0; i <= xFF; i++) {
            IdUtil instance = new IdUtil();
            instance.instanceId = i;
            INSTANCES[i] = instance;
        }
    }

    private IdUtil() {
    }

    public long get() {
        return ((System.currentTimeMillis() >> 10) << 32) // timestamp
                + ((INC.incrementAndGet() & xFFFFFF) << 8) // auto incremental
                + instanceId // instance id
                ;
    }

    public static IdUtil id(int instanceId) {
        if (instanceId < 0 || instanceId > xFF)
            return null;
        return INSTANCES[instanceId];
    }

    public static IdUtil id() {
        return INSTANCES[0];
    }

    public static String toString(long id) {
        String hex = Long.toHexString(id);
        return hex.subSequence(0, 8) //
                + "-" + hex.substring(8, 14) //
                + "-" + hex.substring(14);
    }


    public static String toStringLong(long id) {
        long time = (System.currentTimeMillis() >> 42 << 42) + (id >> 22);
        long inc = (id >> 8) & xFFFFFF;
        long instanceId = id & xFF;

        return id + " (DEC)"//
                + "\n" + toString(id) + "  (HEX)" //
                + "\ntime=" + SDF_MED.format(new Date(time)) + ", instanceId=" + instanceId + ", inc=" + inc;
    }

    public static long generateId() {
        return id().get();
    }

}
