package com.normalworks.common.template;

/**
 * CommonServiceCallback
 *
 * @author: lingeng
 * @date: 8/30/22
 */
public interface ServiceCallback {

    void checkParams();

    void process() throws Exception;
}
