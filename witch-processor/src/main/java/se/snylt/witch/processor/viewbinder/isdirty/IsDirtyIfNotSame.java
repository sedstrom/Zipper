package se.snylt.witch.processor.viewbinder.isdirty;


import com.squareup.javapoet.TypeName;

import se.snylt.witch.processor.utils.TypeUtils;

public class IsDirtyIfNotSame extends IsDirty {

    public IsDirtyIfNotSame(TypeName targetTypeName) {
        super(targetTypeName);
    }

    @Override
    String getIsDirty() {
        return TypeUtils.DIFF_UTILS.toString() + ".notSame(getData(target), historyData)";
    }
}
