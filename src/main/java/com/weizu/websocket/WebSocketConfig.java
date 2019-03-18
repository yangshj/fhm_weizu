package com.weizu.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket配置文件
 * 有了该配置文件，就不用在spring-mvc.xml中进行websocket的配置
 * EnableWebSocket注解 ：开启websocket服务
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    /**
     *添加处理器和拦截器，处理器后面的地址就是websocket的访问路径
     * setAllowedOrigins：指定的域名或IP，如果不限制使用"*"就可以了
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/websocket/weizu")
                .addInterceptors(myInterceptor()).setAllowedOrigins("*");

    }

    /**
     * 直接注入自己定义的websocket处理器
     * @return
     */
    @Bean
    public WebSocketHandler myWebSocketHandler(){
        return new MyWebSocketHandler();
    }

    /**
     * 直接注入自己定义的websocket拦截器
     * @return
     */
    @Bean
    public WebSocketHandshakeInterceptor myInterceptor(){
        return new WebSocketHandshakeInterceptor();
    }


}
