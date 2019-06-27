package com.iamunclexu.confs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;

import static com.iamunclexu.confs.Constant.TEMPLATE_DOCUMENT;

public class TemplateConf {

    private static Logger LOGGER = LoggerFactory.getLogger(TemplateConf.class);
    private static TemplateConf templateConf;

    private static Configuration configuration;

    public static TemplateConf inst() {
        if (templateConf == null) {
            templateConf = new TemplateConf();
        }
        return templateConf;
    }

    public void init() {
        configuration = new Configuration();
        try {
            String templatePath = SysConf.FRONTEND_ROOT + TEMPLATE_DOCUMENT;

            LOGGER.info("Template dir is : " + templatePath);
            configuration.setTemplateLoader(new FileTemplateLoader(new File(templatePath)));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static Configuration fetchConfiguration() {
        return configuration;
    }
}
