package com.sofb.crawler.boot.starter.component.gloablelistener;

import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.listener.global.GlobalListener;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.util.constant.RequestConstant;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liuxuejun
 * @date 2019-12-26 20:27
 */
public class SendMailGlobalListener implements GlobalListener {

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void beforeInit() {
    }

    @Override
    public void afterInit(Request request) {
    }

    @Override
    public void onSuccess(Request request, Response response) {
    }

    @Override
    public void onError(Request request, Response response, CrawlerException e) {

    }

    @Override
    public void unCatch(Request request, Response response, CrawlerException e) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("liuxuejun@sofb.com");
        msg.setBcc();
        msg.setTo("liuxuejun@sofb.com");
        StringBuilder title = new StringBuilder();
        title.append("爬虫异常： ");
        try {
            title.append("ip ");
            title.append(InetAddress.getLocalHost().getHostAddress());
            title.append("队列 ");
            title.append(request.getExtra(RequestConstant.QUEUE).toString());
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        try {
            String stringBuilder = "请求" + request + "响应" + response + "发生异常" + e;
            msg.setSubject(title.toString());
            msg.setText(stringBuilder);
            javaMailSender.send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
