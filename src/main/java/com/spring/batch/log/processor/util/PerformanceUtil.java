package com.spring.batch.log.processor.util;

public class PerformanceUtil {

    public static long getTimeDiff(long startTime,long endTime,long divider){
        if(divider <= 0){
            return 0;
        }

        return Math.round(endTime - startTime) / divider;
    }
}
