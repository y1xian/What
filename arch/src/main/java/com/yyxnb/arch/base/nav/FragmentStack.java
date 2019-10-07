package com.yyxnb.arch.base.nav;

import com.yyxnb.arch.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 单个activity的fragment的堆栈，为了在Activity被销毁时能自动重建
 */
public class FragmentStack {

    private static FragmentStack instance;
    private Map<String, LinkedList<BaseFragment>> stacks = new LinkedHashMap<>();

    private FragmentStack() {

    }

    public static FragmentStack getInstance() {
        if (instance == null) {
            instance = new FragmentStack();
        }
        return instance;
    }

    public void put(String activity, BaseFragment rootFragment) {
        LinkedList<BaseFragment> fragments = new LinkedList<>();
        if (stacks.containsKey(activity)) {
            fragments = stacks.get(activity);
            stacks.remove(activity);
        }
        assert fragments != null;
        fragments.add(rootFragment);
        stacks.put(activity, fragments);
    }

    public void pop(String activity) {
        if (stacks.containsKey(activity)) {
            LinkedList<BaseFragment> fragments = stacks.get(activity);
            assert fragments != null;
            fragments.remove(fragments.size() - 1);
            stacks.remove(activity);
            stacks.put(activity, fragments);
        }
    }

    public LinkedList<BaseFragment> getStacks(String activity) {
        LinkedList<BaseFragment> fragments = stacks.get(activity);
        stacks.remove(activity);
        return fragments;
    }
}
