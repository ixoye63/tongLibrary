package com.tongtong.tonglib.hello;

import com.tongtong.tonglib.util.TimeUtil;
import com.tongtong.tonglib.util.Util;

/**
 * Created by KOHeeJin on 2016-07-26.
 */
public class HelloTongLib {
    String testText = "Library Test Success...";
    public String getText() {
        return testText;
    }
    public void setText(String text) {
        testText = text;
    }

    public String ping() { return Util.sprintf("%s : %s", "Hello!!", TimeUtil.getCurrentTimeMillis());}
    public String ping2() { return Util.sprintf("%s : %s", "Hello2!!", TimeUtil.getCurrentTimeMillis());}
    public String ping3() { return Util.sprintf("%s : %s", "Hello3!!", TimeUtil.getCurrentTimeMillis());}
}
