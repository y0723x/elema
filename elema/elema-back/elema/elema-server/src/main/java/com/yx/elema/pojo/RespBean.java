package com.yx.elema.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object object;

    /**
     * 成功无返回对象
     * @param message
     * @return
     */
    public static RespBean success(String message){
        return new RespBean(200,message,null);
    }

    /**
     * 成功有返回对象
     * @param message
     * @param o
     * @return
     */
    public static RespBean success(String message, Object o){
        return new RespBean(200,message,o);
    }

    /**
     * 失败无返回对象
     * @param message
     * @return
     */
    public static RespBean error(String message){
        return new RespBean(500,message,null);
    }

    /**
     * 失败有返回对象
     * @param message
     * @param o
     * @return
     */
    public static RespBean error(String message, Object o){
        return new RespBean(500,message,o);
    }
}
