package com.example.gym.buddies.data;

import com.example.gym.buddies.data.model.jwtgen.UserLoginResponse;

public class PlaceHolder {
    public static String gymResponse = "{\n" +
            "    \"responseMessage\": \"Found 2 gyms\",\n" +
            "    \"responseStatus\": \"OK\",\n" +
            "    \"responseCode\": 200,\n" +
            "    \"gyms\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"testGym1\",\n" +
            "            \"website\": \"www.testGym1.com\",\n" +
            "            \"branches\": [\n" +
            "                {\n" +
            "                    \"id\": 1,\n" +
            "                    \"locality\": \"kamnahalli\",\n" +
            "                    \"city\": \"bangalore\",\n" +
            "                    \"latitude\": 60.0,\n" +
            "                    \"longitude\": 70.0,\n" +
            "                    \"contact\": 111111111\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 2,\n" +
            "                    \"locality\": \"gokuldham\",\n" +
            "                    \"city\": \"mumbai\",\n" +
            "                    \"latitude\": 70.0,\n" +
            "                    \"longitude\": 59.0,\n" +
            "                    \"contact\": 222222222\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"testGym2\",\n" +
            "            \"website\": \"www.testGym2.com\",\n" +
            "            \"branches\": [\n" +
            "                {\n" +
            "                    \"id\": 3,\n" +
            "                    \"locality\": \"hebbal\",\n" +
            "                    \"city\": \"bangalore\",\n" +
            "                    \"latitude\": 88.0,\n" +
            "                    \"longitude\": 55.0,\n" +
            "                    \"contact\": 12222222\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static UserLoginResponse loginResponse = new UserLoginResponse(1, "successfully authorized", "OK", 200,
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9DTElFTlQifV0sImlhdCI6MTU2OTA2MTM3OCwiZXhwIjozNjAwMDAwMDAwMH0.XGFZU2t1snQNhBQQmU66RjDMR9W-OLxX3I7LnB2LvRk"
            ,"l"
            );
}
