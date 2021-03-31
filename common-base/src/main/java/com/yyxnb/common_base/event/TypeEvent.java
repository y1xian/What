package com.yyxnb.common_base.event;

import androidx.lifecycle.LifecycleOwner;

import cn.hutool.core.util.ObjectUtil;

public class TypeEvent extends SingleLiveEvent<TypeEvent> {

    public String type;
    public Object value;

    public TypeEvent() {
    }

    public TypeEvent(String type) {
        this.type = type;
    }

    public TypeEvent(Object value) {
        this.value = value;
    }

    public TypeEvent(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public void observe(LifecycleOwner owner, final TypeObserver observer) {
        super.observe(owner, t -> {
            if (ObjectUtil.isNotNull(t)) {
                observer.onTypeChange(t);
            }
        });

    }

    public interface TypeObserver {
        void onTypeChange(TypeEvent t);
    }
}
