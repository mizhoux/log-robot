package xyz.mizhoux.logrobot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.mizhoux.logrobot.message.BaseMessage;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * 钉钉机器人消息发送工具
 *
 * @author 之叶
 * @date 2019/10/20
 */
public class DingTalkTool {

    private static final Logger logger = LoggerFactory.getLogger(DingTalkTool.class);

    private static final int CODE_OK = 200;

    private static final String WEBHOOK = "https://oapi.dingtalk.com/robot/send?access_token=f82edaa1f6977f25dff50ad357549b988507155b3b9824897b0cd1a4b9e92d50";

    /**
     * 异步发送消息
     *
     * @param message 消息
     */
    public static void send(BaseMessage message) {
        CompletableFuture.completedFuture(message)
                         .thenAcceptAsync(DingTalkTool::sendSync);
    }

    /**
     * 同步发送消息
     *
     * @param message 消息
     */
    private static void sendSync(BaseMessage message) {
        // HTTP 消息体（编码必须为 utf-8）
        MediaType   mediaType   = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, message.toRequestBody());

        // 创建 POST 请求
        Request request = new Request.Builder()
                .url(WEBHOOK)
                .post(requestBody)
                .build();

        // 通过 HTTP 客户端发送请求
        OkHttpClient httpClient = new OkHttpClient();

        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call c, IOException e) {
                logger.error("发送消息失败，请查看异常信息", e);
            }

            @Override
            public void onResponse(Call c, Response response) throws IOException {
                int code = response.code();
                if (code != CODE_OK) {
                    logger.error("发送消息失败，code={}", code);
                    return;
                }

                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    JSONObject body = JSON.parseObject(responseBody.string());

                    int errCode = body.getIntValue("errcode");
                    if (errCode != 0) {
                        String errMsg = body.getString("errmsg");
                        logger.error("发送消息出现错误，errCode={}, errMsg={}", errCode, errMsg);
                    }
                }
            }
        });
    }
}
