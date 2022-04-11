package com.hcy.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcy.pojo.R;
import com.hcy.util.JwtTokenUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/10/27 10:15
 */
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();

        //放行资源
        URI uri = request.getURI();
        System.out.println("-----------"+uri.getPath());
        String[] releasePath = {"/management-stage/admin/login",
                "/management-stage/admin/register",
                "/management-stage/seller/login",
                "/management-stage/seller/register",
                "/sso-service/user/login",
                "/goods-service/sku/search",
                "/goods-service/goods/getDetailByGoodsId"
        };
        for (String w : releasePath) {
            if(uri.getPath().equals(w)){
                return chain.filter(exchange);//放行
            }
        }
        Map<String,Object> res = new HashMap<>();
        //判断请求头里面是否有token
        String token = request.getHeaders().getFirst("Authorization").substring(7);
        System.out.println("验证令牌："+token);
        if (token == null) {
            //不能放行，直接响应客户端
            res.put("msg","没有令牌");
            return response(response,new R(4000,false,res));
        }else {
            Map<String, Object> verify = JwtTokenUtil.verify(token);
            Boolean b = (Boolean) verify.get("valid");
            if (b){
                return chain.filter(exchange);
            }else {
                res.put("msg","令牌错误");
                return response(response,new R(4000,false,res));
            }
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> response(ServerHttpResponse response, R res){
        //不能放行，直接返回，返回json信息
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        DataBuffer dataBuffer = response.bufferFactory().wrap(jsonStr.getBytes());

        return response.writeWith(Flux.just(dataBuffer));//响应json数据
    }
}
