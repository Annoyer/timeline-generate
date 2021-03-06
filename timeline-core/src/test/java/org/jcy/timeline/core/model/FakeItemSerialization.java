package org.jcy.timeline.core.model;

import static java.lang.Long.parseLong;

public class FakeItemSerialization implements ItemSerialization<FakeItem> {

    private static final String SEPARATOR = "@";

    @Override
    public String serialize(FakeItem item) {
        return item.getId() + SEPARATOR + item.getTimeStamp();
    }

    @Override
    public FakeItem deserialize(String input) {
        String[] split = input.split(SEPARATOR);
        return new FakeItem(split[0], parseLong(split[1]));
    }
}