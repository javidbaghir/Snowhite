package com.Snowhite.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "snowhite")
public class SnowhiteConfigration {

    private int pageSize;
}
