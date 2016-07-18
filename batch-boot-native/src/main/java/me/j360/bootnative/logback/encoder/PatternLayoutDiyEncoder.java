package me.j360.bootnative.logback.encoder;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;
import me.j360.bootnative.logback.DiyPatternLayout;

/**
 * Package: me.j360.bootnative.logback.encoder
 * User: min_xu
 * Date: 16/7/15 下午7:59
 * 说明：
 */
public class PatternLayoutDiyEncoder  extends PatternLayoutEncoderBase<ILoggingEvent> {

    @Override
    public void start() {
        DiyPatternLayout patternLayout = new DiyPatternLayout();
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());
        patternLayout.setOutputPatternAsHeader(outputPatternAsHeader);
        patternLayout.start();
        this.layout = patternLayout;
        super.start();
    }

}