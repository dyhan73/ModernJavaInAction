package Ch18_ThinkingFunctionally;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class S2_FunctionalPractice {
    static List<List<Integer>> subsets(List<Integer> list) {
        if (list.isEmpty()) {
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(Collections.emptyList());
            return ans;
        }

        Integer first = list.get(0);
        List<Integer> rest = list.subList(1, list.size());

        List<List<Integer>> subans = subsets(rest);
        List<List<Integer>> subans2 = insertAll(first, subans);
        return concat(subans, subans2);
    }

    static List<List<Integer>> insertAllNotWork(Integer first, List<List<Integer>> lists) {
        List<List<Integer>> res = List.copyOf(lists);
        for (List<Integer> list : res) {
            list.add(0, first);
        }
        return res;
    }

    static List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> list : lists) {
            List<Integer> copyList = new ArrayList<>();
            copyList.add(first);
            copyList.addAll(list);
            result.add(copyList);
        }
        return result;
    }

    static List<List<Integer>> concat(List<List<Integer>> lists1, List<List<Integer>> lists2) {
        List<List<Integer>> res = new ArrayList<>();
        res.addAll(lists1);
        res.addAll(lists2);
        return res;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 4, 9);
        System.out.println(subsets(list));
    }
}
