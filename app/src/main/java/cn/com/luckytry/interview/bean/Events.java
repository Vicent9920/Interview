package cn.com.luckytry.interview.bean;

/**
 * Event 管家
 * Created by 魏兴 on 2017/8/24.
 */

public class Events<T> {
    public T content;
    public  <O> Events<O> setContent(O t) {
        Events<O> events = new Events<>();
        events.content = t;
        return events;
    }
    public <T> T getContent() {
        return (T) content;
    }
}
