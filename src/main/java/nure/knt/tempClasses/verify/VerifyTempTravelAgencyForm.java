package nure.knt.tempClasses.verify;

import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.entity.important.TravelAgency;
import nure.knt.forms.entities.TravelAgencyForm;
import nure.knt.tempClasses.verify.verify.inter.IVerifySyntaxErrors;
import nure.knt.tempClasses.verify.verify.inter.IVerifyTravelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyTempTravelAgencyForm implements IVerifyTravelForm {

    @Autowired
    private IDAOTravelAgencySQL<TravelAgency> dataTravel;
    @Autowired
    private IVerifySyntaxErrors syntaxErrors;

    @Override
    public String checkOut(TravelAgencyForm taf) {
        if(taf.isChooseEmpty())
            return "Error:Didn't choose EGRPOY or RNEKPN";

        if (syntaxErrors.hasProblemInUsername(taf.getLogin()))
            return "Error:The username is incorrect";

        if (syntaxErrors.hasProblemInPassword(taf.getPassword()))
            return "Error:The password is incorrect";

        if (syntaxErrors.hasProblemInEmail(taf.getEmail()))
            return "Error:The email is incorrect";

        if (taf.getEGRPOYorRNYKPN() == null) {
            return "Error:EGRPOY or RNYKPN didnt input";
        }

        if (taf.getWhatChoose().equals("setEGRPOY")) {
            if (this.dataTravel.findByEGRPOY(taf.getEGRPOYorRNYKPN()) != null)
                return "Error:The EGRPOY is basy";
        }else{
            if (this.dataTravel.findByRNEKPN(taf.getEGRPOYorRNYKPN()) != null)
                return "Error:The RNYKPN is basy";
        }

        return "Successful";
    }
}
