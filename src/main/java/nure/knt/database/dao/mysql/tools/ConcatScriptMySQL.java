package nure.knt.database.dao.mysql.tools;

import nure.knt.database.idao.tools.IConcatScripts;
import org.springframework.stereotype.Component;

@Component("Concat_Script_MySQL")
public class ConcatScriptMySQL implements IConcatScripts {
    @Override
    public String concatScripts(String... scripts) {
        return String.join(" ",scripts);
    }


}
