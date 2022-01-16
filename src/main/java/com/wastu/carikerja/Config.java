package com.wastu.carikerja;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    static Dotenv dotenv = Dotenv.load();
    public static final String DB_NAME = dotenv.get("DB_NAME");
    public static final String DB_USERNAME = dotenv.get("DB_USERNAME");
    public static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
    public static final String DB_URL = "jdbc:mysql://" + dotenv.get("DB_HOST") + ":" + dotenv.get("DB_PORT") +"/"+ DB_NAME;
}
