package nure.knt.database.idao.core;

import nure.knt.entity.enums.ConditionCommodity;

public interface IDAOUpdateConditionCommodity {
    public boolean updateConditionCommodity(Long id, ConditionCommodity conditionCommodity);
}
