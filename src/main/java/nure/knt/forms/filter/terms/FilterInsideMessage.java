package nure.knt.forms.filter.terms;

import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fictions.ITermInsideMessage;
import nure.knt.entity.enums.HowSortSQL;
import nure.knt.entity.enums.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class FilterInsideMessage {
    public static final boolean NOT_ALL_ROLES = false;
    private Role[] roles;//+
    private String messageName;//+
    private String otherUsersName;//+
    private String otherUsersEmail;//+
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startSendDate,endSendDate;//+
    private Boolean itWasRead,itWasNotRead;

    private HowSortSQL howSort;
    private ITermInsideMessage.MessageField orderBy;
    private Long[] limits;//+

    public ITermInformation filtering(ITermInsideMessage term){

        // =========================== String ===========================
        HandlerFilterTerms.checkString(messageName, byMessageName -> term.nameMessageContaining(byMessageName));

        HandlerFilterTerms.checkString(otherUsersName, byOtherUsersName -> term.nameOtherUsersContaining(byOtherUsersName));

        HandlerFilterTerms.checkString(otherUsersEmail, byOtherUsersEmail -> term.emailOtherUsersContaining(byOtherUsersEmail));

        // =========================== Array ===========================


        if(roles != null && Arrays.stream(roles).anyMatch( role -> Objects.equals(role,Role.ALL)) == NOT_ALL_ROLES){
            HandlerFilterTerms.checkArray(roles,byRoles -> term.roleNameIn(roles));
        }



        HandlerFilterTerms.checkArray(limits,byLimit-> term.limitIs(byLimit));

        // =========================== Date ===========================

        HandlerFilterTerms.checkDateTime(startSendDate,endSendDate,
                (startDate,endDate) -> term.sendDateBetween(startDate,endDate),
                (date) -> term.sendDateAfter(date),
                (date) -> term.sendDateBefore(date));

        // =========================== State ===========================

        HandlerFilterTerms.checkTwoBooleanForOneState(itWasRead,itWasNotRead,state -> term.isRead(state.booleanValue()));

        // =========================== Sort ===========================

        if(this.howSort == null){
            this.howSort = HowSortSQL.DESC;
        }

        if(this.orderBy == null){
            this.orderBy = ITermInsideMessage.MessageField.SEND_DATE;
        }

        term.orderBy(orderBy,howSort);

        return term.end();
    }

    public FilterInsideMessage(Role[] roles, String messageName, String otherUsersName, String otherUsersEmail,
                               LocalDateTime startSendDate, LocalDateTime endSendDate, Boolean itWasRead,
                               Boolean itWasNotRead, HowSortSQL howSort, ITermInsideMessage.MessageField orderBy, Long[] limits) {
        this.roles = roles;
        this.messageName = messageName;
        this.otherUsersName = otherUsersName;
        this.otherUsersEmail = otherUsersEmail;
        this.startSendDate = startSendDate;
        this.endSendDate = endSendDate;
        this.itWasRead = itWasRead;
        this.itWasNotRead = itWasNotRead;
        this.howSort = howSort;
        this.orderBy = orderBy;
        this.limits = limits;
    }

    public FilterInsideMessage() {
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getOtherUsersName() {
        return otherUsersName;
    }

    public void setOtherUsersName(String otherUsersName) {
        this.otherUsersName = otherUsersName;
    }

    public String getOtherUsersEmail() {
        return otherUsersEmail;
    }

    public void setOtherUsersEmail(String otherUsersEmail) {
        this.otherUsersEmail = otherUsersEmail;
    }

    public LocalDateTime getStartSendDate() {
        return startSendDate;
    }

    public void setStartSendDate(LocalDateTime startSendDate) {
        this.startSendDate = startSendDate;
    }

    public LocalDateTime getEndSendDate() {
        return endSendDate;
    }

    public void setEndSendDate(LocalDateTime endSendDate) {
        this.endSendDate = endSendDate;
    }

    public Boolean getItWasRead() {
        return itWasRead;
    }

    public void setItWasRead(Boolean itWasRead) {
        this.itWasRead = itWasRead;
    }

    public Boolean getItWasNotRead() {
        return itWasNotRead;
    }

    public void setItWasNotRead(Boolean itWasNotRead) {
        this.itWasNotRead = itWasNotRead;
    }

    public HowSortSQL getHowSort() {
        return howSort;
    }

    public void setHowSort(HowSortSQL howSort) {
        this.howSort = howSort;
    }

    public ITermInsideMessage.MessageField getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(ITermInsideMessage.MessageField orderBy) {
        this.orderBy = orderBy;
    }

    public Long[] getLimits() {
        return limits;
    }

    public void setLimits(Long[] limits) {
        this.limits = limits;
    }
}
