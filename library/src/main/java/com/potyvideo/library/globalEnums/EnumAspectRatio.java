package com.potyvideo.library.globalEnums;

public enum EnumAspectRatio {

    UNDEFINE("UNDEFINE", 0),
    ASPECT_1_1("ASPECT_1_1", 1),
    ASPECT_16_9("ASPECT_16_9", 2),
    ASPECT_4_3("ASPECT_4_3", 3),
    ASPECT_MATCH("ASPECT_MATCH", 4),
    ASPECT_MP3("ASPECT_MP3", 5),
    ;

    private String valueStr;

    private Integer value;

    EnumAspectRatio(String valueStr, Integer value) {
        this.valueStr = valueStr;
        this.value = value;
    }

    public static EnumAspectRatio get(String value) {
        if (value == null) {
            return UNDEFINE;
        }

        EnumAspectRatio[] arr$ = values();
        for (EnumAspectRatio val : arr$) {
            if (val.valueStr.equalsIgnoreCase(value.trim())) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public static EnumAspectRatio get(Integer value) {

        if (value == null) {
            return UNDEFINE;
        }

        EnumAspectRatio[] arr$ = values();
        for (EnumAspectRatio val : arr$) {
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
