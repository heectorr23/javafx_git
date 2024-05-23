package com.empresa.hito2_programacion_hector;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import java.util.Properties;

public class CaptchaGenerator {
    private DefaultKaptcha producer;

    public CaptchaGenerator() {
        Properties properties = new Properties();
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.noise.color", "black");
        properties.put("kaptcha.background.clear.from", "white");
        properties.put("kaptcha.background.clear.to", "white");
        properties.put("kaptcha.image.width", "125");
        properties.put("kaptcha.image.height", "45");

        Config config = new Config(properties);
        producer = new DefaultKaptcha();
        producer.setConfig(config);
    }

    public DefaultKaptcha getProducer() {
        return producer;
    }
}
