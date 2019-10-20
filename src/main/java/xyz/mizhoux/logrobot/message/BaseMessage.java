package xyz.mizhoux.logrobot.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 抽象消息类型（方便将来扩展其他类型的消息）
 *
 * @author 之叶
 * @date   2019/10/20
 */
public abstract class BaseMessage {

    private List<String> atMobiles;

    private boolean atAll;

    /**
     * 转为 JSON 格式的请求体
     *
     * @return 当前消息对应的请求体
     */
    public abstract String toRequestBody();

    public void addAtMobile(String atMobile) {
        if (atMobiles == null) {
            atMobiles = new ArrayList<>(1);
        }

        atMobiles.add(atMobile);
    }

    public void setAtAll(boolean atAll) {
        this.atAll = atAll;
    }

    public List<String> getAtMobiles() {
        return atMobiles != null ? atMobiles : Collections.emptyList();
    }

    public boolean isAtAll() {
        return atAll;
    }

}
