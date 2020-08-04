package com.sofb.crawler.boot.starter.component.schedule;

import com.sofb.crawler.framework.core.base.SpiderContextThreadLocalStore;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.SpiderContext;
import io.lettuce.core.RedisCommandTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.sofb.crawler.framework.core.model.Request.isValidRequest;

/**
 * @author liuxuejun
 * @date 2019-12-30 13:37
 */
@Slf4j
public class RedisScheduleService {
    @Resource
    RedisTemplate<String, Request> redisTemplate;
    private Map<Long, String> targetQueueMap = new HashMap<>();
    private Map<Long, Integer> continueTaskCountMap = new HashMap<>();

    public void pushRequest(Request request) {
        String queue = request.fetchQueue();
        if (request.fetchUrgeStatus()) {
            redisTemplate.opsForList().leftPush(queue, request);
        } else {
            redisTemplate.opsForList().rightPush(queue, request);
        }

        log.info("推送请求 {} 任务到 {}队列", request, queue);
    }

    public Request pollRequest() {
        Request request = new Request();
        SpiderContext spiderContext = SpiderContextThreadLocalStore.get();
        // 为空则需要重新获取目标队列

        String targetQueue = fetchTargetQueue(spiderContext.getQueueList());
        targetQueueMap.put(getThreadId(), targetQueue);

        // 不为空则说明找到目标队列
        if (StringUtils.isNotEmpty(targetQueue)) {
            request = redisTemplate.opsForList().leftPop(targetQueue, 10, TimeUnit.SECONDS);
            if (!isValidRequest(request)) {
                log.info("{}-{} 所有队列都没任务了", spiderContext.getProjectName(), spiderContext.getPlatformName());
                targetQueueMap.remove(getThreadId());
            }
        }

        return request;
    }

    public int size(String targetQueue) {
        if (StringUtils.isNotEmpty(targetQueue)) {
            return Optional.ofNullable(redisTemplate.opsForList().size(targetQueue)).orElse(0L).intValue();
        } else {
            log.warn("targetQueue 为空");
            return 0;
        }
    }

    /**
     * 寻找有任务的队列
     *
     * @param queueList 待选队列列表
     * @return 返回任务列表
     */
    private String fetchTargetQueue(List<String> queueList) {
        int continueTaskCount = continueTaskCountMap.getOrDefault(getThreadId(), 0);
        String targetQueue = targetQueueMap.getOrDefault(getThreadId(), "");
        if (StringUtils.isNotEmpty(targetQueue) && continueTaskCount < 100) {
            continueTaskCountMap.put(getThreadId(), ++continueTaskCount);
            log.info("队列 {} 连续 {}次 执行任务", targetQueue, continueTaskCount);
            return targetQueue;
        }
        for (String queue : queueList) {
            if (size(queue) > 0) {
                continueTaskCountMap.remove(getThreadId());
                log.info("找到代取任务的目标队列 {}", queue);
                return queue;
            }

        }
        log.info("所有队列 {} 内容为空", queueList);
        return null;
    }

    public void deleteQueue(String... queues) {
        deleteQueue(StringUtils.join(queues, ":"));

    }

    public void deleteQueue(String queue) {
        redisTemplate.delete(queue);
        log.warn("删除队列 {}", queue);

    }


    private long getThreadId() {

        return Thread.currentThread().getId();
    }


    public void backUpItem(Request request) {
        String queue = request.fetchQueue() + request.fetchBatchId();
        String key = request.fetchDataId();
        log.info("备份到任务队列 {}，key {}", queue, key);
        redisTemplate.opsForHash().put(queue, key, request);
    }

    public void delBackUpItem(Request request) {
        String queue = request.fetchQueue() + request.fetchBatchId();
        String key = request.fetchDataId();
        log.info("删除备份到任务队列 {}，key {}", queue, key);
        redisTemplate.opsForHash().delete(queue, key);
    }


    public void pushErrorQueue(Request request) {
        if (!isValidRequest(request)) {
            return;
        }
        try {
            String queue = request.fetchQueue() + request.fetchBatchId() + "error";
            redisTemplate.opsForList().leftPush(queue, request);
        } catch (RedisCommandTimeoutException e) {
            log.info("终极失败任务队列 {} 失败", request);
            pushErrorQueue(request);
        }


    }
}
