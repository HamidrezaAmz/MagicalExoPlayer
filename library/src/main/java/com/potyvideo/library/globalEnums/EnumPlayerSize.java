package com.potyvideo.library.globalEnums;

public enum EnumPlayerSize {

    UNDEFINE("UNDEFINE", -1),
    EXACTLY("EXACTLY", 0),
    AT_MOST("AT_MOST", 1),
    UNSPECIFIED("UNSPECIFIED", 2),
    ;

    private String valueStr;

    private Integer value;

    EnumPlayerSize(String valueStr, Integer value) {
        this.valueStr = valueStr;
        this.value = value;
    }

    public static EnumPlayerSize get(String value) {
        if (value == null) {
            return UNDEFINE;
        }

        EnumPlayerSize[] arr$ = values();
        for (EnumPlayerSize val : arr$) {
            if (val.valueStr.equalsIgnoreCase(value.trim())) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public static EnumPlayerSize get(Integer value) {

        if (value == null) {
            return UNDEFINE;
        }

        EnumPlayerSize[] arr$ = values();
        for (EnumPlayerSize val : arr$) {
            if (val.value == value) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public String getValueStr() {
        return valueStr;
    }

    public Integer getValue() {
        return value;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}
