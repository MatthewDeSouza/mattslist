package com.github.matthewdesouza.mattslist.helper;

import com.github.matthewdesouza.mattslist.entity.Topic;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionUtil {
    public static Map<String, Integer> occurrenceOfNameInTopicList(List<Topic> list) {
        Map<String, Integer> map = new HashMap<>();
        list.forEach(obj -> map.merge(obj.getName(), 1, Integer::sum));
        return map;
    }
}
