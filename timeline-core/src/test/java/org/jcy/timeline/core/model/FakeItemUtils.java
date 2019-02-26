package org.jcy.timeline.core.model;

import org.assertj.core.internal.cglib.core.CollectionUtils;

import java.text.DecimalFormat;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

public class FakeItemUtils {

    public static final FakeItem FIRST_ITEM = new FakeItem("1000000", 1000000);
    public static final FakeItem SECOND_ITEM = new FakeItem("2000000", 2000000);
    public static final FakeItem THIRD_ITEM = new FakeItem("3000000", 3000000);

    private static final DecimalFormat FORMAT = new DecimalFormat("0000000");

    public static final Set<FakeItem> ALL_ITEMS
            = unmodifiableSet(
            new HashSet<>(
                    asList(FIRST_ITEM, SECOND_ITEM, THIRD_ITEM)));

    public static FakeItem[] createItems(int itemCount) {
        FakeItem[] result = new FakeItem[itemCount];
        for (int i = 0; i < result.length; i++) {
            result[i] = new FakeItem(FORMAT.format(i), i);
        }
        return result;
    }

    public static FakeItem[] createNewItems(int currentMaxId, int count) {
        FakeItem[] result = new FakeItem[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = new FakeItem(FORMAT.format(currentMaxId + i + 1), currentMaxId + i + 1);
        }
        return result;
    }

    public static FakeItem[] createMoreItems(int currentMinId, int count) {
        return createNewItems(currentMinId - count - 1, count);
    }

    public static FakeItem[] reverse(FakeItem[] items) {
        List<FakeItem> itemList = asList(items);
        Collections.reverse(itemList);
        return itemList.toArray(new FakeItem[items.length]);
    }

    public static FakeItem[] subArray(FakeItem[] items, int fromIndex, int toIndex) {
        return asList(items)
                .subList(fromIndex, toIndex)
                .toArray(new FakeItem[toIndex]);
    }

    public static boolean containsAll(Collection<? extends Item> wanted, Collection<? extends Item> actual) {
        return wanted == null || (actual != null && actual.containsAll(wanted));
    }

    public static boolean containsNone(Collection<? extends Item> wanted, Collection<? extends Item> actual) {
        return wanted == null || (actual != null && wanted.stream().allMatch(i -> !actual.contains(i)));
    }
}