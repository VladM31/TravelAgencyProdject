package com.example.demo.database.idao.goods;

import com.example.demo.entity.enums.ConditionCommodity;
import com.example.demo.entity.enums.TypeState;

import java.util.Set;
import java.util.function.Supplier;

public interface ScriptTourAdWhere extends Supplier<String> {


    public ScriptTourAdWhere idIs(Long id);
    public ScriptTourAdWhere typeStateIs(Set<TypeState> types);
    public ScriptTourAdWhere conditionCommodityIs(Set<ConditionCommodity> conditions);
}
