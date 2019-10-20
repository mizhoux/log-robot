package xyz.mizhoux.logrobot.message;

/**
 * TypeEnum
 *
 * @author 之叶
 * @date   2019/10/20
 */
public enum MessageType {

    /**
     * 文本类型
     */
    TEXT("text"),

    /**
     * 链接类型
     */
    LINK("link"),

    /**
     * markdown 类型
     */
    MARKDOWN("markdown");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
