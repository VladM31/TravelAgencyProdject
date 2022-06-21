package nure.knt.database.idao.goods;

import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;

import java.util.Set;
import java.util.function.Supplier;

public interface ScriptTourAdWhere extends Supplier<String> {


    public ScriptTourAdWhere idTravelAgencyIs(Long id);
    public ScriptTourAdWhere typeStateIn(Set<TypeState> types);
    public ScriptTourAdWhere conditionCommodityIn(Set<ConditionCommodity> conditions);
    public ScriptTourAdWhere hiddenIs(boolean hidden);
}
