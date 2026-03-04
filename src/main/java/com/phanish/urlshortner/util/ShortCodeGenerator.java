package com.phanish.urlshortner.util;
import java.security.SecureRandom;

import static java.lang.Math.random;

public class ShortCodeGenerator
{

    private static final String CHARACTERS="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH=6;
    private static final SecureRandom random = new SecureRandom();
    public static String generateShortCode()
    {
        StringBuilder code=new StringBuilder();
        for(int i=0;i<LENGTH;i++)
        {
            int index=random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
}
