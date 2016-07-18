package me.j360.bootnative.logback.pattern;

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.boolex.EventEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Package: me.j360.bootnative.logback.pattern
 * User: min_xu
 * Date: 16/7/15 下午8:19
 * 说明：
 */
public class DiyRootCauseFirstThrowableProxyConverter extends DiyExtendedThrowableProxyConverter {

    int lengthOption;
    List<EventEvaluator<ILoggingEvent>> evaluatorList = null;

    int errorCount = 0;

    @SuppressWarnings("unchecked")
    public void start() {

        String lengthStr = getFirstOption();

        if (lengthStr == null) {
            lengthOption = Integer.MAX_VALUE;
        } else {
            lengthStr = lengthStr.toLowerCase();
            if ("full".equals(lengthStr)) {
                lengthOption = Integer.MAX_VALUE;
            } else if ("short".equals(lengthStr)) {
                lengthOption = 2;
            } else {
                try {
                    // we add one because, printing starts at offset 1
                    lengthOption = Integer.parseInt(lengthStr) + 1;
                } catch (NumberFormatException nfe) {
                    addError("Could not parse [" + lengthStr + "] as an integer");
                    lengthOption = Integer.MAX_VALUE;
                }
            }
        }

        final List optionList = getOptionList();

        if (optionList != null && optionList.size() > 1) {
            final int optionListSize = optionList.size();
            for (int i = 1; i < optionListSize; i++) {
                String evaluatorStr = (String) optionList.get(i);
                Context context = getContext();
                Map evaluatorMap = (Map) context.getObject(CoreConstants.EVALUATOR_MAP);
                EventEvaluator<ILoggingEvent> ee = (EventEvaluator<ILoggingEvent>) evaluatorMap
                        .get(evaluatorStr);
                addEvaluator(ee);
            }
        }
        super.start();
    }

    private void addEvaluator(EventEvaluator<ILoggingEvent> ee) {
        if (evaluatorList == null) {
            evaluatorList = new ArrayList<EventEvaluator<ILoggingEvent>>();
        }
        evaluatorList.add(ee);
    }

    @Override
    protected String throwableProxyToString(IThrowableProxy tp) {
        StringBuilder buf = new StringBuilder(2048);
        subjoinRootCauseFirst(tp, buf);
        return buf.toString();
    }

    private void subjoinRootCauseFirst(IThrowableProxy tp, StringBuilder buf) {
        if (tp.getCause() != null)
            subjoinRootCauseFirst(tp.getCause(), buf);
        subjoinRootCause(tp, buf);
    }

    private void subjoinRootCause(IThrowableProxy tp, StringBuilder buf) {
        ThrowableProxyUtil.subjoinFirstLineRootCauseFirst(buf, tp);
        buf.append("###");
        StackTraceElementProxy[] stepArray = tp.getStackTraceElementProxyArray();
        int commonFrames = tp.getCommonFrames();

        boolean unrestrictedPrinting = lengthOption > stepArray.length;


        int maxIndex = (unrestrictedPrinting) ? stepArray.length : lengthOption;
        if (commonFrames > 0 && unrestrictedPrinting) {
            maxIndex -= commonFrames;
        }

        for (int i = 0; i < maxIndex; i++) {
            String string = stepArray[i].toString();
            buf.append(CoreConstants.TAB);
            buf.append(string);
            extraData(buf, stepArray[i]); // allow other data to be added
            buf.append("###");
        }

    }


}