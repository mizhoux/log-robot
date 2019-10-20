package xyz.mizhoux.logrobot.dingtalk;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import xyz.mizhoux.logrobot.message.TextMessage;

import java.util.Optional;

import static org.apache.commons.lang3.CharUtils.LF;

/**
 * DingTalkAppender
 *
 * @author 之叶
 * @date 2019/10/17
 */
public class DingTalkAppender extends AppenderSkeleton {

    @Override
    protected void append(LoggingEvent event) {
        // 获得调用的位置信息
        LocationInfo loc = event.getLocationInformation();

        String className = loc.getClassName();
        // 如果是 DingTalkTool 的日志，不进行输出，否则网络出错时会引起无限递归
        if (DingTalkTool.class.getName().equals(className)) { return; }

        StringBuilder content = new StringBuilder(1024);
        content.append("级别：").append(event.getLevel()).append(LF)
               .append("位置：").append(className).append('.').append(loc.getMethodName())
               .append("(行号=").append(loc.getLineNumber()).append(')').append(LF)
               .append("信息：").append(event.getMessage());

        Throwable ex = Optional.of(event)
                               .map(LoggingEvent::getThrowableInformation)
                               .map(ThrowableInformation::getThrowable)
                               .orElse(null);
        // 存在异常信息
        if (ex != null) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            content.append(LF).append("异常：").append(stackTrace);
        }

        TextMessage message = new TextMessage(content.toString());
        // 可添加你想要 at 的人: message.addAtMobile(xxx)
        DingTalkTool.send(message);
    }

    @Override
    public void close() { }

    @Override
    public boolean requiresLayout() { return false; }

}
