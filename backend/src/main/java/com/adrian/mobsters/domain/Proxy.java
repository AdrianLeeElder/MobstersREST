package com.adrian.mobsters.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Document(collection = "proxies")
@NoArgsConstructor
public class Proxy implements Comparable {

    @Id
    private String id;
    @NotBlank
    @NonNull
    @Indexed(unique = true)
    private String host;
    @Min(1)
    private int port;
    private boolean inUse;
    private int attempts;
    private int failures;
    private int successes;
    private boolean httpsEnabled;
    private String country;
    private long sinceLastUpdate;
    private boolean outdated;
    /**
     * This means the proxy is unable to be detected by the web server.
     */
    private boolean isEliteProxy;

    public Proxy(@NotBlank String host, @Min(1) int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public int compareTo(Object o) {
        Proxy proxy = (Proxy) o;
        if (sinceLastUpdate > proxy.getSinceLastUpdate()) {
            return 1;
        } else if (sinceLastUpdate < proxy.getSinceLastUpdate()) {
            return -1;
        }

        return 0;
    }
}
