package com.github.lane_cn.cmdb.domain;

import java.util.Map;

public class NodeSet {
    private Map<String, Host> hosts;
    private Map<String, Exchange> exchanges;

    public Map<String, Host> getHosts() {
        return hosts;
    }

    public void setHosts(Map<String, Host> hosts) {
        this.hosts = hosts;
    }

    public Map<String, Exchange> getExchanges() {
        return exchanges;
    }

    public void setExchanges(Map<String, Exchange> exchanges) {
        this.exchanges = exchanges;
    }
}
