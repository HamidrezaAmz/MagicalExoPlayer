package com.potyvideo.library.globalEnums;

public enum EnumLoop {

    UNDEFINE("UNDEFINE", -1),
    INFINITE("Infinite", 1),
    Finite("Finite", 2),
    ;

    private String valueStr;

    private Integer value;

    EnumLoop(String valueStr, Integer value) {
        this.valueStr = valueStr;
        this.value = value;
    }

    public static EnumLoop get(String value) {
        if (value == null) {
            return UNDEFINE;
        }

        EnumLoop[] arr$ = values();
        for (EnumLoop val : arr$) {
            if (val.valueStr.equalsIgnoreCase(value.trim())) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public static EnumLoop get(Integer value) {

        if (value == null) {
            return UNDEFINE;
        }

        EnumLoop[] arr$ = values();
        for (EnumLoop val : arr$) {
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
