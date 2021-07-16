package com.delcache.website.common.helper;

import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

public class ParamsHelper {

    private Map<String, Object> params;
    private List<String> condition;

    public ParamsHelper(Map<String, Object> params) {
        this.params = params;
        int page = Integer.parseInt(params.getOrDefault("page", "1").toString());
        this.params.put("page", page);
        int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10").toString());
        this.params.put("pageSize", pageSize);
        String limit = String.valueOf((page - 1) * pageSize) + "," + String.valueOf(pageSize);
        this.params.put("limit", limit);
        if (!ObjectUtils.isEmpty(params.get("searchType"))) {
            this.params.put(params.get("searchType").toString(), params.get("searchValue"));
        }
    }

    public ParamsHelper where(String key, Object value) {
        if ("java.util.ArrayList".equals(value.getClass().getTypeName())) {
            return this.where(key, "in", value);
        } else {
            return this.where(key, "=", value);
        }
    }

    public ParamsHelper where(String key, String symbol, Object value) {
        if ("java.util.ArrayList".equals(value.getClass().getTypeName())) {
            String v = com.delcache.website.common.helper.Convert.listToString((List) value);
            this.condition.add("(" + com.delcache.website.common.helper.Convert.camelToUnderline(key) + " " + symbol + " (" + v + ")" + ")");
        } else {
            this.condition.add("(" + Convert.camelToUnderline(key) + " " + symbol + " " + value.toString() + ")");
        }
        return this;
    }
    public Map<String, Object> getParams() {
        return params;
    }
}
