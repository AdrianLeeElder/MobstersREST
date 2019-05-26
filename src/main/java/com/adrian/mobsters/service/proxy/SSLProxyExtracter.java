package com.adrian.mobsters.service.proxy;

import com.adrian.mobsters.domain.Proxy;
import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pulls a list of proxies from the https://us-proxy.org.
 */
@Service
@Slf4j
public class SSLProxyExtracter implements ProxyExtracter {

    private static final String PROXY_ROWS_XPATH = "//*[@id='proxylisttable']/tbody//tr";

    private static final String[] TABLE_HEADERS = {
            "IP Address", "Port", "Code", "Country", "Anonymity", "Google", "Https", "Last Checked"
    };

    @Override
    public List<Proxy> getProxiesFromPageString(String webpage) {
        try {
            HtmlCleaner htmlCleaner = new HtmlCleaner();
            TagNode tagNode = htmlCleaner.clean(webpage);
            Object[] objects = tagNode.evaluateXPath(PROXY_ROWS_XPATH);

            return getProxiesFromNodeList(objects);
        } catch (XPatherException e) {
            log.error("Unable to run XPath", e);
        }

        return Collections.emptyList();
    }

    private List<Proxy> getProxiesFromNodeList(Object[] tagNodes) {
        List<Proxy> proxies = new ArrayList<>();

        for (Object row : tagNodes) {
            TagNode node = (TagNode) row;
            Proxy proxy = new Proxy();
            TagNode[] columnTds = node.getChildTags();

            for (int columnIndex = 0; columnIndex < columnTds.length; columnIndex++) {
                String columnName = TABLE_HEADERS[columnIndex];
                String columnValue = columnTds[columnIndex].getText().toString();
                addColumnValue(proxy, columnName, columnValue);
            }

            addProxyToList(proxy, proxies);
        }

        return proxies;
    }

    private void addProxyToList(Proxy proxy, List<Proxy> proxies) {
        String country = proxy.getCountry();
        Objects.requireNonNull(country);
        if (country.equalsIgnoreCase("United States") &&
                proxy.isHttpsEnabled() &&
                proxy.isEliteProxy() && proxy.getHost() != null) {
            proxies.add(proxy);
        }
    }

    private void addColumnValue(Proxy proxy, String columnName, String columnValue) {
        switch (columnName) {
            case "IP Address":
                proxy.setHost(columnValue);
                break;
            case "Port":
                setPort(proxy, columnValue);
                break;
            case "Country":
                setCountry(proxy, columnValue);
                break;
            case "Https":
                setHttpsEnabled(proxy, columnValue);
                break;
            case "Last Checked":
                setLastChecked(proxy, columnValue);
                break;
            case "Anonymity":
                setAnonymity(proxy, columnValue);
        }
    }

    private void setAnonymity(Proxy proxy, String columnValue) {
        if (columnValue == null || columnValue.isEmpty()) {
            throw new IllegalArgumentException("Anonymity cannot be empty or null:" + columnValue);
        }

        proxy.setEliteProxy(columnValue.equalsIgnoreCase("elite proxy"));
    }

    private void setCountry(Proxy proxy, String columnValue) {
        if (columnValue == null || columnValue.isEmpty()) {
            throw new IllegalArgumentException("Country value must not be empty: " + columnValue);
        }

        proxy.setCountry(columnValue);
    }

    private void setHttpsEnabled(Proxy proxy, String columnValue) {
        if (!columnValue.matches("(?i)YES|NO")) {
            throw new IllegalArgumentException("Https value is invalid: " + columnValue);
        }

        proxy.setHttpsEnabled(columnValue.equalsIgnoreCase("yes"));
    }

    private void setPort(Proxy proxy, String port) {
        if (port == null || !port.matches("[0-9]+")) {
            throw new IllegalArgumentException("Port must be numeric: " + port);
        }

        proxy.setPort(Integer.parseInt(port.trim()));
    }

    private void setLastChecked(Proxy proxy, String columnValue) {
        Pattern hourMinute = Pattern.compile("(?:([0-9]+) (?:hours?|seconds?)? ?)?(?:([0-9]+) minutes?)? ago");
        Matcher matcher = hourMinute.matcher(columnValue);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Last checked does not match regex: " + columnValue);
        } else {
            String seconds = "";
            String hours = "";
            if (columnValue.contains("seconds")) {
                seconds = matcher.group(1);
            } else {
                hours = matcher.group(1);
            }
            String minutes = matcher.group(2);

            long timeSinceUpdated = 0;
            if (hours != null && hours.matches("[0-9]+")) {
                timeSinceUpdated += Duration.ofHours(Long.parseLong(hours)).toMillis();
            }

            if (minutes != null && minutes.matches("[0-9]+")) {
                timeSinceUpdated += Duration.ofMinutes(Long.parseLong(minutes)).toMillis();
            }

            if (seconds != null && seconds.matches("[0-9]+")) {
                timeSinceUpdated += Duration.ofSeconds(Long.parseLong(seconds)).toMillis();
            }

            proxy.setSinceLastUpdate(timeSinceUpdated);
        }
    }
}
