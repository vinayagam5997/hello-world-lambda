package com.vinay;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.Map;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        context.getLogger().log("Received request: " + request.getHttpMethod() + " " + request.getPath());

        String name = null;

        // Try query string params first
        if (request.getQueryStringParameters() != null) {
            name = request.getQueryStringParameters().get("name");
        }

        String message = (name != null && !name.isBlank())
                ? "Hello, " + name + "!"
                : "Hello, World!";

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(Map.of("Content-Type", "application/json"))
                .withBody("{\"message\": \"" + message + "\"}");
    }
}
