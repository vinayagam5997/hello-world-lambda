package com.vinay;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.HashMap;
import java.util.Map;

public class vinayHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {

        context.getLogger().log("Method: " + request.getHttpMethod() + " | Path: " + request.getPath());

        // Read optional ?name= query param
        String name = null;
        if (request.getQueryStringParameters() != null) {
            name = request.getQueryStringParameters().get("name");
        }

        String message = (name != null && !name.isBlank())
                ? "Hello, " + name + "! Welcome to AWS Lambda via API Gateway."
                : "Hello, World! CI/CD Pipeline is working.";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(headers)
                .withBody("{\"message\": \"" + message + "\"}");
    }
}