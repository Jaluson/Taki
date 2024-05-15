package com.taki.vo.currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class R<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> success(T data) {
        return new R<T>() {{
            setCode(200);
            setMsg("成功");
            setData(data);
        }};
    }

    public static <T> R<T> success() {
        return new R<T>() {{
            setCode(200);
            setMsg("成功");
        }};
    }

    public static <T> R<T> fail(String msg) {
        return new R<T>() {{
            setCode(500);
            setMsg(msg);
        }};
    }

    public static <T> R<T> fail() {
        return new R<T>() {{
            setCode(500);
            setMsg("失败");
        }};
    }

}
