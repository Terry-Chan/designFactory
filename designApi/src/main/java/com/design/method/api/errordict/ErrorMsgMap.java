package com.design.method.api.errordict;

import java.util.HashMap;
import java.util.Map;

public class ErrorMsgMap {

    private static final Map<Integer,String> map = new HashMap();

    public static final Integer REQUEST_ERROR = 10010;

    public static final Integer REQUEST_INDEX_ERROR = 10011;

    public static final Integer INDEX_EXISTS_ALREADY = 10012;

    public static final Integer INDEX_CREATE_ERROR = 10013;

    public static final Integer INDEX_NOT_EXISTS = 10014;

    public static final Integer DELETEE_INDEX_FAIL = 10015;

    public static final Integer DELETEE_INDEX_ERROR = 10016;

    public static final Integer INSERT_DATA_FAIL = 10017;

    public static final Integer INSERT_DATA_ERROR = 10018;

    public static final Integer SELECT_DATA_ERROR = 10019;

    public static final Integer UPDATE_DATE_FAIL = 10020;

    public static final Integer UPDATE_DATE_ERROR = 10021;

    public static final Integer SEND_MSG_MQ_ERROR = 10023;

    public static final Integer PULL_MSG_MQ_ERROR = 10024;

    public static final Integer REQUEST_ERROR_PARAM = 10025;

    public static final Integer SYSTEM_MAXTHREAD_WARN = 10026;

    public static final Integer MAKE_ORDER_FAIL = 10027;

    public static final Integer PRODUCT_NOT_EXIT = 10028;

    public static final Integer PRODUCT_SOLD_OUT_ALREADY = 10029;

    public static final Integer CAN_NOT_REPEAT_OREDR = 10029;

    public static final Integer NOT_ALLOW_TO_ACCESS = 10030;

    public static final Integer NOT_VALIDATA_TOKEN = 10031;

    static {
        map.put(REQUEST_ERROR,"系统异常");
        map.put(REQUEST_INDEX_ERROR,"查询索引失败");
        map.put(INDEX_EXISTS_ALREADY,"查询索引失败");
        map.put(INDEX_CREATE_ERROR,"创建索引异常");
        map.put(INDEX_NOT_EXISTS,"索引不存在");
        map.put(DELETEE_INDEX_FAIL,"删除索引失败");
        map.put(DELETEE_INDEX_ERROR,"执行删除索引异常");
        map.put(INSERT_DATA_ERROR,"插入数据异常");
        map.put(INSERT_DATA_FAIL,"插入数据失败");
        map.put(SELECT_DATA_ERROR,"查询数据异常");
        map.put(UPDATE_DATE_FAIL,"更新数据失败");
        map.put(UPDATE_DATE_ERROR,"更新数据异常");
        map.put(SEND_MSG_MQ_ERROR,"推送消息异常");
        map.put(PULL_MSG_MQ_ERROR,"拉取消息异常");
        map.put(REQUEST_ERROR_PARAM,"参数异常");
        map.put(SYSTEM_MAXTHREAD_WARN,"系统繁忙，请稍后再试");
        map.put(MAKE_ORDER_FAIL,"下单失败！");
        map.put(PRODUCT_NOT_EXIT,"商品不存在！");
        map.put(PRODUCT_SOLD_OUT_ALREADY,"商品已经抢购完，下次早点来哟！");
        map.put(CAN_NOT_REPEAT_OREDR,"近期已经有抢购成功，无法再次下单");
        map.put(NOT_ALLOW_TO_ACCESS,"无访问权限！");
        map.put(NOT_VALIDATA_TOKEN,"无效token！");
    }

    public  static String getOrDefault(Integer code) {
        return map.getOrDefault(code,"未知异常");
    }

}
