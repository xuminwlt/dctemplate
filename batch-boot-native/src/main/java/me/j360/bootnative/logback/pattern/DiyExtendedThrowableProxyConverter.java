package me.j360.bootnative.logback.pattern;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;

/**
 * Package: me.j360.bootnative.logback.pattern
 * User: min_xu
 * Date: 16/7/15 下午8:23
 * 说明：
 */
public class DiyExtendedThrowableProxyConverter extends DiyBaseThrowableProxyConverter {
    @Override
    protected void extraData(StringBuilder builder, StackTraceElementProxy step) {
        ThrowableProxyUtil.subjoinPackagingData(builder, step);
    }

    protected void prepareLoggingEvent(ILoggingEvent event) {

    }
}
