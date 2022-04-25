package com.example.demo.tempClasses.verify;

import com.example.demo.dao.idao.IDAOTravelAgency;
import com.example.demo.entity.important.TravelAgency;
import com.example.demo.forms.signup.TravelAgencyForm;
import com.example.demo.verify.inter.IVerifySyntaxErrors;
import com.example.demo.verify.inter.IVerifyTravelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyTempTravelAgencyForm implements IVerifyTravelForm {

    @Autowired
    private IDAOTravelAgency<TravelAgency> dataTravel;
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

        if (this.dataTravel.findByKVED(taf.getKVED()) != null)
            return "Error:The KVED is basy";

        if (this.dataTravel.findByAddress(taf.getAddressTravelAgency())!= null)
            return "Error:The Address is basy";;

        if(this.dataTravel.findByNameTravelAgency(taf.getNameTravelAgency()) != null)
            return "Error:The name travel agency is basy";

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
