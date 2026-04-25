package com.seng.management_system.constant;

public final class MessageConstant {
    private MessageConstant() {
        // prevent instantiation
    }
    public static String RequiedField(String name){
        return String.format("%s is required!",name);
    }
}
