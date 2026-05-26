package com.vinay;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {

        return "Hello From CI CD Pipeline ,Automatic pipeline trigger";
    }
}