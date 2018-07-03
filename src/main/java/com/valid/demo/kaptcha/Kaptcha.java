package com.valid.demo.kaptcha;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class Kaptcha {


    private Properties porp = new Properties();
    private Producer producer;

    public  Kaptcha() {
        ImageIO.setUseCache(false);

        porp.put("kaptcha.border", "no");
        porp.put("kaptcha.textproducer.char.space","4");
        porp.put("kaptcha.textproducer.font.color", "red");

        Config config = new Config(this.porp);

        this.producer = config.getProducerImpl();
    }

    public void kaptcha(HttpServletRequest req, HttpServletResponse resp,String kapText) throws IOException {
        resp.setHeader("Cache-Control", "no-store, no-cache");
        resp.setContentType("image/jpeg");

        BufferedImage bi = this.producer.createImage(kapText);
        ServletOutputStream sos = resp.getOutputStream();

        ImageIO.write(bi, "jpg", sos);

    }

    public String generateKapText() {
        return this.producer.createText();
    }
}
